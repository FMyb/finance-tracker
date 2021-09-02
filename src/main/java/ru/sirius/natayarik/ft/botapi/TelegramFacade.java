package ru.sirius.natayarik.ft.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sirius.natayarik.ft.botapi.handlers.DefaultHandler;
import ru.sirius.natayarik.ft.services.CurrentUserService;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.Collections;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@Component
public class TelegramFacade {
    private final List<InputMessageHandler> handlers;
    private final TelegramUserService telegramUserService;
    private final CurrentUserService currentUserService;
    private final MessageMenuService messageMenuService;
    private final DefaultHandler defaultHandler;

    public TelegramFacade(List<InputMessageHandler> handlers, TelegramUserService telegramUserService, CurrentUserService currentUserService, MessageMenuService messageMenuService, DefaultHandler defaultHandler) {
        this.handlers = handlers;
        this.telegramUserService = telegramUserService;
        this.currentUserService = currentUserService;
        this.messageMenuService = messageMenuService;
        this.defaultHandler = defaultHandler;
    }

    public List<SendMessage> handleUpdate(Update update) {
        if (update.hasMessage() && (update.getMessage().hasText() | (update.getMessage().hasContact() && telegramUserService.getBotState().equals(BotState.CHOSE_MEMBER)))) {
            Message message = update.getMessage();
            String userId = String.valueOf(message.getFrom().getId());
            currentUserService.setUser(userId);
            telegramUserService.create(message.getFrom(), message.getChatId());
            BotState state = telegramUserService.getBotState();
            return getHandlerByState(state).handleMessage(message, message.getChatId());
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String userId = String.valueOf(callbackQuery.getFrom().getId());
            currentUserService.setUser(userId);
            telegramUserService.create(callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
            BotState state = telegramUserService.getBotState();
            return getHandlerByState(state).handleCallbackQuery(callbackQuery, callbackQuery.getMessage().getChatId());
        } else {
            return Collections.emptyList();
        }
    }

    private InputMessageHandler getHandlerByState(BotState state) {
        return handlers.stream()
                .filter(handler -> (handler.getOperatedState() != null) && (handler.getOperatedState().contains(state)))
                .findAny()
                .orElse(defaultHandler);
    }
}
