package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LacollineIntegrationFlow extends AbstractPageObject {

	public LacollineIntegrationFlow(WebDriver driver) {
		super(driver);
	}

	public void searchByName(String patientName) {
		driver.findElement(By.cssSelector("#search-by-name input")).sendKeys(patientName);
		clickOn(By.id("search-button"));
	}

	public boolean isImportButtonPresented() {
		return driver.findElement(By.className("import-patient")).isDisplayed();
	}
	
}
