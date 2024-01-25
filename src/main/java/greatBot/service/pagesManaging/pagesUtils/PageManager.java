package greatBot.service.pagesManaging.pagesUtils;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pages.startPage.StartPage;
import greatBot.service.pagesManaging.pages.tariffsPage.TariffsMenu;
import greatBot.service.pagesManaging.pages.tariffsPage.TariffsPage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageManager {

    private Map<String, Page> pages = new HashMap<>(){{
        put("/start", new StartPage());
        put("/tariffs1", new TariffsPage(TariffsPage.FIRST));
        put("/tariffs2", new TariffsPage(TariffsPage.SECOND));
        put("/tariffsMenu", new TariffsMenu());
    }};

    public List<Message> execute(Update update, String pageName) throws IOException {
        System.out.println(pageName);
        return pages.get(pageName).execute(update);
    }
}
