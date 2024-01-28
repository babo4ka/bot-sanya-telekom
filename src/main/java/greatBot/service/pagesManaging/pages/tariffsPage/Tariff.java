package greatBot.service.pagesManaging.pages.tariffsPage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Tariff {
    private String name;
    private String internet;
    private String tv;
    private String wink;
    private String mobile;
    private String rent;
    private String price;
    private String number;

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("<strong>&#128204;").append(name).append("</strong>").append("\n\n")
                .append("&#127760;").append("до ").append((int) Double.parseDouble(internet)).append(" Мбит/сек.").append("\n")
                .append(tv != null ? ("&#128250;" + countChannels(tv) + "\n") : "")
                .append(wink != null ? "WINK: " + wink + "\n" : "")
                .append(mobile != null ? "&#128222;" + mobile + "\n" : "")
                .append("&#9203;").append(rent).append("\n")
                .append("&#128176;").append(price).append("\n");

        return builder.toString();
    }

    private String countChannels(String channels){
        int channelsInt = (int) Double.parseDouble(channels);

        int end = channelsInt % 10;

        if(end == 1){
            return channelsInt + " канал";
        }else if(end>1 && end<5){
            return channelsInt + " канала";
        }else{
            return channelsInt + " каналов";
        }
    }
}
