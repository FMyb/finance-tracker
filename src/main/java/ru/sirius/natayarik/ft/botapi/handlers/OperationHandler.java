package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.services.*;

import java.util.List;

/**
 * @author Natalia Nikonova
 */
@Component
public class OperationHandler implements InputMessageHandler {
    private final TelegramUserService telegramUserService;
    private final TelegramOperationService telegramOperationService;
    private final MessageMenuService messageMenuService;

    public OperationHandler(TelegramUserService telegramUserService,
                            TelegramOperationService telegramOperationService,
                            MessageMenuService messageMenuService) {
        this.telegramUserService = telegramUserService;
        this.telegramOperationService = telegramOperationService;
        this.messageMenuService = messageMenuService;
    }

    @Override
    public List<SendMessage> handleMessage(Message message, long chatId) {
        BotState state = telegramUserService.getBotState();
        switch (state) {
            case CREATE_OPERATIONS:
                return telegramOperationService.createOperation(chatId);
            case ASK_AMOUNT:
                return telegramOperationService.addAmount(chatId, message.getText());
            case ASK_TYPE:
                return telegramOperationService.choseType(chatId, message.getText());
            case ASK_INCOME_CATEGORY:
            case ASK_OUTCOME_CATEGORY:
                return telegramOperationService.choseCategoryAndSaveOperation(chatId, message.getText());
            case GET_OPERATIONS:
                return telegramOperationService.sendOperations(chatId);
            default:
                return List.of(messageMenuService.getMainMenuMessage(chatId, "Ой"));
        }
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.ASK_AMOUNT, BotState.ASK_TYPE, BotState.ASK_INCOME_CATEGORY, BotState.ASK_OUTCOME_CATEGORY, BotState.GET_OPERATIONS, BotState.CREATE_OPERATIONS);
    }
}
