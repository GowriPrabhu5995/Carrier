package GowriPrabhu.Test;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GowriPrabhu.TestComponents.BaseTest;
import GowriPrabhu.pageobjects.ApprovalEngine;
import GowriPrabhu.pageobjects.CreatePurchaseOrder;
import GowriPrabhu.pageobjects.Landingpage;
import GowriPrabhu.pageobjects.ViewPurchaseOrderPage;

public class PO_CreationUsing_JsonData extends BaseTest {
	WebDriver driver = new ChromeDriver();
	Landingpage lp = new Landingpage(driver);
	CreatePurchaseOrder C_PO = new CreatePurchaseOrder(driver);
	ViewPurchaseOrderPage View_PO = new ViewPurchaseOrderPage(driver);
	ApprovalEngine approvals = new ApprovalEngine(driver);

	private void loginAndNavigateToSalesManagement(String UserCode, String Password) throws InterruptedException {
		lp.goTo(baseUrl);
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

	@Test(dataProvider = "purchaseOrderData", groups = { "orderFlow" })
	public void CreatePurchaseOrder(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("ASMLogin_ID"), (String) input.get("Password"));
		C_PO.LaunchCreatePruchaseOrder();
		C_PO.SelectChannelPartner((String) input.get("ChannelPartner_Code"));
		C_PO.SelectBillToAddress();
		C_PO.SelectShipToAddress((String) input.get("ShipToAddressCode"));
		C_PO.ClickOnAddProductsButton();
		
		// Handle Product List Properly
		List<String> products = (List<String>) input.get("ProductsToBuy");
		C_PO.AddItems(products.toArray(new String[0]));
		C_PO.ViewTheSelectedProductDetails(products.toArray(new String[0]), (String) input.get("quantity"));

		C_PO.SaveAsDraft();
		View_PO.getDmsPurchaseOrderNo();
	}

	@Test(dataProvider = "purchaseOrderData", dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withoutEdit" })
	public void LoginAsCPandConfirmDirectly(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("ChannelPartner_Code"), (String) input.get("Password"));
		FilterForThePurchaseOrder();

		String[] Confirmation = View_PO.ConfirmTheOrderDirectly();
		AssertJUnit.assertEquals(Confirmation[1].trim().replaceAll("\\r?\\n", " "),
				"Purchase Order Placed successfully.");
	}

	@Test(dataProvider = "purchaseOrderData", dependsOnMethods = { "LoginAsCPandConfirmDirectly" }, groups = { "withoutEdit" })
	public void Branch_Manage_Approval(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("BranchManager_ID"), (String) input.get("Password"));
		FilterForThePurchaseOrder();
		Approval_Calling();
	}

	@Test(dataProvider = "purchaseOrderData", dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withEdit" })
	public void LoginAsCPandConfirmAfterEditing(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("ChannelPartner_Code"), (String) input.get("Password"));
		FilterForThePurchaseOrder();
		View_PO.EditTheQTY((String) input.get("EditedQty"));
		String[] Confirmation = View_PO.ConfirmTheOrderDirectly();
		AssertJUnit.assertEquals(Confirmation[1].trim().replaceAll("\\r?\\n", " "),
				"Purchase Order Placed successfully.");
	}

	@Test(dataProvider = "purchaseOrderData", dependsOnMethods = { "LoginAsCPandConfirmAfterEditing" }, groups = { "withEdit" })
	public void Approvals_for_PurchaseOrder_AfterEditing(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("ASMLogin_ID"), (String) input.get("Password"));
		FilterForThePurchaseOrder();
		Approval_Calling();

		loginAndNavigateToSalesManagement((String) input.get("BranchManager_ID"), (String) input.get("Password"));
		FilterForThePurchaseOrder();
		Approval_Calling();
	}

	

	@DataProvider(name = "purchaseOrderData")
	public Object[][] getData() throws IOException {
	    List<HashMap<String, Object>> data = (List<HashMap<String, Object>>) getJsonDataToMap(System.getProperty("user.dir") + 
	            "\\src\\test\\java\\GowriPrabhu\\data\\PurchaseOrderData.json");

	    if (data.isEmpty()) {
	        throw new IllegalStateException("DataProvider JSON is empty!");
	    }

	    Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}

	
}
