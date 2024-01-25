package greatBot.service.pagesManaging.pages.tariffsPage;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import greatBot.service.pagesManaging.pagesUtils.ReplyKeyboardConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TariffsMenu implements Page {

    private final String [] groups = {"Интернет и ТВ", "Интернет, ТВ и мобильная связь",
            "GPON для: Залесный, Аракчино, Лагерная, Адмиралтейская слобода, Красная горка, Юдино",
            "GPON для ЖК \"Беседа\""};

    private final String mainText = "Выберите группу тарифов, чтобы ознакомиться: \n" +
            groups[0] + "\n" +
            groups[1] + "\n" +
            groups[2] + "\n" +
            groups[3] + "\n";

    private final String secondaryText = "Или вы можете посмотреть тарифы в мини-приложении, нажав на кнопку снизу";

    private MessageCreator creator = new MessageCreator();

    @Override
    public List<Message> execute(Update update) throws IOException {
        List<Message> messages = new ArrayList<>();

        InlineKeyboardConstructor inlineKeyboardConstructor = new InlineKeyboardConstructor();


        for(int group = 0; group<groups.length; group++){
            inlineKeyboardConstructor.addButton(groups[group], ("/tariffs" + (group + 1))).nextRow();
        }

        inlineKeyboardConstructor.addButton("вернуться в начало", "/start").nextRow();

        messages.add(creator.createTextMessage(
                inlineKeyboardConstructor.build(),
                update.getCallbackQuery().getMessage().getChatId(),
                mainText, true
        ));


        ReplyKeyboardConstructor replyKeyboardConstructor = new ReplyKeyboardConstructor();

        replyKeyboardConstructor.
                addWebAppButton("ПОСМОТРЕТЬ ТАРИФЫ", "https://genuine-haupia-09d816.netlify.app").nextRow();

        messages.add(creator.createTextMessage(
                replyKeyboardConstructor.build(),
                update.getCallbackQuery().getMessage().getChatId(),
                secondaryText, true
        ));


        return messages;
    }
}
