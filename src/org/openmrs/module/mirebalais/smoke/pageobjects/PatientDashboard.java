package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientDashboard {

	private WebDriver driver;

	public PatientDashboard(WebDriver driver) {
		this.driver = driver;
	}
	
	public String generateDossieNumber() {
		driver.findElement(By.id("generateDossierNumber")).click();
		String result = driver.findElement(By.id("dossierNumber")).getText();
		driver.findElement(By.id("checkmark-yellow")).click();
		return result;
	}

}
