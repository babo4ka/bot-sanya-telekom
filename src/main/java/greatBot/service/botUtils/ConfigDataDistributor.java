package greatBot.service.botUtils;

import greatBot.config.BotConfig;
import lombok.Getter;
import lombok.Setter;

public class ConfigDataDistributor {

    private static ConfigDataDistributor instance;

    private ConfigDataDistributor(){}

    public static ConfigDataDistributor getInstance(){
        if(instance == null){
            instance = new ConfigDataDistributor();
        }

        return instance;
    }

    @Setter
    private BotConfig config;

    public long getOnwerId(){return config.getOwnerId();}
    public long getSubOwnerId(){return config.getSubOwner();}
}
