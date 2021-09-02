package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.TelegramUserRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

/**
 * @author Natalia Nikonova
 */
@Service
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;
    private final UserToAccountRepository userToAccountRepository;

    public TelegramUserService(TelegramUserRepository telegramUserRepository, AccountRepository accountRepository, CurrentUserService currentUserService, UserToAccountRepository userToAccountRepository) {
        this.telegramUserRepository = telegramUserRepository;
        this.accountRepository = accountRepository;
        this.currentUserService = currentUserService;
        this.userToAccountRepository = userToAccountRepository;
    }

    public BotState getBotState() {
        return telegramUserRepository.findByUserId(currentUserService.getUser().getName()).getState();
    }

    public TelegramUserEntity create(User user, long chatId) {
        TelegramUserEntity newUser = new TelegramUserEntity();
        newUser.setUserId(currentUserService.getUser().getName());
        newUser.setState(BotState.START);
        newUser.setAccountEntity(userToAccountRepository.findAllByUser(currentUserService.getUser()).get(0).getAccount());
        newUser.setChatId(chatId);
        if (user.getUserName() != null) {
            newUser.setUserName(user.getUserName());
        } else if (user.getLastName() != null) {
            newUser.setUserName(user.getFirstName() + " " + user.getLastName());
        } else {
            newUser.setUserName(user.getFirstName());
        }
        TelegramUserEntity oldUser = telegramUserRepository.findByUserId(currentUserService.getUser().getName());
        if (oldUser == null) {
            return telegramUserRepository.save(newUser);
        } else {
            return oldUser;
        }
    }

    public void setBotState(BotState state) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(currentUserService.getUser().getName());
        user.setState(state);
        telegramUserRepository.save(user);
    }

    public void setCurrentAccount(long accountId) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(currentUserService.getUser().getName());
        user.setAccountEntity(accountRepository.findById(accountId).orElseThrow(RuntimeException::new));
        telegramUserRepository.save(user);
    }

    public AccountEntity getCurrentAccount() {
        return telegramUserRepository.findByUserId(currentUserService.getUser().getName()).getAccountEntity();
    }

    public TelegramUserEntity getTelegramUserByUserId(String userId) {
        return telegramUserRepository.findByUserId(userId);
    }
}
