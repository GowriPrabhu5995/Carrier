package GowriPrabhu.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GowriPrabhu.AbstractComponents.AbstractComponent;

public class Landingpage extends AbstractComponent {
	String baseUrl;
	public Landingpage(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		//this.baseUrl = baseUrl;
		PageFactory.initElements(driver, this);

	}

//WebElement userEmailId =	driver.findElement(By.xpath("//input[@id='userEmail']"));
//pagefactory

	@FindBy(xpath = "//input[@id='txtUsername']")
	WebElement loginID;

	@FindBy(xpath = "//input[@id='txtPassword']")
	WebElement Password;

	@FindBy(xpath = "//input[@class='cls_n_bttn']")
	WebElement Submit;

	@FindBy(xpath = "//div[@id='toast-container']")
	WebElement Login_Toast_Msg;

	public void login(String ID, String password) throws InterruptedException {
		Thread.sleep(5000);
		waitForElementToAppear(loginID);
		loginID.sendKeys(ID);
		Password.sendKeys(password);
		Submit.click();

	}

	public String getLoginMsg() {
		waitForElementToAppear(Login_Toast_Msg);
		return Login_Toast_Msg.getText();

	}
	public void goTo(String url) {
		// driver.get("https://google.com");
		driver.get(url);
		driver.manage().window().maximize();
		//driver.get("https://rahulshettyacademy.com/client/");
	}
public void goTostaticurl() {
		
//		//driver.get("https://cmisarathitest.carriermidea.in/winitapp/");
	driver.get("https://netcoretest.winitsoftware.com/winitapp/");
	}
}
