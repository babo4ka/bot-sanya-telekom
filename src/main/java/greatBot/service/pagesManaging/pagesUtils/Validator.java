package greatBot.service.pagesManaging.pagesUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
public class Validator {

    @Value("${bot.owner}")
    private long ownerId;
    @Value(("${bot.subowner}"))
    private long subOwnerId;

    private static Validator instance;

    public static Validator getInstance() {
        if(instance == null){
            instance = new Validator();
        }
        return instance;
    }

    public boolean validUser(long userChatId){
        return userChatId == ownerId || userChatId == subOwnerId;
    }
}
