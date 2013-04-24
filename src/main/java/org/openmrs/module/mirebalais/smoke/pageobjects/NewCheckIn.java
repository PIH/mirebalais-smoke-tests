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
	
	public NewCheckIn(WebDriver driver) {
		super(driver);
	}
	
	public void checkInPatient(String patientIdentifier) {
		findPatient(patientIdentifier);
		confirmRightPatient();
		clickOnPaymentOption("50");
		confirmData();
		confirmPopup();
	}

	private void findPatient(String patientIdentifier) {
		super.findPatientById(patientIdentifier, "patient-search-field-search");
	}

	private void confirmRightPatient() {
		driver.findElement(By.className("icon-arrow-right")).click();
	}
	
	private void clickOnPaymentOption(String payment) {
		List<WebElement> options = driver.findElements(By.cssSelector("#paymentAmount option"));
		for (WebElement option : options) {
	        if(option.getText().contains(payment)) {
	            option.click();
	        }
	    }
	}
	
	private void confirmData() {
		List<WebElement> elements = driver.findElements(By.cssSelector("#formBreadcrumb span"));
		for (WebElement element : elements) {
	        if(element.getText().contains(CONFIRM_TEXT)) {
	        	element.click();
	        }
	    }
		
		driver.findElement(By.cssSelector("#confirmationQuestion input")).click();
	}
	
	private void confirmPopup() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.id("request-paper-record-dialog")).isDisplayed();
			}
		});
		driver.findElement(By.cssSelector("#request-paper-record-dialog button")).click();
	}

	public boolean isPatientSearchDisplayed() {
		return driver.findElement(By.id("patient-search-field-search")).isDisplayed();
	}
}	