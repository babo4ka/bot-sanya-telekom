package greatBot.service.pagesManaging.pages.tariffsPage;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TariffsReader {

    private final String filePath = "./src/main/resources/tariffs.xlsx";

    public List<Tariff> readTariffs(int page) throws IOException {
        List<Tariff> tariffs = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            if((int) row.getCell(7).getNumericCellValue() != page)continue;

            List<String> args = new ArrayList<>();

            Iterator<Cell> cellIterator = row.iterator();

            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                switch (cell.getCellType()){
                    case STRING -> args.add(cell.getStringCellValue());
                    case NUMERIC -> args.add(String.valueOf(cell.getNumericCellValue()));
                }
            }

            tariffs.add(new Tariff(
                    args.get(0).equals("-")?null:args.get(0),
                    args.get(1).equals("-")?null:args.get(1),
                    args.get(2).equals("-")?null:args.get(2),
                    args.get(3).equals("-")?null:args.get(3),
                    args.get(4).equals("-")?null:args.get(4),
                    args.get(5).equals("-")?null:args.get(5),
                    args.get(6).equals("-")?null:args.get(6),
                    args.get(8).equals("-")?null:args.get(8)
                    ));
        }

        return tariffs;
    }
}
