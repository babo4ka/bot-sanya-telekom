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

        builder.append(name).append("\n")
                .append("Интернет: ").append(internet).append("\n")
                .append(tv!=null?("ТВ: " + tv + "\n"):"")
                .append(wink!=null?"WINK: " + wink + "\n":"")
                .append(mobile!=null?"Мобильная связь: " + mobile + "\n":"")
                .append("Аренда оборудования: ").append(rent).append("\n")
                .append("Цена: ").append(price).append("\n");

        return builder.toString();
    }
}
