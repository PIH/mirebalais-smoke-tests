package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FindPatient extends AbstractPageObject{
	
	private WebDriver driver;

	public FindPatient(WebDriver driver) {
		super(driver);
	}

	public void findPatient(String name) {
		driver.findElement(By.linkText("Find a Patient")).click();
		driver.findElement(By.linkText(name)).click();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
