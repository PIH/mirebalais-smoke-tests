package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewCheckIn extends AbstractPageObject {
	
	private static final String CONFIRM_TEXT = "Konfime";
	private static final String PAYMENT_EXEMPT = "Exempt";
	private static final String PAYMENT_50 = "50";
	
	public NewCheckIn(WebDriver driver) {
		super(driver);
	}
	
	public void checkInPatient(String patientIdentifier) throws Exception {
		findPatient(patientIdentifier);
		confirmRightPatient();
		clickOnPaymentOption(PAYMENT_50);
		confirmData();
		confirmPopup();
	}

	public void checkInPatientFillingTheFormTwice(String patientIdentifier) throws Exception {
		findPatient(patientIdentifier);
		confirmRightPatient();
		clickOnPaymentOption(PAYMENT_50);
		clickOnNoButton();
		clickOnPaymentOption(PAYMENT_EXEMPT);
		confirmData();
		confirmPopup();
	}
	
	private void findPatient(String patientIdentifier) throws Exception {
		super.findPatientById(patientIdentifier, "patient-search-field-search");
	}

	private void confirmRightPatient() {
		driver.findElement(By.className("icon-arrow-right")).click();
	}
	
	private void clickOnPaymentOption(String payment) throws Exception{
		clickOnOptionLookingForText(payment, By.cssSelector("#paymentAmount option"));
	}
	
	private void confirmData() {
		clickOnConfirmationTab();
		driver.findElement(By.cssSelector("#confirmationQuestion input.confirm")).click();
	}
	
	private void clickOnNoButton() {
		clickOnConfirmationTab();
		driver.findElement(By.cssSelector("#confirmationQuestion input.cancel")).click();
	}
	
	private void clickOnConfirmationTab() {
		List<WebElement> elements = driver.findElements(By.cssSelector("#formBreadcrumb span"));
		for (WebElement element : elements) {
	        if(element.getText().contains(CONFIRM_TEXT)) {
	        	element.click();
	        }
	    }
	}
	
	private void confirmPopup() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("request-paper-record-dialog")).isDisplayed();
			}
		});
		driver.findElement(By.cssSelector("#request-paper-record-dialog button")).click();
	}

	public boolean isPatientSearchDisplayed() {
		return driver.findElement(By.id("patient-search-field-search")).isDisplayed();
	}
	
}	