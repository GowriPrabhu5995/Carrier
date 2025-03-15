package GowriPrabhu.pageobjects;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import GowriPrabhu.AbstractComponents.AbstractComponent;

public class CreatePurchaseOrder extends AbstractComponent {
	WebDriver driver;

	public CreatePurchaseOrder(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	String dmsPurchaseOrderNo;
	String channelParterCode;
	@FindBy(xpath = "//a[normalize-space()='Create Purchase Order']")
	WebElement CreatePO_Link;

	@FindBy(xpath = "//input[@value='Select Channel Partner ']")
	WebElement ChannelPartnerSelection;

	@FindBy(xpath = "//input[@placeholder='Search by Channel Partner Code / Name']")
	WebElement Cp_SearchBar;

	@FindBy(xpath = "//input[@name = 'store']")
	WebElement Store_RadioButton;

	@FindBy(xpath = "//button[normalize-space()='Proceed']")
	WebElement ProceedButton;

	@FindBy(xpath = "//label[normalize-space()='Select Organization Unit']")
	WebElement OrgField;

	@FindBy(xpath = "//input[@placeholder='Search here...']")
	WebElement Search;

	@FindBy(xpath = "//ul/button")
	WebElement SelectOrg;

	@FindBy(xpath = "//div[5]//button[1]")
	WebElement BillToAddressField;

	@FindBy(xpath = "//button[@class='cls_dropdown_item  ']")
	WebElement SelectDropDown;

	@FindBy(xpath = "//label[normalize-space()='Select Shipping Address']")
	WebElement ShipToAddressField;

	@FindBy(xpath = "//button[normalize-space()='Add Product']")
	WebElement AddProducts;

	@FindBy(xpath = "//tbody/tr")
	List<WebElement> ListOfItems;

	@FindBy(xpath = "//button[normalize-space()='Add']")
	WebElement AddButton;

	@FindBy(xpath = "//body[1]/div[1]/div[1]/main[1]/article[1]/div[1]/div[4]/div[6]/table[1]/tbody[1]/tr")
	List<WebElement> ListOfProductsSelected;

	@FindBy(xpath = "//button[normalize-space()='Save as Draft']")
	WebElement SaveAsDraft;
	
	@FindBy(xpath = "//div[@class='toast-body']")
	WebElement ToastMessage;

	public CreatePurchaseOrder LaunchCreatePruchaseOrder() {
		clickElementUsingActions(CreatePO_Link);
		return this;
	}

	public CreatePurchaseOrder SelectChannelPartner(String CP_CODE) throws InterruptedException {
		waitForElementToAppear(ChannelPartnerSelection);
		clickElementUsingActions(ChannelPartnerSelection);
		waitForElementToAppear(Cp_SearchBar);
		sendKeysUsingActions(Cp_SearchBar, CP_CODE);
		Thread.sleep(2000);
		// pressEnterUsingActions(SearchBar);
		Store_RadioButton.click();
		ProceedButton.click();
		return this;

	}

	public CreatePurchaseOrder SelectOrg(String Division) {
		OrgField.click();
		Search.click();
		sendKeysUsingActions(Search, Division);
		SelectOrg.click();
		return this;
	}

	public CreatePurchaseOrder SelectBillToAddress() throws InterruptedException {
		Thread.sleep(5000);
		LongwaitForElementToAppear(BillToAddressField);
		
		BillToAddressField.click();
		SelectDropDown.click();
		return this;
	}

	public CreatePurchaseOrder SelectShipToAddress(String ShipToAddressCode) throws InterruptedException {
		LongwaitForElementToAppear(ShipToAddressField);		
		ShipToAddressField.click();
		waitForElementToAppear(Search);
		Search.sendKeys(ShipToAddressCode);
		Thread.sleep(1000);
		SelectDropDown.click();
		return this;
	}

	public CreatePurchaseOrder ClickOnAddProductsButton() {
		LongwaitForElementToAppear(AddProducts);
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		waitForWebElementToBeClickable(AddProducts);
		AddProducts.click();
		return this;

	}

	public CreatePurchaseOrder AddItems(String[] productToBuy) {
		for (WebElement row : ListOfItems) {
			try {
				List<WebElement> productLabels = row.findElements(By.xpath(".//label[contains(text(), '@CMI')]"));

				for (WebElement label : productLabels) {
					String productText = label.getText().trim();
					if (Arrays.asList(productToBuy).contains(productText)) {
						label.click();
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("⚠️ Error processing row: " + e.getMessage());
			}
		}
		AddButton.click();
		return this;

	}

	public CreatePurchaseOrder ViewTheSelectedProductDetails(String[] productToBuy, String quantity)
			throws InterruptedException {

		PageDownUsingActions(SaveAsDraft);
		Thread.sleep(1000);
		List<WebElement> productRows = ListOfProductsSelected;
		for (WebElement row : productRows) {
			try {
				String productCode = row.findElement(By.xpath(".//td[3]")).getText().trim(); // Extract product code

				if (Arrays.asList(productToBuy).contains(productCode)) {
					// Check if code exists in array
					Thread.sleep(1000);
					WebElement qtyInput = row.findElement(By.xpath(".//td[5]/input")); // Locate input field
					Thread.sleep(1000);
					new Actions(driver).moveToElement(qtyInput).click().sendKeys(Keys.BACK_SPACE, quantity).perform();
				}
			} catch (Exception e) {
				System.out.println("⚠️ Error processing row: " + e.getMessage());
			}
		}
		return this;
	}

	public void SaveAsDraft() {
		waitForElementToAppear(SaveAsDraft);
		hoverOverElement(SaveAsDraft);
		SaveAsDraft.click();
		waitForElementToAppear(ToastMessage);
		String msg = ToastMessage.getText();
		Assert.assertEquals(msg, "Purchase order save successfully...", "Toast message mismatch!");
		
	}

	
	
}
