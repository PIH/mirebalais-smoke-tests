package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MergeFlow extends AbstractPageObject {

	
	public MergeFlow(WebDriver driver) {
		super(driver);
	}

	public void setFirstPatient(String patientData) {
		setClearTextToField("choose-first-search", patientData);
	}
	
	public void setSecondPatient(String patientData) {
		setClearTextToField("choose-second-search", patientData);
	}

	public void setPatientsToMerge(String patientDataOne, String patientDataTwo) {
		setFirstPatient(patientDataOne);
		setSecondPatient(patientDataTwo);
		
		clickOnContinueMergeButton();
		clickOnContinueMergeButton(); // Yes, the button is the same, it just changes the caption!
		
		clickOnLeftPatient();
		clickOnPerformMerge();
	}

	private void clickOnPerformMerge() {
		driver.findElement(By.id("perform-merge")).click();
	}

	private void clickOnLeftPatient() {
		driver.findElement(By.id("choose1")).click();
	}

	private void clickOnContinueMergeButton() {
		driver.findElement(By.id("confirm-button")).click();
	}
	
}