package GowriPrabhu.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import GowriPrabhu.AbstractComponents.AbstractComponent;

public class ApprovalEngine extends AbstractComponent {
	WebDriver driver;

	public ApprovalEngine(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//div[@class = 'cls_section_level2']")
	List<WebElement> ListOfApprovals;

	@FindBy(xpath = "//button[normalize-space()='Approve']")
	WebElement ApproveButton;

	@FindBy(xpath = "//button[normalize-space()='Reject']")
	WebElement RejectButton;

	@FindBy(xpath = "//button[@class='cls_comman_btn'][normalize-space()='Approve']")
	WebElement ConfirmationApproveButton;

	@FindBy(xpath = "//button[normalize-space()='Yes']")
	WebElement FianlConfirmation;
	
	public void ApproveThePurchaseOrder() {
		waitForElementToVisible(ApproveButton);
		scrollToElement(ApproveButton);
		ApproveButton.click();
	}

	public void RejectThePurchaseOrder() {
		waitForElementToVisible(RejectButton);
		scrollToElement(RejectButton);
		RejectButton.click();
	}

	public void ApprovalConfirmation() {
		waitForElementToVisible(ConfirmationApproveButton);
		ConfirmationApproveButton.click();
	}
	
	public void Final_Confirmation() {
		// TODO Auto-generated method stub
		waitForElementToVisible(FianlConfirmation);
		waitForWebElementToBeClickable(FianlConfirmation);
		FianlConfirmation.click();
	}
	public int getApprovalCount() {
	    int count = ListOfApprovals.size();
	    System.out.println("✅ Found " + count + " approval(s) required.");
	    return count;
	}

	
	
	
}
