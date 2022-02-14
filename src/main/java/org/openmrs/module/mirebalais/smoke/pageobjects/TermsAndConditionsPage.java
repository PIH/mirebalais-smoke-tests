package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TermsAndConditionsPage {

	protected WebDriver driver;

	public TermsAndConditionsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void acceptTermsIfPresent() {
		try {
			driver.findElement(By.id("termsAcceptCheckbox-field")).click();
			driver.findElement(By.id("save-button")).click();
		}
		catch (Exception e) {
		}
	}
}
