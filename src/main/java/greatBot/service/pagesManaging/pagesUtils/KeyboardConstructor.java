package greatBot.service.pagesManaging.pagesUtils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class KeyboardConstructor {

    private List<List<InlineKeyboardButton>> buttonsMatrix = new ArrayList<>();
    private List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

    public KeyboardConstructor addButton(
            String text,
            String callback
    ){
        this.buttonsRow.add(InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callback).build()
        );

        return this;
    }

    public KeyboardConstructor addWebAppButton(
            String text,
            String url
    ){
        this.buttonsRow.add(InlineKeyboardButton.builder()
                .text(text)
                .webApp(WebAppInfo.builder().url(url).build()).build());

        return this;
    }

    public KeyboardConstructor nextRow(){
        this.buttonsMatrix.add(this.buttonsRow);
        this.buttonsRow = new ArrayList<>();
        return this;
    }

    public KeyboardConstructor reset(){
        this.buttonsMatrix = new ArrayList<>();
        this.buttonsRow = new ArrayList<>();
        return this;
    }

    public List<List<InlineKeyboardButton>> build(){
        return this.buttonsMatrix;
    }


}
