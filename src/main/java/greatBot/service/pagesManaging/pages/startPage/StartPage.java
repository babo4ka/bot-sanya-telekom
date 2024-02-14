package greatBot.service.pagesManaging.pages.startPage;

import greatBot.service.botUtils.UserInfoSaver;
import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartPage implements Page {

    private final String info = "&#128129;Ведущий специалист по домашним услугам Александр\n" +
            "Агент группы продаж, работаю в сфере пятый год, суммарно через меня подключилось более 1000 квартир\n\n" +
            "&#128205;Веду работу по Республике Татарстан\n\n" +
            "&#128222;Можете позвонить мне по номеру <strong>8 - 917 - 292 - 84 - 45</strong>\n" +
            "&#128233;Или написать мне в телеграме @aleksandr_rst_RT\n\n" +
            "&#129470;А я его бот-помощник:) Через меня вы вкратце можете узнать интересующую Вас информацию\n" +
            "&#128064;Могу показать Вам тарифы или же сообщу Александру, чтобы он связался с Вами и обсудил все тарифы\n\n" +
            "&#8252;Убедитесь, что у Вас указано имя пользователя в телеграмм(username) или я не смогу сообщить Александру с кем надо связаться";

    private final MessageCreator creator = new MessageCreator();
    private final InlineKeyboardConstructor constructor = new InlineKeyboardConstructor();
    private final UserInfoSaver saver = new UserInfoSaver();

    @Override
    public List<Message> execute(Update update) throws IOException {
        List<Message> messages = new ArrayList<>();

        long chatId = update.hasMessage()?update.getMessage().getChatId():update.getCallbackQuery().getMessage().getChatId();

        String userName = update.hasMessage()?
               (update.getMessage().getChat().getUserName()==null?null:update.getMessage().getChat().getUserName()):
                (update.getCallbackQuery().getMessage().getChat().getUserName()==null?null:update.getCallbackQuery().getMessage().getChat().getUserName());

        saver.createUser(chatId, userName);

        constructor.reset();

        messages.add(creator.createTextMessage(
                constructor.addButton("ознакомиться с тарифами", "/tariffsMenu").nextRow().build(),
                chatId,
                info,
                true
        ));


        return messages;
    }
}
