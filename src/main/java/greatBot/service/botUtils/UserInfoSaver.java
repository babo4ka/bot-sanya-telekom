package greatBot.service.botUtils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class UserInfoSaver {

    private final String filePath = File.separator + "root" + File.separator + "bot_info.xlsx";
//    private final String filePath = "./src/main/resources/bot_info.xlsx";


    public void createUser(long id, String username) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheet("пользователи");

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        int rowNum = 1;

        while(rowIterator.hasNext()){
            Row tempRow = rowIterator.next();
            if(tempRow.getCell(0).getNumericCellValue() == id)return;
            rowNum++;
        }

        Row row = sheet.createRow(rowNum);
        row.createCell(0, CellType.NUMERIC).setCellValue(id);
        row.createCell(1, CellType.STRING).setCellValue(username==null?"-":username);
        row.createCell(2, CellType.NUMERIC).setCellValue(0);
        row.createCell(3, CellType.NUMERIC).setCellValue(0);
        row.createCell(4, CellType.NUMERIC).setCellValue(0);

        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();
    }

    public void updateInfo(long id, int group) throws Exception {
        FileInputStream inputStream = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheet("пользователи");

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        Row row = null;
        while(rowIterator.hasNext()){
            row = rowIterator.next();

            if(row.getCell(0).getNumericCellValue() == id)break;
        }

        if(row == null){
            throw new Exception("ПОЛЬЗОВАТЕЛЬ НЕ НАЙДЕН!!!");
        }

        row.getCell(group).setCellValue(1);

        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();
    }

}
