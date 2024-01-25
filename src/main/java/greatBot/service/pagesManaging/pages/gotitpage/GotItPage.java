package greatBot.service.pagesManaging.pages.gotitpage;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GotItPage implements Page {

    private final String info = "Я передам александру, чтобы он с Вами связался!";

    private MessageCreator creator = new MessageCreator();

    private InlineKeyboardConstructor constructor = new InlineKeyboardConstructor();

    @Override
    public List<Message> execute(Update update) throws IOException {
        long chatId = update.hasMessage()?update.getMessage().getChatId():
                update.getCallbackQuery().getMessage().getChatId();

        List<Message> messages = new ArrayList<>();

        constructor.reset();

        messages.add(creator.createTextMessage(
                constructor.addButton("в начало", "/start").nextRow().build(),
                chatId,
                info, true
        ));

        return messages;
    }
}
