package greatBot.service.pagesManaging.pages.updateTariffsPage;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pages.tariffsPage.TariffsReader;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateTariffsPage implements Page {

    private final TariffsReader tariffsReader = TariffsReader.getInstance();
    private final MessageCreator creator = new MessageCreator();
    private final InlineKeyboardConstructor constructor = new InlineKeyboardConstructor();

    public UpdateTariffsPage() throws IOException {
    }

    @Override
    public List<Message> execute(Update update) throws Exception {
        List<Message> messages = new ArrayList<>();

        long chatId = update.hasMessage()?update.getMessage().getChatId():update.getCallbackQuery().getMessage().getChatId();

        tariffsReader.loadTariffs();

        constructor.reset();

        messages.add(creator.createTextMessage(
                constructor.addButton("в начало", "/start").nextRow().build(),
                chatId,
                "&#9989;Тарифы обновлены!",
                true
        ));

        return messages;
    }
}
