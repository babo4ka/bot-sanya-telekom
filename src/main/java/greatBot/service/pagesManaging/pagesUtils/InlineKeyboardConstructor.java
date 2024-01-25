package greatBot.service.pagesManaging.pagesUtils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardConstructor {

    private List<List<InlineKeyboardButton>> buttonsMatrix = new ArrayList<>();
    private List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

    public InlineKeyboardConstructor addButton(
            String text,
            String callback
    ){
        this.buttonsRow.add(InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callback).build()
        );

        return this;
    }

    public InlineKeyboardConstructor addURLButton(
            String text,
            String url
    ){
        this.buttonsRow.add(InlineKeyboardButton.builder()
                .text(text)
                .url(url).build()
        );

        return this;
    }

    public InlineKeyboardConstructor addWebAppButton(
            String text,
            String url
    ){
        this.buttonsRow.add(InlineKeyboardButton.builder()
                .text(text)
                .webApp(WebAppInfo.builder().url(url).build()).build());

        return this;
    }

    public InlineKeyboardConstructor nextRow(){
        this.buttonsMatrix.add(this.buttonsRow);
        this.buttonsRow = new ArrayList<>();
        return this;
    }

    public InlineKeyboardConstructor reset(){
        this.buttonsMatrix = new ArrayList<>();
        this.buttonsRow = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkup build(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(this.buttonsMatrix);
        return keyboardMarkup;
    }

}
