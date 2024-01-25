package greatBot.service.pagesManaging.pagesUtils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardConstructor {

    private List<KeyboardRow> keyboard = new ArrayList<>();

    private KeyboardRow row = new KeyboardRow();

    public ReplyKeyboardConstructor addButton(String text){
        row.add(new KeyboardButton(text));
        return this;
    }

    public ReplyKeyboardConstructor addWebAppButton(String text, String url){
        row.add(KeyboardButton.builder().text(text).webApp(
                WebAppInfo.builder().url(url).build()
        ).build());

        return this;
    }

    public ReplyKeyboardConstructor nextRow(){
        keyboard.add(row);
        row = new KeyboardRow();
        return this;
    }

    public ReplyKeyboardConstructor reset(){
        keyboard = new ArrayList<>();
        row = new KeyboardRow();
        return this;
    }

    public ReplyKeyboardMarkup build(){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(this.keyboard);
        return keyboard;
    }
}
