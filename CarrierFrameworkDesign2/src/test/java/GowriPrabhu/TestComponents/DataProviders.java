package GowriPrabhu.TestComponents;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviders extends BaseTest {

	static DataFormatter formatter = new DataFormatter();
	@DataProvider
	public Object[][] getData() {

//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("ASMLogin_ID", "Ine00913");
//		map.put("Password", "password");
//		map.put("ChannelPartner_Code", "834911");
//		map.put("ShipToAddressCode", "1967391");
//		String[] items = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
//        String[] items = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
//      map.put("ProductsToBuy", "items"); // Keeping array format
//      map.put("quantity", "2");
//      map.put("BranchManager_ID", "Ine00688");
//      map.put("EditedQty", "5");

		return new Object[][] { { "Ine00913", "password", "834911", "1967391",
				new String[] { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" }, "2",
				"Ine00688", "5" } };
	}
	@DataProvider(name = "getDataFromExcel")
    public Object[][] getDataFromExcel() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\WINIT\\Documents\\Onboarding File.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        int rowCount = sheet.getLastRowNum(); // Get the last row index
        XSSFRow firstRow = sheet.getRow(0);
        
        if (firstRow == null) {
            workbook.close();
            throw new IOException("First row is empty. Please check the Excel file.");
        }
        
        int columnCount = firstRow.getLastCellNum();
        Object[][] data = new Object[rowCount][columnCount];  

        for (int i = 0; i < rowCount; i++) {
            XSSFRow row = sheet.getRow(i + 1); // Start from row index 1 (skipping header)

            if (row == null) { // Skip null rows
                System.out.println("Skipping empty row at index: " + (i + 1));
                continue;
            }

            for (int j = 0; j < columnCount; j++) {
                XSSFCell cell = row.getCell(j);
                data[i][j] = (cell == null) ? "" : formatter.formatCellValue(cell); // Handle empty cells
            }
        }
        
        workbook.close();
        return data;
    }
}
