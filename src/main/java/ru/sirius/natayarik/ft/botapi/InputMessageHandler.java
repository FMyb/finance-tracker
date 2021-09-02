package ru.sirius.natayarik.ft.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

/**
 * @author Natalia Nikonova
 */
public interface InputMessageHandler {
   List<SendMessage> handleMessage(Message message, long chatId);
   default List<SendMessage> handleCallbackQuery(CallbackQuery data, long chatId) {
      Message message = new Message();
      message.setText(data.getData());
      return handleMessage(message, chatId);
   }
   List<BotState> getOperatedState();
}
