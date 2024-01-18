package greatBot.service.pagesManaging.pages.tariffsPage;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pagesUtils.KeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TariffsPage implements Page {

    public static final int FIRST = 1;
    public static final int SECOND = 2;

    private final int pageNum;
    public TariffsPage(int pageNum){
        this.pageNum = pageNum;
    }

    private TariffsReader reader = new TariffsReader();

    private MessageCreator creator = new MessageCreator();
    @Override
    public List<Message> execute(Update update) throws IOException {
        List<Tariff> tariffs = reader.readTariffs(pageNum);
        List<Message> messages = new ArrayList<>();
        StringBuilder textBuilder = new StringBuilder();

        KeyboardConstructor constructor = new KeyboardConstructor();


        tariffs.forEach(tariff -> {
            constructor.addButton(tariff.getName(), ("/consult " + (int)Double.parseDouble(tariff.getNumber()))).nextRow();
            textBuilder.append(tariff);
        });

        switch (pageNum){
            case FIRST -> {
                constructor
                        .addButton("в начало", "/start")
                        .addButton("--->", "/tariffs2")
                        .nextRow();
            }

            case SECOND -> {
                constructor
                        .addButton("<---", "/tariffs1")
                        .addButton("в начало", "/start")
                        .nextRow();
            }
        }


        messages.add(creator.createTextMessage(
                constructor.build(),
                update.getCallbackQuery().getMessage().getChatId(),
                textBuilder.toString(),
                true
        ));

        return messages;
    }
}
