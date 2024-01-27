package greatBot.service.pagesManaging.pages.consultpage;

import greatBot.config.BotConfig;
import greatBot.service.botUtils.ConfigDataDistributor;
import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pages.tariffsPage.Tariff;
import greatBot.service.pagesManaging.pages.tariffsPage.TariffsReader;
import greatBot.service.pagesManaging.pagesUtils.InlineKeyboardConstructor;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("application.properties")
public class ConsultPage implements Page {

    private final String info = "Я передам александру, чтобы он с Вами связался!";

    private final MessageCreator creator = new MessageCreator();

    private final InlineKeyboardConstructor constructor = new InlineKeyboardConstructor();

    public ConsultPage() throws IOException {
    }

    @Override
    public List<Message> executeWithArgs(Update update, String[] args) throws IOException {
        long chatId = update.hasMessage()?update.getMessage().getChatId():
                update.getCallbackQuery().getMessage().getChatId();

        List<Message> messages = new ArrayList<>();

        constructor.reset();

        messages.add(creator.createTextMessage(
                constructor.addButton("в начало", "/start").nextRow().build(),
                chatId,
                info, true
        ));

        messages.add(messageForSanya(Integer.parseInt(args[0]),
                update.hasMessage()?update.getMessage().getChat().getUserName():
                update.getCallbackQuery().getMessage().getChat().getUserName(),

                update.hasMessage()?update.getMessage().getChat().getFirstName():
                        update.getCallbackQuery().getMessage().getChat().getFirstName(), args[1])
        );

        return messages;
    }

    private final ConfigDataDistributor distributor = ConfigDataDistributor.getInstance();
    private final String[] commonTexts = {"Консультация по тарифу \n", "Для пользователя \n"};
    private final TariffsReader reader = TariffsReader.getInstance();
    private Message<SendMessage> messageForSanya(int tariffNum, String userName, String firstName, String src){
        Tariff tariff = reader.getTariffByNumber(tariffNum);

        constructor.reset();

        String text = commonTexts[0] + tariff.getName() + "\n" +commonTexts[1] + firstName + "\n" +
                "Прислано из " + (src.equals("bot")?"бота":"приложения");

        return creator.createTextMessage(
                constructor.addURLButton("Открыть чат с пользователем", "https://t.me/" + userName).nextRow().build(),
                distributor.getOnwerId(),
                text,
                false
        );

    }
}
