package greatBot.service.pagesManaging.pages.tariffsPage;

import greatBot.service.botUtils.UserInfoSaver;
import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TariffsPage implements Page {

    public static final int FIRST = 1;
    public static final int SECOND = 2;
    public static final int THIRD = 3;

    private final String warning = "&#8252;После нажатия на кнопку \"Получить консультацию\", " +
            "Александр получит уведомление о том, что с Вами необходимо связаться и получит ссылку, чтобы написать Вам\n" +
            "&#8252;Убедитесь, что у Вас указано имя пользователя в телеграмм(username) или я не смогу сообщить Александру с кем надо связаться";

    private final int groupNum;
    public TariffsPage(int pageNum) throws IOException {
        this.groupNum = pageNum;
    }

    private final TariffsReader reader = TariffsReader.getInstance();

    private final MessageCreator creator = new MessageCreator();
    private final InlineKeyboardConstructor constructor = new InlineKeyboardConstructor();
    private final UserInfoSaver saver = new UserInfoSaver();


    @Override
    public List<Message> execute(Update update) throws Exception {
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        List<Tariff> tariffs = reader.getTariffsByGroup(groupNum);
        List<Message> messages = new ArrayList<>();

        constructor.reset();

        saver.updateInfo(chatId, groupNum+1);

        tariffs.forEach(tariff -> {
            constructor.addButton("Получить консультацию",
                    ("/consult " + (int)Double.parseDouble(tariff.getNumber()) + " bot")).nextRow();

            String tariffText = tariff + "\n\n" + warning;

            messages.add(creator.createTextMessage(
                    constructor.build(),
                    chatId,
                    tariffText,
                    true
            ));

            constructor.reset();
        });

        switch (groupNum){
            case FIRST -> constructor
                    .addButton("в меню", "/tariffsMenu")
                    .addButton("--->", "/tariffs2")
                    .nextRow();

            case SECOND -> constructor
                    .addButton("<---", "/tariffs1")
                    .addButton("в меню", "/tariffsMenu")
                    .addButton("--->", "/tariffs3")
                    .nextRow();

            case THIRD -> constructor
                    .addButton("<---", "/tariffs2")
                    .addButton("в меню", "/tariffsMenu")
                    .nextRow();
        }


        messages.add(creator.createTextMessage(
                constructor.build(),
                chatId,
                "Навигация по группам тарифов",
                true
        ));

        return messages;
    }
}
