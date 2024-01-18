package greatBot;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("./src/main/resources/tariffs.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        StringBuilder result = new StringBuilder();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();

            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                switch (cell.getCellType()){
                    case STRING -> result.append(cell.getStringCellValue()).append(" ");
                    case NUMERIC -> result.append(cell.getNumericCellValue()).append(" ");
                }

            }

            result.append("\n");

        }

        System.out.println(result);
    }
}
