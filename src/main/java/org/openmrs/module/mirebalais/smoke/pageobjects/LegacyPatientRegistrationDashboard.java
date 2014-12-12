package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LegacyPatientRegistrationDashboard extends AbstractPageObject{

	
	public LegacyPatientRegistrationDashboard(WebDriver driver) {
		super(driver);
	}

	public String getIdentifier() {
		return driver.findElement(By.id("patientPreferredId")).getText();
	}

	public String getName() {
		return new StringBuilder()
				.append(driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[4]/td[1]")).getText())
				.append(" ")
				.append(driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText()).toString();
	}
	
	public String getGender() {
		return driver.findElement(By.id("tdGenderId")).getText();
	}

	
}
