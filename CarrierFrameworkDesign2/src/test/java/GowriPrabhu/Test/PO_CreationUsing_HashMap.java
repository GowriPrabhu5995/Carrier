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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GowriPrabhu.TestComponents.BaseTest;
import GowriPrabhu.pageobjects.ApprovalEngine;
import GowriPrabhu.pageobjects.CreatePurchaseOrder;
import GowriPrabhu.pageobjects.Landingpage;
import GowriPrabhu.pageobjects.ViewPurchaseOrderPage;

public class PO_CreationUsing_HashMap extends BaseTest {
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

	@Test(dataProvider = "getData", groups = { "orderFlow" })
	public void CreatePurchaseOrder(HashMap<String, Object> input) throws InterruptedException {
		loginAndNavigateToSalesManagement((String) input.get("ASMLogin_ID"), (String) input.get("Password"));
		C_PO.LaunchCreatePruchaseOrder();
		C_PO.SelectChannelPartner((String) input.get("ChannelPartner_Code"));
		// C_PO.SelectOrg(Division);
		C_PO.SelectBillToAddress();
		C_PO.SelectShipToAddress((String) input.get("ShipToAddressCode"));
		C_PO.ClickOnAddProductsButton();
		C_PO.AddItems((String[]) input.get("ProductsToBuy"));

		C_PO.ViewTheSelectedProductDetails((String[]) input.get("ProductsToBuy"), (String) input.get("quantity"));
		C_PO.SaveAsDraft();
		View_PO.getDmsPurchaseOrderNo();

	}

	@Test(dataProvider = "getData", dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withoutEdit" })
	public void LoginAsCPandConfirmDirectly(String ASMCODE, String Password, String CP_CODE, String ShipToAddressCode,
			String[] productToBuy, String quantity, String BM, String EditedQTY) throws InterruptedException {
		loginAndNavigateToSalesManagement(CP_CODE, Password);
		FilterForThePurchaseOrder();

		String[] Confirmation = View_PO.ConfirmTheOrderDirectly();
		AssertJUnit.assertEquals(Confirmation[1].trim().replaceAll("\\r?\\n", " "),
				"Purchase Order Placed successfully.");

	}

	@Test(dataProvider = "getData", dependsOnMethods = { "LoginAsCPandConfirmDirectly" }, groups = { "withoutEdit" })
	public void Branch_Manage_Approval(String ASMCODE, String Password, String CP_CODE, String ShipToAddressCode,
			String[] productToBuy, String quantity, String BM, String EditedQTY) throws InterruptedException {
		loginAndNavigateToSalesManagement(BM, Password);
		FilterForThePurchaseOrder();
		Approval_Calling();

	}

	@Test(dataProvider = "getData", dependsOnMethods = { "CreatePurchaseOrder" }, groups = { "withEdit" })
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

	@Test(dataProvider = "getData", dependsOnMethods = { "LoginAsCPandConfirmAfterEditing" }, groups = { "withEdit" })
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

	@DataProvider
	public Object[][] getData() {

		HashMap<String, Object> map = new HashMap<>();
		map.put("ASMLogin_ID", "Ine00913");
		map.put("Password", "password");
		map.put("ChannelPartner_Code", "834911");
		map.put("ShipToAddressCode", "1967391");
		String[] items = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
		map.put("ProductsToBuy", items); // ✅ Store as an array

		map.put("quantity", "2");
		map.put("BranchManager_ID", "Ine00688");
		map.put("EditedQty", "5");

		return new Object[][] { { map } };
	}

}
