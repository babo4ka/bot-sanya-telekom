package greatBot.service.botUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesDump {

    private Map<Long, List<Integer>> messagesToDelete = new HashMap<>();


    public void addMessageToDelete(long chatId, Integer messageId){
        messagesToDelete.computeIfAbsent(chatId, k -> new ArrayList<>());

        messagesToDelete.get(chatId).add(messageId);
    }

    public List<Integer> getMessagesToDelete(long chatId) throws Exception {
        if(messagesToDelete.get(chatId) == null){
            throw new Exception("ПУСТАААА)))");
        }

        List<Integer> toDelete = messagesToDelete.get(chatId);

        messagesToDelete.put(chatId, new ArrayList<>());

        return toDelete;
    }
}
