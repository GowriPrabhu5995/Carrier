package GowriPrabhu.pageobjects;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import GowriPrabhu.AbstractComponents.AbstractComponent;

public class ViewPurchaseOrderPage extends AbstractComponent {
	WebDriver driver;

	public ViewPurchaseOrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[normalize-space()='View Purchase Order Status']")
	WebElement ViwePurchaseOrderLink;

	@FindBy(xpath = "//tbody/tr")
	List<WebElement> PO_Rows;

	@FindBy(xpath = "//tbody/tr/td[2]")
	List<WebElement> DMS_PO_NUMs;

	@FindBy(xpath = "(//input[@placeholder='Enter Text..'])[1]")
	WebElement Filter_DMS_Order_TextField;

	@FindBy(xpath = "//tbody/tr[1]/td[10]/button[1]/img[1]")
	WebElement ActionIcon;

	@FindBy(xpath = "//button[normalize-space()='Confirm']")
	WebElement ConfirmButton;

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	WebElement Confirm_YES;

	@FindBy(xpath = "//p[@class='cls_popup_center_text_p']")
	WebElement FinalizedOrderNumber;

	@FindBy(xpath = "//label[@class='cls_popup_center_label']")
	WebElement ConfirmationMessage;
	
	@FindBy(xpath = "//div[@class='modal-body']//span[@class='close']")
	WebElement CloseConfirmation_Pop_Up;
	
	@FindBy(xpath ="(//input[@type='number'])[1]")
	WebElement Order_Qty_Of_First_Record;

	public List<String> extractOrderDetails() {
		List<String> xpaths = Arrays.asList("//tbody/tr[1]/td[1]", // Channel Partner
				"//tbody/tr[1]/td[2]", // DMS Purchase Order No
				"//tbody/tr[1]/td[3]", // DMS Creation Date
				"//tbody/tr[1]/td[4]", // DMS Approval Date
				"//tbody/tr[1]/td[5]", // Oracle Order Number
				"//tbody/tr[1]/td[6]", // Oracle Order Date
				"//tbody/tr[1]/td[7]", // Net Amount
				"//tbody/tr[1]/td[8]", // ASM Name / Created By
				"//tbody/tr[1]/td[9]" // Oracle Order Status
		);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ✅ Fix: Use explicit typing in `map()`
		List<String> extractedData = xpaths.stream().map(xpath -> {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			return element.getText().trim(); // Ensure type inference works
		}).collect(Collectors.toList());

		return extractedData;
	}

	public String getDmsPurchaseOrderNo() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(1);
	}

	public String ChannelParterCode() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(0);
	}

	public String DMS_PO_Creation_Date() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(2);
	}

	public String DMS_PO_ApprovalDate() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(3);
	}

	public String Oracle_Order_Number() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(4);
	}

	public String Oracle_Order_Date() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(5);
	}

	public String Net_Amount() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(6);
	}

	public String ASMNameORCreatedBy() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(7);
	}

	public String Oracle_Order_Status() {
		List<String> orderDetails = extractOrderDetails();
		return orderDetails.get(8);
	}

	public ViewPurchaseOrderPage ViewPurchaseOrder() throws InterruptedException {
		waitForElementToAppear(ViwePurchaseOrderLink);
		Thread.sleep(2000);
		ViwePurchaseOrderLink.click();
		return this;

	}

	public void FiletrWithPO_Number() {
		String expectedOrderNo = getDmsPurchaseOrderNo(); // Get the order number
		Filter_DMS_Order_TextField.sendKeys(expectedOrderNo);
	}

	public void ClickOnActionIcon() {
		waitForWebElementToBeClickable(ActionIcon);
		ActionIcon.click();
	}
	
	public void EditTheQTY(String EditedQTY ) throws InterruptedException {
		Thread.sleep(3000);
		scrollToElement(ConfirmButton);
		Thread.sleep(5000);
		clickElementUsingActions(Order_Qty_Of_First_Record);
		
		Order_Qty_Of_First_Record.clear();;
		Order_Qty_Of_First_Record.sendKeys( EditedQTY);

	}

	public String[] ConfirmTheOrderDirectly() {
		waitForElementToAppear(ConfirmButton);
		scrollToElementBy(ConfirmButton);
		waitForWebElementToBeClickable(ConfirmButton);
		ConfirmButton.click();
		waitForElementToAppear(Confirm_YES);
		Confirm_YES.click();
		waitForElementToAppear(FinalizedOrderNumber);
		String finalizedOrderNumber = FinalizedOrderNumber.getText();
		String confirmation_MSG = ConfirmationMessage.getText();
		waitForWebElementToBeClickable(CloseConfirmation_Pop_Up);
		CloseConfirmation_Pop_Up.click();
		return new String[] { finalizedOrderNumber, confirmation_MSG };

	}
	
	

}
