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
    private String router;
    private String pristavka;
    private String discount;
    private String price;
    private String number;

    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("<strong>&#128204;").append(name).append("</strong>").append("\n\n")
                .append("&#127760;").append("до ").append((int) Double.parseDouble(internet)).append(" Мбит/сек.").append("\n\n")
                .append(tv != null ? ("&#128250;" + countChannels(tv) + "\n\n") : "")
                .append(wink != null ? "WINK: " + wink + "\n\n" : "")
                .append(mobile != null ? "&#128222;" + mobile.replace("\n", "\n&#128222;") + "\n\n" : "")
                .append("&#9203; РОУТЕР\n").append("&#9203;").append(router.replace("\n", "\n&#9203;")).append("\n\n")
                .append(pristavka != null ? "&#9203; ТВ приставка\n &#9203;" + pristavka.replace("\n", "\n&#9203;") + "\n\n":"")
                .append(discount != null? "&#128176;" + discount + "\n":"")
                .append("&#128176;").append((int) Double.parseDouble(price)).append("руб./мес.\n");

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
