package greatBot.service.pagesManaging.pages.tariffsPage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TariffsReader {

    private final String filePath = File.separator + "root" + File.separator + "bot_info.xlsx";
//    private final String filePath = "./src/main/resources/bot_info.xlsx";


    private TariffsReader() throws IOException {
        loadTariffs();
    }

    private static TariffsReader instance;

    public static TariffsReader getInstance() throws IOException {
        if(instance == null){
            instance = new TariffsReader();
        }

        return instance;
    }


    /*
    * маппинг групп тарифов (номер страницы -> маппинг тарифов по номерам)
    * номер тарифа -> тариф
    */
    private Map<Integer, Map<Integer, Tariff>> allTariffs = new HashMap<>();

    private Map<Integer, List<Integer>> numsInGroup = new HashMap<>();


    public void loadTariffs() throws IOException {
        allTariffs = new HashMap<>();
        numsInGroup = new HashMap<>();
        FileInputStream inputStream = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFSheet sheet = workbook.getSheet("тарифы");

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            int group = (int) row.getCell(10).getNumericCellValue();
            int num = (int) row.getCell(9).getNumericCellValue();

            allTariffs.computeIfAbsent(group, k -> new HashMap<>());
            numsInGroup.computeIfAbsent(group, k -> new ArrayList<>());

            List<String> args = new ArrayList<>();

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING -> args.add(cell.getStringCellValue());
                    case NUMERIC -> args.add(String.valueOf(cell.getNumericCellValue()));
                }
            }

            allTariffs.get(group).put(num, new Tariff(
                    args.get(0).equals("-")?null:args.get(0),
                    args.get(1).equals("-")?null:args.get(1),
                    args.get(2).equals("-")?null:args.get(2),
                    args.get(3).equals("-")?null:args.get(3),
                    args.get(4).equals("-")?null:args.get(4).replace("\\n", "\n"),
                    args.get(5).equals("-")?null:args.get(5).replace("\\n", "\n"),
                    args.get(6).equals("-")?null:args.get(6).replace("\\n", "\n"),
                    args.get(7).equals("-")?null:args.get(7),
                    args.get(8).equals("-")?null:args.get(8),
                    args.get(9).equals("-")?null:args.get(9)
            ));

            numsInGroup.get(group).add(num);
        }
    }

    public List<Tariff> getTariffsByGroup(int groupNum){
        return allTariffs.get(groupNum).values().stream().toList();
    }

    public Tariff getTariffByNumber(int tariffNum){
        for(int key : numsInGroup.keySet()){
            if(numsInGroup.get(key).contains(tariffNum)){
                return allTariffs.get(key).get(tariffNum);
            }
        }

        return null;
    }

}
