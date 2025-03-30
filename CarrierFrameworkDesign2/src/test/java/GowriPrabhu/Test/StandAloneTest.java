package GowriPrabhu.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import GowriPrabhu.pageobjects.CreatePurchaseOrder;
import GowriPrabhu.pageobjects.Landingpage;
import GowriPrabhu.pageobjects.ViewPurchaseOrderPage;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		String[] productToBuy = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
		String CP_CODE = "834911";
		String Division = "Distribution OU";
		String ShipToAddressCode = "1967391";
		String quantity = "2"; 
		WebDriver driver = new ChromeDriver();
		Landingpage lp = new Landingpage(driver);
		
		lp.goTostaticurl();
		lp.login("ine00913", "password");
		lp.getLoginMsg();
		
		CreatePurchaseOrder C_PO = new CreatePurchaseOrder(driver);
		C_PO.MouseHoverToSalesManagement();
		C_PO.LaunchCreatePruchaseOrder();
		C_PO.SelectChannelPartner(CP_CODE);
		// C_PO.SelectOrg(Division);
		C_PO.SelectBillToAddress();
		C_PO.SelectShipToAddress(ShipToAddressCode);
		C_PO.ClickOnAddProductsButton();
		C_PO.AddItems(productToBuy);
		C_PO.ViewTheSelectedProductDetails(productToBuy, quantity);
		 C_PO.SaveAsDraft();
		 ViewPurchaseOrderPage View_PO = new ViewPurchaseOrderPage(driver);
		View_PO.getDmsPurchaseOrderNo();
		
//		
//		new CreatePurchaseOrder(driver).MouseHoverToSalesManagement().LaunchCreatePruchaseOrder().SelectChannelPartner(CP_CODE)
//		.SelectBillToAddress().SelectShipToAddress(ShipToAddressCode).ClickOnAddProductsButton()
//		.AddItems(productToBuy).ViewTheSelectedProductDetails(productToBuy,quantity);

		 
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		// hnjhgjngjngjngjgjngjng
//		List<String> xpaths = Arrays.asList("//tbody/tr[1]/td[1]", // Channel Partner
//				"//tbody/tr[1]/td[2]", // DMS Purchase Order No
//				"//tbody/tr[1]/td[3]", // DMS Creation Date
//				"//tbody/tr[1]/td[4]", // DMS Approval Date
//				"//tbody/tr[1]/td[5]", // Oracle Order Number
//				"//tbody/tr[1]/td[6]", // Oracle Order Date
//				"//tbody/tr[1]/td[7]", // Net Amount
//				"//tbody/tr[1]/td[8]", // ASM Name / Created By
//				"//tbody/tr[1]/td[9]" // Oracle Order Status
//		);
//
//		Thread.sleep(1000);
//		// Extract data sequentially
//		String[] extractedArray = xpaths.stream().map(xpath -> driver.findElement(By.xpath(xpath)).getText().trim())
//				.toArray(String[]::new); // Convert List to Array
//		// Print extracted data in sequence
//		for (int i = 0; i < extractedArray.length; i++) {
//			System.out.println("Data " + (i + 1) + ": " + extractedArray[i]);
//		}
//
//		// Access specific data points
//		System.out.println("DMS Purchase Order No: " + extractedArray[1]); // Second value

	}
	
//	@DataProvider(name = "productData")
//    public Object[][] getProductData() {
//        return new Object[][] {
//            { "Ine00913", "password", "834911", "1967391", 
//              new String[]{ "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" }, 
//              "2" }
//        };
//	}
}


