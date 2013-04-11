package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MergeFlow extends AbstractPageObject {

	
	public MergeFlow(WebDriver driver) {
		super(driver);
	}

	public void setFirstPatient(String patientData) {
		setClearTextToField("patient1-text", patientData);
	}
	
	public void setSecondPatient(String patientData) {
		setClearTextToField("patient2-text", patientData);
	}

	public void setPatientsToMerge(String patientDataOne, String patientDataTwo) {
		setFirstPatient(patientDataOne);
		setSecondPatient(patientDataTwo);

        waitUntilContinueMergeButtonEnabled();
        clickOnContinueMergeButton();
		clickOnLeftPatient();
		clickOnContinueMergeButton(); // Yes, the button is the same, it just changes the caption!
	}

	private void clickOnLeftPatient() {
		driver.findElement(By.id("first-patient")).click();
	}

	private void clickOnContinueMergeButton() {
		driver.findElement(By.className("confirm")).click();
	}

    private void waitUntilContinueMergeButtonEnabled() {

        Wait<WebDriver> wait = new WebDriverWait(driver, 2);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return driver.findElement(By.className("confirm")).isEnabled();
            }
        });

    }
	
}
