package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

public class FindPatient {
	
	private WebDriver driver;

	public FindPatient(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
	}

	public void findPatient(String name) {
		driver.findElement(By.linkText("Find a Patient")).click();
		driver.findElement(By.linkText(name)).click();
	}
}
