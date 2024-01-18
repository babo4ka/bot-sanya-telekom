package greatBot.service;

import greatBot.config.BotConfig;
import greatBot.service.botUtils.MessagesDump;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.PageManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class BotSanya extends TelegramLongPollingBot {

    final BotConfig config;

    public BotSanya(BotConfig config){
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private final PageManager pageManager = new PageManager();

    private final MessagesDump dump = new MessagesDump();

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().getText().equals("/start")){
            List<Message> executedPages;
            try {
                executedPages = pageManager.execute(update, "/start");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            executedPages.forEach(page -> {
                if(page.getSend().getClass().equals(SendMessage.class)){
                    try {
                        org.telegram.telegrambots.meta.api.objects.Message
                            sentMessage = execute((SendMessage) page.getSend());
                        if(page.isMarkable())
                            addToDump(update.getMessage().getChatId(),
                                    sentMessage.getMessageId());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }else if(page.getSend().getClass().equals(SendPhoto.class)){
                    try {
                        execute((SendPhoto) page.getSend());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        if(update.hasCallbackQuery()){
            List<Message> executedPages;
            try {
                executedPages = pageManager.execute(update, update.getCallbackQuery().getData());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                deletePrevious(update.getCallbackQuery().getMessage().getChatId(),
                        dump.getMessagesToDelete(update.getCallbackQuery().getMessage().getChatId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            executedPages.forEach(page -> {
                if(page.getSend().getClass().equals(SendMessage.class)){
                    try {
                        org.telegram.telegrambots.meta.api.objects.Message
                                sentMessage = execute((SendMessage) page.getSend());
                        if(page.isMarkable())
                            addToDump(update.getCallbackQuery().getMessage().getChatId(),
                                    sentMessage.getMessageId());

                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }else if(page.getSend().getClass().equals(SendPhoto.class)){
                    try {
                        org.telegram.telegrambots.meta.api.objects.Message
                                sentMessage = execute((SendPhoto) page.getSend());
                        if(page.isMarkable())
                            addToDump(update.getCallbackQuery().getMessage().getChatId(),
                                    sentMessage.getMessageId());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }


    private void addToDump(long chatId, Integer messageId){
        dump.addMessageToDelete(chatId, messageId);
    }

    private void deletePrevious(long chatId, List<Integer> messageIds){
        messageIds.forEach(md -> {
            DeleteMessage deleteMessage = DeleteMessage.builder().messageId(md).chatId(chatId).build();
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
