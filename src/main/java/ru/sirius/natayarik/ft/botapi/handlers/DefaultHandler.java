package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author Natalia Nikonova
 */
@Component
public class DefaultHandler implements InputMessageHandler {
   @Override
   public List<SendMessage> handleMessage(Message message, long chatId) {
      SendMessage result = new SendMessage();
      result.setChatId(String.valueOf(chatId));
      result.setText("Пожалуйста, пользуйтесь лишь теми функциями, которые сейчас вам доступны через кнопки");
      return null;
   }

   @Override
   public List<BotState> getOperatedState() {
      return Collections.emptyList();
   }
}
