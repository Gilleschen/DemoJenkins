package Auto;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadExpectResult {

	public ArrayList<String> ResultList = new ArrayList<String>();

	public void LoadExpectResult(String CaseName) {
		XSSFWorkbook workbook;
		XSSFSheet sheet;

		try {
			workbook = new XSSFWorkbook(new FileInputStream("TestTool\\TestScript\\TestScript.xlsm"));
			sheet = workbook.getSheet("ExpectResult");// hard code
			ResultList = new ArrayList<String>();
			int i = 1;
			try {
				do {
					if (sheet.getRow(i).getCell(0).toString().equals(CaseName)) {
						for (int j = 1; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {

							ResultList.add(sheet.getRow(i).getCell(j).toString());
						}
						break;
					}

					i++;
				} while (!sheet.getRow(i).getCell(0).toString().equals(""));

			} catch (Exception e) {
				;
			}

			// System.out.println(ResultList);

			workbook.close();
		} catch (IOException e) {
			System.out.println("Can't find TestTool\\TestScript\\TestScript.xlsm");
		}
	}

}
