package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.OperationsConverter;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.OperationRepository;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final OperationsConverter operationsConverter;
    private final AccountBalanceService accountBalanceService;
    private final UserToAccountService userToAccountService;

    public OperationService(OperationRepository operationRepository, AccountRepository accountRepository, OperationsConverter operationsConverter, AccountBalanceService accountBalanceService, UserToAccountService userToAccountService) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.operationsConverter = operationsConverter;
        this.accountBalanceService = accountBalanceService;
        this.userToAccountService = userToAccountService;
    }

    @Transactional
    public OperationCreateDTO create(final OperationCreateDTO operation) {
        return saveUserWithCheckPermission(operation);
    }

    @Transactional
    public List<FullOperationDTO> getAll(final long accountId) {
        return getAllToEntity(accountId)
                .stream()
                .map(operationsConverter::convertToFullDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OperationEntity> getAllToEntity(final long accountId) {
        AccountEntity account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new NotFoundDataException("Don't find account."));
        if (!userToAccountService.checkPermissionCurrentUserWithAccount(account)) {
            throw new PermissionDeniedException("Current user doesn't have permission to interact with the account");
        }
        return new ArrayList<>(operationRepository
                .findAllByAccountOrderByCreationDateDesc(
                        account));
    }

    public FullOperationDTO getFromId(final long operationId) {
        OperationEntity operationEntity = operationRepository.findById(operationId)
                .orElseThrow(() -> new NotFoundDataException("Not found operation"));
        if (!userToAccountService.checkPermissionCurrentUserWithAccount(operationEntity.getAccount())) {
            throw new PermissionDeniedException("Current user doesn't have permission to interact with the account");
        }
        return operationsConverter.convertToFullDTO(operationEntity);
    }

    @Transactional
    public void delete(final long operationId) {
        FullOperationDTO operationDTO = getFromId(operationId);
        long accountId = operationDTO.getAccountId();
        operationRepository.delete(operationsConverter.convertToEntityFromFullDTO(operationDTO));
        accountBalanceService.updateBalance(accountId);
    }

    @Transactional
    public OperationCreateDTO change(final OperationCreateDTO operation) {
        if (operationRepository.findById(operation.getId())
                .orElseThrow(() -> new NotFoundDataException("Not found operation"))
                .getAccount().getId() != operation.getAccountId()) {
            throw new NotFoundDataException("Not found operation");
        }
        return saveUserWithCheckPermission(operation);
    }

    private OperationCreateDTO saveUserWithCheckPermission(OperationCreateDTO operation) {
        if (operation.getCreationDate() == null) {
            operation.setCreationDate(ZonedDateTime.now());
        }
        OperationEntity operationEntity = operationsConverter.convertToEntityFromCreateDTO(operation);
        if (!userToAccountService.checkPermissionCurrentUserWithAccount(operationEntity.getAccount())) {
            throw new PermissionDeniedException("Current user doesn't have permission to interact with the account");
        }
        if (userToAccountService.getAllCategoriesFromAccount(operationEntity.getAccount().getId(), operationEntity.getCategory().getType())
                .stream().noneMatch(category -> category.getId() == operation.getCategoryId())) {
            throw new PermissionDeniedException("Current user doesn't have permission to use this category");
        }
        OperationCreateDTO result = operationsConverter.convertToCreateDTO(operationRepository.save(operationEntity));
        accountBalanceService.updateBalance(operation.getAccountId());
        return result;
    }
}
