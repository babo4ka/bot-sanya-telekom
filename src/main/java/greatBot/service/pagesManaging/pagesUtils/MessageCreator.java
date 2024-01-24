package greatBot.service.pagesManaging.pagesUtils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class MessageCreator {

    public Message<SendMessage> createTextMessage(
            ReplyKeyboard keyBoard,
            long chatId,
            String text,
            boolean markable
    ){
        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");

        sendMessage.setReplyMarkup(keyBoard);

        return new Message(sendMessage, markable);
    }
}
