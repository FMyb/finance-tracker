package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.*;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.UserRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;
import ru.sirius.natayarik.ft.services.AccountBalanceService;

import javax.transaction.Transactional;
import java.math.BigDecimal;


/**
 * @author Natalia Nikonova
 */
@Component
public class AccountConverter {
    private final AccountBalanceService accountBalanceService;
    private final UserToAccountRepository userToAccountRepository;

    public AccountConverter(UserRepository userRepository, AccountBalanceService accountBalanceService, UserToAccountRepository userToAccountRepository) {
        this.accountBalanceService = accountBalanceService;
        this.userToAccountRepository = userToAccountRepository;
    }

    @Transactional
    public AccountDTO convertToDTO(AccountEntity accountEntity) {
        AccountDTO result = new AccountDTO();
        result.setId(accountEntity.getId());
        result.setName(accountEntity.getName());
        result.setUserId(userToAccountRepository.findByAccountAndRole(accountEntity, Role.OWNER).getUser().getId());
        result.setCurrency(Currency.RUSSIAN_RUBLE);
        result.setBalance(accountEntity.getBalance());

        result.setIncome(accountBalanceService.getSumByType(accountEntity.getId(), Type.INCOME));
        result.setOutcome(accountBalanceService.getSumByType(accountEntity.getId(), Type.OUTCOME));
        return result;
    }

    public AccountEntity convertToEntity(AccountDTO accountDTO) {
        AccountEntity result = new AccountEntity();
        result.setId(accountDTO.getId());
        result.setName(accountDTO.getName());
        result.setBalance(accountDTO.getBalance());
        return result;
    }

    public AccountEntity convertFromCreateDTO(AccountCreateDTO accountCreateDTO) {
        AccountEntity result = new AccountEntity();
        result.setBalance(new BigDecimal(0));
        result.setName(accountCreateDTO.getName());
        return result;
    }
}
