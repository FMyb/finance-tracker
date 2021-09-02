package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.services.CurrentUserService;

import javax.transaction.Transactional;

/**
 * @author Yaroslav Ilin
 */

@Component
public class OperationsConverter {
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final CurrentUserService currentUserService;

    public OperationsConverter(AccountRepository accountRepository, CategoryRepository categoryRepository, CategoryConverter categoryConverter, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.currentUserService = currentUserService;
    }

    public OperationCreateDTO convertToCreateDTO(final OperationEntity operationEntity) {
        OperationCreateDTO result = new OperationCreateDTO();
        result.setAccountId(operationEntity.getAccount().getId());
        result.setAmount(operationEntity.getAmount());
        result.setCategoryId(operationEntity.getCategory().getId());
        result.setId(operationEntity.getId());
        result.setCreationDate(operationEntity.getCreationDate());
        return result;
    }

    public FullOperationDTO convertToFullDTO(final OperationEntity operationEntity) {
        FullOperationDTO result = new FullOperationDTO();
        result.setAccountId(operationEntity.getAccount().getId());
        result.setAmount(operationEntity.getAmount());
        result.setCategoryDTO(categoryConverter.convertToDTO(operationEntity.getCategory()));
        result.setId(operationEntity.getId());
        result.setCreationDate(operationEntity.getCreationDate());
        return result;
    }

    @Transactional
    public OperationEntity convertToEntityFromCreateDTO(final OperationCreateDTO operationDTO) {
        OperationEntity result = new OperationEntity();
        result.setCreationDate(operationDTO.getCreationDate());
        result.setAccount(accountRepository.findById(operationDTO.getAccountId())
                .orElseThrow(() -> new NotFoundDataException("Not found account by ID")));
        result.setAmount(operationDTO.getAmount());
        result.setCategory(categoryRepository.findById(operationDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundDataException("Not found category by ID")));
        result.setId(operationDTO.getId());
        result.setUserEntity(currentUserService.getUser());
        return result;
    }

    @Transactional
    public OperationEntity convertToEntityFromFullDTO(final FullOperationDTO operationDTO) {
        OperationEntity result = new OperationEntity();
        result.setCreationDate(operationDTO.getCreationDate());
        result.setAccount(accountRepository.findById(operationDTO.getAccountId())
                .orElseThrow(() -> new NotFoundDataException("Not found account by ID")));
        result.setAmount(operationDTO.getAmount());
        result.setCategory(categoryConverter.convertToEntity(operationDTO.getCategoryDTO()));
        result.setId(operationDTO.getId());
        result.setUserEntity(currentUserService.getUser());
        return result;
    }
}
