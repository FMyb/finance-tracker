package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.services.*;

import java.util.List;

/**
 * @author Natalia Nikonova
 */
@Component
public class AccountHandler implements InputMessageHandler {
    private final TelegramUserService telegramUserService;
    private final TelegramAccountService telegramAccountService;

    public AccountHandler(TelegramUserService telegramUserService, TelegramAccountService telegramAccountService) {
        this.telegramUserService = telegramUserService;
        this.telegramAccountService = telegramAccountService;
    }

    @Override
    public List<SendMessage> handleMessage(Message message, long chatId) {
        BotState state = telegramUserService.getBotState();
        switch (state) {
            case CHANGE_ACCOUNT:
                return telegramAccountService.sendAllAccountsAndNew(chatId);
            case CHOSE_ACCOUNT:
                if (message.getText().equals("new")) {
                    return telegramAccountService.createAccount(chatId);
                } else {
                    return telegramAccountService.choseAccount(chatId, message.getText());
                }
            case CREATE_ACCOUNT:
                return telegramAccountService.saveAccount(chatId, message.getText());
            case SHARED_ACCOUNT:
                return telegramAccountService.chosePerson(chatId);
            case CHOSE_MEMBER:
                return telegramAccountService.sharedAccount(chatId, message);
            default:
                return null;
        }
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.CHOSE_ACCOUNT, BotState.CHANGE_ACCOUNT, BotState.CREATE_ACCOUNT, BotState.CHOSE_MEMBER, BotState.SHARED_ACCOUNT);
    }
}
