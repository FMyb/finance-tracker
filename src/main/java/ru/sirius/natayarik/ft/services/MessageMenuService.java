package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Natalia Nikonova
 */
@Service
public class MessageMenuService {
   public SendMessage getInlineMenuMessage(final long chatId, final String textMessage, Map<String, String> buttons) {
      InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboard(buttons);
      final SendMessage inlineMenuMessage =
              createMessageWithKeyboard(chatId, textMessage, inlineKeyboardMarkup);

      return inlineMenuMessage;
   }

   public SendMessage getWithoutMenuMessage(final long chatId, final String textMessage) {
      ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
      final SendMessage withoutMenuMessage =
              createMessageWithKeyboard(chatId, textMessage, replyKeyboardRemove);

      return withoutMenuMessage;
   }

   public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
      final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
      final SendMessage mainMenuMessage =
              createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
      mainMenuMessage.setParseMode("HTML");
      return mainMenuMessage;
   }

   private ReplyKeyboardMarkup getMainMenuKeyboard() {
      final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
      replyKeyboardMarkup.setSelective(true);
      replyKeyboardMarkup.setResizeKeyboard(true);

      List<KeyboardRow> keyboard = new ArrayList<>();
      KeyboardRow row1 = new KeyboardRow();
      row1.add(new KeyboardButton("Создать операцию"));
      row1.add(new KeyboardButton("Посмотреть мои операции"));
      keyboard.add(row1);
      KeyboardRow row2 = new KeyboardRow();
      row2.add(new KeyboardButton("Сменить кошелек"));
      row2.add(new KeyboardButton("Расшарить текущий кошелек"));
      keyboard.add(row2);
      replyKeyboardMarkup.setKeyboard(keyboard);
      return replyKeyboardMarkup;
   }

   private InlineKeyboardMarkup getInlineKeyboard(Map<String, String> buttons) {
      InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
      List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
      List<String> keys = new ArrayList<>(buttons.keySet());
      Collections.sort(keys);
      for (String name: keys) {
         List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
         InlineKeyboardButton button = new InlineKeyboardButton();
         button.setCallbackData(name);
         button.setText(buttons.get(name));
         keyboardButtonsRow.add(button);
         rowList.add(keyboardButtonsRow);
      }
      inlineKeyboardMarkup.setKeyboard(rowList);
      return inlineKeyboardMarkup;
   }

   private SendMessage createMessageWithKeyboard(final long chatId,
                                                 String textMessage,
                                                 final ReplyKeyboard replyKeyboardMarkup) {
      final SendMessage sendMessage = new SendMessage();
      sendMessage.enableMarkdown(true);
      sendMessage.setChatId(String.valueOf(chatId));
      sendMessage.setText(textMessage);
      if (replyKeyboardMarkup != null) {
         sendMessage.setReplyMarkup(replyKeyboardMarkup);
      }
      return sendMessage;
   }

   public SendMessage getAskClickMessage(long chatId) {
      return getWithoutMenuMessage(chatId, "Пожалуйста, не надо ничего вводить - нажмите на одну из предложенных кнопок");
   }

   public SendMessage getNotChoseAccountMessage(long chatId) {
      return getMainMenuMessage(chatId, "Функция недоступна, так как сейчас у вас нет кошельков. Чтобы создать новый, нажмите в главном меню на 'Сменить кошелек'");
   }
}
