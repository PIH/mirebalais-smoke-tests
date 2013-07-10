package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckIn extends AbstractPageObject {

    public CheckIn(WebDriver driver) {
		super(driver);
	}

	
	public void checkInPatient(final Patient testPatient) {
		driver.findElement(By.id("patientIdentifier")).sendKeys(testPatient.getIdentifier());
		clickNext();
		
		wait5seconds.until(new ExpectedCondition<Boolean>() {
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

        wait5seconds.until(ExpectedConditions.visibilityOfElementLocated(By.id("medicalRecordLocationBtn")));
        driver.findElement(By.id("medicalRecordLocationBtn")).click();
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
