package GowriPrabhu.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GowriPrabhu.AbstractComponents.AbstractComponent;

public class Onboarding extends AbstractComponent {

	WebDriver driver;

	public Onboarding(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[normalize-space()='Customer Details']")
	WebElement CustomerDetails;

	@FindBy(xpath = "//button[normalize-space()='Add New']")
	WebElement AddButtonToOnboard;

	@FindBy(xpath = "//div[@class='col-md-3']")
	WebElement ChannelPartnerCode;

	@FindBy(xpath = "//input[@placeholder='Enter here']")
	List<WebElement> Enter_Here;

	@FindBy(xpath = "//button[normalize-space()='Fetch Details']")
	WebElement FetchDetails_From_GST;

	@FindBy(xpath = "//label[normalize-space() = 'Select Broad Customer Classification']")
	WebElement BroadClassification_DropDown;

	@FindBy(xpath = "//div[@class='cls_search_desk']")
	WebElement Search_For_BroadClassification;

	@FindBy(xpath = "//button[@class='cls_dropdown_item  ']")
	WebElement Select_The_Searched_Recorder;

	@FindBy(xpath = "//label[normalize-space()='Select Customer Classification Type']")
	WebElement Select_Customer_Classification_Type;

	@FindBy(xpath = "(//input[contains(@placeholder,'Enter here')])[1]")
	WebElement Firm_Registration_Number;

	@FindBy(xpath = "(//input[contains(@placeholder,'Enter here')])[2]")
	WebElement Company_Registration_Number;

	@FindBy(xpath = "//label[normalize-space()='Select Firm Type']")
	WebElement Select_Firm_Type;

	@FindBy(xpath = "//label[normalize-space()='Select Division']")
	WebElement Select_Division;

	@FindBy(xpath = "//input[contains(@placeholder,'Search here...')]")
	WebElement Search_For_Division;

	@FindBy(xpath = "(//img[@class='cls_img_top'])[1]")
	WebElement Clear_The_Search_Field_Of_DIV;

	@FindBy(xpath = "//button[normalize-space()='Ok']")
	WebElement Confirm_Division_By_CLICKING_OK;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	List<WebElement> Save_Details;

	@FindBy(xpath = "//span[normalize-space()='Contact Details']")
	WebElement Contact_Details;

	@FindBy(xpath = "//label[normalize-space()='Select Contact Type']")
	WebElement Select_Contact_Details_DropDown;

	@FindBy(xpath = "(//input[contains(@placeholder,'Enter here')])[8]")
	WebElement Enter_Contact_Preson_Name;

	@FindBy(xpath = "(//input[@placeholder='Enter here'])[9]")
	WebElement Enter_Contact_Number;

	@FindBy(xpath = "//input[@type='email']")
	WebElement Enter_Email;

	@FindBy(xpath = "//label[normalize-space(text())='IsPrimary']")
	WebElement IS_Primary_Flag;

	//@FindBy(xpath = "(//button[normalize-space()='Save'])[2]")
	//List<WebElement> Save_details;

	@FindBy(xpath = "//span[normalize-space()='Bill To Address']")
	WebElement Bill_To_Address;

	@FindBy(xpath = "//input[@type='checkbox']")
	List<WebElement> Check_BOX;

	@FindBy(xpath = "//input[@placeholder='Enter Locality here']")
	WebElement Enter_Locality;

	@FindBy(xpath = "//label[normalize-space()='Select Branch']")
	WebElement Select_Branch;

	@FindBy(xpath = "//label[normalize-space()='Select Organisation Unit']")
	WebElement Sales_ORG_Dropdown;

	@FindBy(xpath = "//span[normalize-space()='Ship To Address']")
	WebElement Ship_To_Address;

	@FindBy(xpath = "//label[normalize-space()='Select Sales Office']")
	WebElement Click_On_Sales_Office_dropdown;

	@FindBy(xpath = "//span[contains(text(),'Name & Address of Proprietor/All Partners/All Dire')]")
	WebElement Karta;
	
	@FindBy(xpath = "//span[normalize-space()='Asm Mapping']")
	WebElement ASM_Mapping;
	
	@FindBy(xpath = "//label[normalize-space()='Select ASM']")
	WebElement Select_ASM;
	@FindBy(xpath = "Select_The_Searched_Recorder.click();")
	WebElement Click_On_Ok;

	public void LaunchOnboardingPage() {
		waitForElementToAppear(CustomerDetails);
		clickElementUsingActions(CustomerDetails);
	}

	public void ClickOnAddNewButtonToOnBoard() {
		waitForElementToAppear(AddButtonToOnboard);
		clickElementUsingActions(AddButtonToOnboard);
	}

	public String Enter_GST_Number_And_Fetch_Details(String GSTNUM) {
		waitForElementToAppear(Enter_Here.get(0));
		clickElementUsingActions(Enter_Here.get(0));
		Enter_Here.get(0).sendKeys(GSTNUM);
		FetchDetails_From_GST.click();
		return ChannelPartnerCode.getText().trim();

	}

	public void Select_Broad_Customer_Classification(String BroadClassification) {
		waitForElementToAppear(BroadClassification_DropDown);
		scrollToElementBy(BroadClassification_DropDown);
		BroadClassification_DropDown.click();
		Search_For_BroadClassification.click();
		Search_For_BroadClassification.sendKeys(BroadClassification);
		Select_The_Searched_Recorder.click();

	}

	public void Classification_Type(String ClassificationType) {
		Select_Customer_Classification_Type.click();
		Search_For_BroadClassification.click();
		Select_Customer_Classification_Type.sendKeys(ClassificationType);
		Select_The_Searched_Recorder.click();
	}

	public void Enter_Firm_And_Company_REG_NUM(String Firm_Registration_Num, String Company_Registration_num) {

		Firm_Registration_Number.sendKeys(Firm_Registration_Num);
		Company_Registration_Number.sendKeys(Company_Registration_num);

	}

	public void Select_FirmType(String Firm_Type) {
		Select_Firm_Type.click();
		Search_For_BroadClassification.click();
		Select_Customer_Classification_Type.sendKeys(Firm_Type);
		Select_The_Searched_Recorder.click();
	}

	public void Select_Divisions(String Div_10) {
		Select_Division.click();
		Search_For_Division.sendKeys(Div_10);
		Select_The_Searched_Recorder.click();
		Confirm_Division_By_CLICKING_OK.click();

	}

	public void Save_The_Data() {
		waitForElementToAppear(Save_Details.get(1));
		Save_Details.get(1).click();
	}

	public void Fill_Contact_Details(String Contact_Type, String Contact_Person_name, String Mobile_Number,
			String Email) {
		Contact_Details.click();
		Select_Contact_Details_DropDown.click();
		Search_For_Division.sendKeys(Contact_Type);
		Select_The_Searched_Recorder.click();
		Enter_Contact_Preson_Name.sendKeys(Contact_Person_name);
		Enter_Contact_Number.sendKeys(Mobile_Number);
		Enter_Email.sendKeys(Email);
		IS_Primary_Flag.click();
		Save_Details.get(2).click();

	}

	public void Fill_Bill_To_Address(String Email, String Mobile_Number, String Aadhar_Number, String Locality,
			String Branch, String Org) {
		waitForElementToAppear(Bill_To_Address);
		clickElementUsingActions(Bill_To_Address);
		Enter_Here.get(10).sendKeys(Email);
		Enter_Here.get(11).sendKeys(Mobile_Number);
		Enter_Here.get(13).sendKeys(Aadhar_Number);
		Enter_Locality.sendKeys(Locality);
		Select_Branch.click();
		Select_The_Searched_Recorder.click();
		Sales_ORG_Dropdown.click();
		Search_For_Division.sendKeys(Org);
		Select_The_Searched_Recorder.click();
		Check_BOX.get(4).click();
		Save_Details.get(3).click();
	}

	public void Fill_Ship_To_Address() throws InterruptedException {
		waitForElementToAppear(Ship_To_Address);
		clickElementUsingActions(Ship_To_Address);
		Check_BOX.get(5).click();
		waitForWebElementToBeClickable(Click_On_Sales_Office_dropdown);
		Click_On_Sales_Office_dropdown.click();
		Select_The_Searched_Recorder.click();
		Save_Details.get(4).click();
		Thread.sleep(5000);

	}
	
	public void Save_KARTA_Details() {
		waitForElementToAppear(Karta);
		clickElementUsingActions(Karta);
		Save_Details.get(5).click();
	}

	
	public void ASM_Mapping() {
		ASM_Mapping.click();
		Select_ASM.click();
		Select_Division.click();
		Select_The_Searched_Recorder.click();
		Select_ASM.click();
		Select_The_Searched_Recorder.click();
		Click_On_Ok.click();
		Save_Details.get(6).click();
	}
}
