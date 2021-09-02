package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.data.Role;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Natalia Nikonova
 */
@Service
public class TelegramAccountService {
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;
    private final MessageMenuService messageMenuService;
    private final CurrentUserService currentUserService;
    private final UserToAccountService userToAccountService;

    public TelegramAccountService(TelegramUserService telegramUserService,
                                  AccountService accountService,
                                  MessageMenuService messageMenuService,
                                  CurrentUserService currentUserService,
                                  UserToAccountService userToAccountService) {
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
        this.messageMenuService = messageMenuService;
        this.currentUserService = currentUserService;
        this.userToAccountService = userToAccountService;
    }

    public List<SendMessage> sendAllAccountsAndNew(long chatId) {
        telegramUserService.setBotState(BotState.CHOSE_ACCOUNT);
        Map<String, String> accountMap = new HashMap<>();
        accountService.getAllEntity()
                .forEach(account -> accountMap.put(String.valueOf(account.getId()),
                        String.format("%s (%s)",
                                account.getName(),
                                telegramUserService.getTelegramUserByUserId(userToAccountService.getByAccountAndRole(account,
                                        Role.OWNER).getUser().getName()).getUserName())));
        accountMap.put("new", "Создать новый");
        return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Выберите кошелек"),
                messageMenuService.getInlineMenuMessage(chatId, "или создайте новый:", accountMap));
    }

    public List<SendMessage> createAccount(long chatId) {
        telegramUserService.setBotState(BotState.CREATE_ACCOUNT);
        return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Введите имя кошелька:"));
    }

    public List<SendMessage> choseAccount(long chatId, String userAnswer) {
        try {
            long accountId = Long.parseLong(userAnswer);
            AccountDTO account = accountService.getAccountById(accountId);
            telegramUserService.setCurrentAccount(accountId);
            telegramUserService.setBotState(BotState.MENU);
            return List.of(messageMenuService.getMainMenuMessage(chatId,
                    String.format("Вы перешли в кошелёк <b>%s</b>\nБаланс: <b>%.2f</b>", account.getName(), account.getBalance())));
        } catch (NumberFormatException | NotFoundDataException | NullPointerException e) {
            List<SendMessage> result = sendAllAccountsAndNew(chatId);
            result.add(0, messageMenuService.getAskClickMessage(chatId));
            return result;
        }
    }

    public List<SendMessage> saveAccount(long chatId, String userAnswer) {
        AccountDTO newAccount = new AccountDTO();
        newAccount.setName(userAnswer);
        newAccount.setUserId(currentUserService.getUser().getId());
        newAccount.setBalance(new BigDecimal(0));
        AccountDTO account = accountService.create(newAccount);
        telegramUserService.setCurrentAccount(account.getId());
        telegramUserService.setBotState(BotState.MENU);
        return List.of(messageMenuService.getMainMenuMessage(chatId,
                String.format("Вы создали кошелёк %s и перешли в него, баланс %.2f", account.getName(), account.getBalance())));
    }

    public List<SendMessage> chosePerson(long chatId) {
        telegramUserService.setBotState(BotState.CHOSE_MEMBER);
        return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Пришлите контакт человека, которого хотите добавить в кошелёк"));
    }

    public List<SendMessage> sharedAccount(long chatId, Message message) {
        try {
            if (!message.hasContact()) {
                return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Вы не прислали контакт, попробуйте ещё раз"));
            }
            telegramUserService.setBotState(BotState.MENU);
            Contact contact = message.getContact();
            if (contact.getUserID() == null) {
                return List.of(messageMenuService.getMainMenuMessage(chatId, "Вы не можете добавить этого пользователя в кошелек, так как его нет у вас в контактах"));
            }
            userToAccountService.addUserToAccount(telegramUserService.getCurrentAccount().getId(), String.valueOf(contact.getUserID()));
            return List.of(messageMenuService.getMainMenuMessage(chatId,
                            String.format("Пользователь %s %s успешно добавлен в кошелек",
                                    contact.getFirstName(),
                                    contact.getLastName())),
                    messageMenuService.getMainMenuMessage(telegramUserService.getTelegramUserByUserId(String.valueOf(contact.getUserID())).getChatId(),
                            String.format("<b>%s</b> добавил вас в свой кошелек: <b>%s</b>",
                                    telegramUserService.getTelegramUserByUserId(currentUserService.getUser().getName()).getUserName(),
                                    telegramUserService.getCurrentAccount().getName())));
        } catch (PermissionDeniedException e) {
            return List.of(messageMenuService.getMainMenuMessage(chatId, "Вы не можете добавить пользователя в этот кошелек, так как не являетесь его владельцем"));
        } catch (NotFoundDataException e) {
            return List.of(messageMenuService.getMainMenuMessage(chatId, "Вы не можете добавить этого пользователя в кошелек, так как он не пользуется нашим ботом"));
        }
    }
}
