package greatBot.service.pagesManaging.pagesUtils;

import lombok.Getter;
import lombok.Setter;

public class Message <SENDTYPE> {

    @Getter @Setter
    private SENDTYPE send;
    @Getter @Setter
    private boolean markable;

    public Message(SENDTYPE send, boolean markable) {
        this.send = send;
        this.markable = markable;
    }


}
