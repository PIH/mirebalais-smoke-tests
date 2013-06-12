package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckIn extends AbstractPageObject {

	private Wait<WebDriver> wait;
	
	public CheckIn(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver,2);
	}

	
	public void checkInPatient(final Patient testPatient) {
		driver.findElement(By.id("patientIdentifier")).sendKeys(testPatient.getIdentifier());
		clickNext();
		
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.id("okBtn")).isDisplayed() &&
						webDriver.findElement(By.className("confirmExistingPatientModalList")).getText().contains(format(testPatient.getName())) ;
			}
		});
		driver.findElement(By.id("okBtn")).click();
		
		clickNext();
		clickNext();
		choosePaymentAmount(PAYMENT_50);
        clickYellowCheckMark();

        Waiter.waitForElementToDisplay(By.id("medicalRecordLocationBtn"), 5, driver);
        driver.findElement(By.id("medicalRecordLocationBtn")).click();
/*
    	Waiter.waitForElementToDisplay(By.id("okDialog"), 5, driver);
		driver.findElement(By.id("okDialog")).click();
*/
	}
	
	public void enterReceiptNumber(String receiptNumber) {
        driver.findElement(By.id("receiptInput")).sendKeys(receiptNumber);
    }

    public void choosePaymentAmount(String paymentAmountElementId) {
        driver.findElement(By.id(paymentAmountElementId)).click();
    }

    public void chooseVisitReason(String visitReasonElementId) {
        driver.findElement(By.id(visitReasonElementId)).click();
    }
    
    public static final String MEDICAL_CERTIFICATE = "visitReasonStatus1";
    public static final String STANDART_DENTAL_VISIT = "visitReasonStatus2";
    public static final String MARRIAGE_CERTIFICATE = "visitReasonStatus3";
    public static final String STANDART_OUTPATIENT = "visitReasonStatus4";
    
    public static final String PAYMENT_0 = "paymentAmountStatus3";
    public static final String PAYMENT_50 = "paymentAmountStatus1";
    public static final String PAYMENT_100 = "paymentAmountStatus2";
    
}
