package greatBot.service.pagesManaging.pagesUtils;

import greatBot.service.pagesManaging.interfaces.Page;
import greatBot.service.pagesManaging.pages.consultpage.ConsultPage;
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
        put("/tariffs3", new TariffsPage(TariffsPage.THIRD));
        put("/tariffsMenu", new TariffsMenu());
        put("/consult", new ConsultPage());
    }};

    public PageManager() throws IOException{}

    public List<Message> execute(Update update, String pageName) throws Exception {
        System.out.println("execute " + pageName);
        return pages.get(pageName).execute(update);
    }

    public List<Message> executeWithArgs(Update update, String pageName, String[] args) throws IOException {
        System.out.println("execute w/args" + pageName);
        return pages.get(pageName).executeWithArgs(update, args);
    }
}
