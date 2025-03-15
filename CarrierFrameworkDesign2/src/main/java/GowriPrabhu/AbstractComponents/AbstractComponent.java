package GowriPrabhu.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import GowriPrabhu.pageobjects.CreatePurchaseOrder;



public class AbstractComponent {

	protected WebDriver driver;
	Actions a ;
	public AbstractComponent(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super();
		this.driver = driver;
		 this.a = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[normalize-space()='Sales Management']")
	WebElement SalesManagement;
	
	@FindBy(xpath = "//button[normalize-space()='Add Product']")
	WebElement AddProducts;
	
	@FindBy(xpath = "//button[normalize-space()='Filter']")
	WebElement FilterButton;
	
	@FindBy(xpath = "//button[normalize-space()='Search']")
	WebElement SearchButton;
	
	public void waitForElementToAppear(WebElement element) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	    wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void LongwaitForElementToAppear(WebElement findBy ) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	    wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	public void waitForListOFElementsToAppear(List<WebElement> findBy) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    wait.until(ExpectedConditions.visibilityOfAllElements(findBy));
	}
	
	public void waitForWebElementToBeClickable(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		// Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(findBy));

	}
	public void waitForElementToInvisible(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	public void waitForElementToVisible(WebElement element ) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	    wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void hoverOverElement(WebElement element) {
	    
	    a.moveToElement(element).perform();
	}
	
	public void clickElementUsingActions(WebElement element) {
	   
	    a.moveToElement(element).click().perform();
	}
	
	public void clickElementUsingAction(WebElement findBy) {
		   
	    a.moveToElement(findBy).click().perform();
	}
	
	public void doubleClickElement(WebElement element) {
	    
	    a.doubleClick(element).perform();
	}
	
	public void rightClickElement(WebElement element) {
	    
	    a.contextClick(element).perform();
	}
	
	public void sendKeysUsingActions(WebElement element, String text) {
	    
	    a.moveToElement(element).click().sendKeys(text).perform();
	}
	
	public void scrollToElement(WebElement element) {
	    
	    a.moveToElement(element).perform();
	}
	
public void scrollToElementBy(WebElement findby) {
	    
	    a.moveToElement(findby).perform();
	}
	
	public void pressEnterUsingActions(WebElement element) {
	    
	    a.moveToElement(element).sendKeys(Keys.ENTER).perform();
	}
	
public void PageDownUsingActions(WebElement element) {
	    
	    a.moveToElement(element).sendKeys(Keys.PAGE_DOWN).perform();
	}
	public CreatePurchaseOrder MouseHoverToSalesManagement() {
		waitForElementToAppear(SalesManagement);
		hoverOverElement(SalesManagement);
		CreatePurchaseOrder C_PO = new CreatePurchaseOrder(driver);
		return C_PO;
	}
	
	
	public CreatePurchaseOrder ClickOnAddProductsButton() {
		AddProducts.click();
		return null;
		
	}
		

	public void CommonFilterButton() {
		FilterButton.click();
		}
	public void CommonSearchButton() {
		waitForWebElementToBeClickable(SearchButton);
		SearchButton.click();
		}
	
	}

	

