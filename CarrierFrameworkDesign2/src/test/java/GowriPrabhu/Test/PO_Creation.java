package GowriPrabhu.Test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GowriPrabhu.TestComponents.BaseTest;
import GowriPrabhu.TestComponents.DataProviders;
import GowriPrabhu.TestComponents.RetryFailedTest;
import GowriPrabhu.pageobjects.ApprovalEngine;
import GowriPrabhu.pageobjects.CreatePurchaseOrder;
import GowriPrabhu.pageobjects.Landingpage;
import GowriPrabhu.pageobjects.ViewPurchaseOrderPage;

public class PO_Creation extends BaseTest {
	  WebDriver driver;
	    Landingpage lp;
	    CreatePurchaseOrder C_PO;
	    ViewPurchaseOrderPage View_PO;
	    ApprovalEngine approvals;
	    @BeforeClass
	    public void setup() {
	    	
	        driver = new ChromeDriver(); // ✅ Initialize WebDriver properly
	        lp = new Landingpage(driver);
	        C_PO = new CreatePurchaseOrder(driver);
	        View_PO = new ViewPurchaseOrderPage(driver);
	        approvals = new ApprovalEngine(driver);
	    }
	    
	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit(); // ✅ Ensure WebDriver quits after tests finish
	        }
	    }

	private void loginAndNavigateToSalesManagement(String UserCode, String Password) throws InterruptedException {
		lp.goTo(baseUrl);  // ✅ Now `goTo()` will use `baseUrl`
		    lp.login(UserCode, Password);
		    lp.getLoginMsg();
		    C_PO.MouseHoverToSalesManagement();
	}

	private void FilterForThePurchaseOrder() throws InterruptedException {
		View_PO.ViewPurchaseOrder();
		C_PO.CommonFilterButton();
		View_PO.FiletrWithPO_Number();
		C_PO.CommonSearchButton();
		C_PO.CommonFilterButton();
		View_PO.ClickOnActionIcon();

	}

	private void Approval_Calling() {
		approvals.ApproveThePurchaseOrder();
		approvals.ApprovalConfirmation();
		approvals.Final_Confirmation();
	}

	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, groups = { "orderFlow" },  retryAnalyzer = RetryFailedTest.class )
	public void CreatePurchaseOrder(String ASMCODE, String Password, String CP_CODE, String ShipToAddressCode,
			String[] productToBuy, String quantity, String BM, String EditedQTY) throws InterruptedException {
		loginAndNavigateToSalesManagement(ASMCODE, Password);
		C_PO.LaunchCreatePruchaseOrder();
		C_PO.SelectChannelPartner(CP_CODE);
		// C_PO.SelectOrg(Division);
		C_PO.SelectBillToAddress();
		C_PO.SelectShipToAddress(ShipToAddressCode);
		C_PO.ClickOnAddProductsButton();
		C_PO.AddItems(productToBuy);
		C_PO.ViewTheSelectedProductDetails(productToBuy, quantity);
		C_PO.SaveAsDraft();
		View_PO.getDmsPurchaseOrderNo();

	}

	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withoutEdit" })
	public void LoginAsCPandConfirmDirectly(String ASMCODE, String Password, String CP_CODE, String ShipToAddressCode,
			String[] productToBuy, String quantity, String BM, String EditedQTY) throws InterruptedException {
		loginAndNavigateToSalesManagement(CP_CODE, Password);
		FilterForThePurchaseOrder();

		String[] Confirmation = View_PO.ConfirmTheOrderDirectly();
		AssertJUnit.assertEquals(Confirmation[1].trim().replaceAll("\\r?\\n", " "),
				"Purchase Order Placed successfully.");

	}

	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, dependsOnMethods = { "LoginAsCPandConfirmDirectly" }, groups = { "withoutEdit" })
	public void Branch_Manage_Approval(String ASMCODE, String Password, String CP_CODE, String ShipToAddressCode,
			String[] productToBuy, String quantity, String BM, String EditedQTY) throws InterruptedException {
		loginAndNavigateToSalesManagement(BM, Password);
		FilterForThePurchaseOrder();
		Approval_Calling();

	}

	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withEdit" })
	public void LoginAsCPandConfirmAfterEditing(String ASMCODE, String Password, String CP_CODE,
			String ShipToAddressCode, String[] productToBuy, String quantity, String BM, String EditedQTY)
			throws InterruptedException {
		loginAndNavigateToSalesManagement(CP_CODE, Password);
		FilterForThePurchaseOrder();
		View_PO.EditTheQTY(EditedQTY);
		String[] Confirmation = View_PO.ConfirmTheOrderDirectly();
		AssertJUnit.assertEquals(Confirmation[1].trim().replaceAll("\\r?\\n", " "),
				"Purchase Order Placed successfully.");

	}

	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, dependsOnMethods = { "LoginAsCPandConfirmAfterEditing" }, groups = { "withEdit" })
	public void Approvals_for_PurchaseOrder_AfterEditing(String ASMCODE, String Password, String CP_CODE,
			String ShipToAddressCode, String[] productToBuy, String quantity, String BM, String EditedQTY)
			throws InterruptedException {
		loginAndNavigateToSalesManagement(ASMCODE, Password);
		FilterForThePurchaseOrder();
		Approval_Calling();
		loginAndNavigateToSalesManagement(BM, Password);
		FilterForThePurchaseOrder();
		Approval_Calling();

	}
	
	@Test(dataProvider = "getData",dataProviderClass = DataProviders.class, dependsOnMethods = { "LoginAsCPandConfirmAfterEditing","LoginAsCPandConfirmDirectly" }, groups = { "withEdit" , "withoutEdit"})
	public void Approvals_for_PurchaseOrder_With_Positive_Margin(String ASMCODE, String Password, String CP_CODE,
			String ShipToAddressCode, String[] productToBuy, String quantity, String BM, String EditedQTY)
			throws InterruptedException{
		
		 System.out.println("🔹 Checking Approval Levels...");
		    int approvalCount = approvals.getApprovalCount(); //Getting the count of the approval levels
		    if (approvalCount >= 1) {
		        System.out.println("🔹 Logging in as BM...");
		        loginAndNavigateToSalesManagement(BM, Password);
				FilterForThePurchaseOrder();
				Approval_Calling();
		    }
		    
		    if (approvalCount >= 2) {
		    	//When the PO is edited by CP then system will go to ASM approval & BM approval.
		        System.out.println("🔹 Logging in as ASM...");
		        loginAndNavigateToSalesManagement(ASMCODE, Password);
				FilterForThePurchaseOrder();
				Approval_Calling();
				System.out.println("🔹 Logging in as BM...");
		        loginAndNavigateToSalesManagement(BM, Password);
				FilterForThePurchaseOrder();
				Approval_Calling();
		    }
		    
		   
		
	}

	

}
