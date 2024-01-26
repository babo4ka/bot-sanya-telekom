package greatBot.service;

import greatBot.config.BotConfig;
import greatBot.service.botUtils.ConfigDataDistributor;
import greatBot.service.botUtils.MessagesDump;
import greatBot.service.pagesManaging.pagesUtils.Message;
import greatBot.service.pagesManaging.pagesUtils.PageManager;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.webapp.AnswerWebAppQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class BotSanya extends TelegramLongPollingBot {

    final BotConfig config;

    private ConfigDataDistributor distributor = ConfigDataDistributor.getInstance();

    public BotSanya(BotConfig config) throws IOException {
        this.config = config;

        distributor.setConfig(config);
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
        try {
            processMessage(update, update.hasMessage()?
                    MessageType.MESSAGE:MessageType.CALLBACK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum MessageType{
        MESSAGE,
        CALLBACK
    }

    private void processMessage(Update update, MessageType type) throws IOException {
        String[] gotData = null;
        List<Message> executedPages;

        long chatId = update.hasMessage()?update.getMessage().getChatId()
                :update.getCallbackQuery().getMessage().getChatId();

        switch (type){
            case MESSAGE -> gotData = update.getMessage().getWebAppData()==null?
                    update.getMessage().getText().split(" "):
                    update.getMessage().getWebAppData().getData().split(" ");

            case CALLBACK -> gotData = update.getCallbackQuery().getData().split(" ");
        }

        if(gotData.length == 1){
            executedPages = pageManager.execute(update, gotData[0]);
        }else{
            executedPages = pageManager.executeWithArgs(update, gotData[0],
                    Arrays.copyOfRange(gotData, 1, gotData.length));
        }


        try{
            deletePrevious(chatId, dump.getMessagesToDelete(chatId));
        }catch (Exception e){
            System.err.println(e);
        }finally {
            executedPages.forEach(page -> {
                org.telegram.telegrambots.meta.api.objects.Message sentMessage = null;

                if(page.getSend().getClass().equals(SendMessage.class)){
                    try {
                        sentMessage = execute((SendMessage) page.getSend());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }else if(page.getSend().getClass().equals(SendPhoto.class)){
                    try {
                        sentMessage = execute((SendPhoto) page.getSend());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(page.isMarkable()) {
                    assert sentMessage != null;
                    addToDump(chatId, sentMessage.getMessageId());
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
