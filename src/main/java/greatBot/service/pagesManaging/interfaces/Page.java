package greatBot.service.pagesManaging.interfaces;

import greatBot.service.pagesManaging.pagesUtils.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public interface Page {
    default List<Message> execute(Update update) throws IOException{
        return null;
    }
    default List<Message> executeWithArgs(Update update, String[] args) throws IOException{
        return null;
    }
}
