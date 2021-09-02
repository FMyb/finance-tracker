package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@Component
public class MenuHandler implements InputMessageHandler {
    private final MessageMenuService messageMenuService;
    private final OperationHandler operationHandler;
    private final AccountHandler accountHandler;
    private final TelegramUserService telegramUserService;

    public MenuHandler(MessageMenuService messageMenuService, OperationHandler operationHandler, AccountHandler accountHandler, TelegramUserService telegramUserService) {
        this.messageMenuService = messageMenuService;
        this.operationHandler = operationHandler;
        this.accountHandler = accountHandler;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public List<SendMessage> handleMessage(Message message, long chatId) {
        BotState state = telegramUserService.getBotState();
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case START:
                telegramUserService.setBotState(BotState.MENU);
                reply.add(messageMenuService.getMainMenuMessage(chatId, "Добро пожаловать! Воспользуйтесь главным меню."));
                break;
            case MENU:
                switch (message.getText()) {
                    case "Создать операцию":
                        telegramUserService.setBotState(BotState.CREATE_OPERATIONS);
                        reply = operationHandler.handleMessage(message, chatId);
                        break;
                    case "Посмотреть мои операции":
                        telegramUserService.setBotState(BotState.GET_OPERATIONS);
                        reply = operationHandler.handleMessage(message, chatId);
                        break;
                    case "Сменить кошелек":
                        telegramUserService.setBotState(BotState.CHANGE_ACCOUNT);
                        reply = accountHandler.handleMessage(message, chatId);
                        break;
                    case "Расшарить текущий кошелек":
                        telegramUserService.setBotState(BotState.SHARED_ACCOUNT);
                        reply = accountHandler.handleMessage(message, chatId);
                        break;
                    default:
                        //SendMessage m = new SendMessage();
                        reply = List.of(messageMenuService.getMainMenuMessage(chatId, "Такой команды не существует, но можете просто что-то писать, я вас слушаю..."));
                        break;
                }
                break;
        }
        return reply;
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.START, BotState.MENU);
    }
}
