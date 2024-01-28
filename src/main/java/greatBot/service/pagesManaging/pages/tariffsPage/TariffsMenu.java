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

    private final String [] groupEmojis = {"&#49;&#65039;&#8419;", "&#50;&#65039;&#8419;",
            "&#51;&#65039;&#8419;", "&#52;&#65039;&#8419;"};

    private final String warning = "&#8252;После нажатия на кнопку \"консультация по всем тарифам\", " +
            "Александр получит уведомление о том, что с Вами необходимо связаться и получит ссылку, чтобы написать Вам\n" +
            "&#8252;Убедитесь, что у Вас указано имя пользователя в телеграмм(username) или я не смогу сообщить Александру с кем надо связаться";

    private final String mainText = "&#10067;Выберите группу тарифов, чтобы ознакомиться: \n\n" +
            groupEmojis [0] + groups[0] + "\n\n" +
            groupEmojis [1] + groups[1] + "\n\n" +
            groupEmojis [2] + groups[2] + "\n\n" +
            groupEmojis [3] + groups[3] + "\n\n" +
            warning;

    private final String GPON_info = "GPON - Gigabit Passive Optical Network - " +
            "«Гигабитная пассивная оптическая сеть»";

    private final String secondaryText = "Или вы можете посмотреть тарифы в мини-приложении, нажав на кнопку снизу&#9196;";

    private MessageCreator creator = new MessageCreator();

    private final InlineKeyboardConstructor inlineKeyboardConstructor = new InlineKeyboardConstructor();

    private final ReplyKeyboardConstructor replyKeyboardConstructor = new ReplyKeyboardConstructor();

    @Override
    public List<Message> execute(Update update) throws IOException {
        List<Message> messages = new ArrayList<>();

        inlineKeyboardConstructor.reset();
        replyKeyboardConstructor.reset();


        for(int group = 0; group<groups.length; group++){
            inlineKeyboardConstructor.addButton(groups[group], ("/tariffs" + (group + 1))).nextRow();
        }

        inlineKeyboardConstructor.addButton("консультация по всем тарифам", "/consult all bot").nextRow();

        inlineKeyboardConstructor.addButton("вернуться в начало", "/start").nextRow();

        messages.add(creator.createTextMessage(
                inlineKeyboardConstructor.build(),
                update.getCallbackQuery().getMessage().getChatId(),
                mainText, true
        ));




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
