package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends AbstractPageObject {

	public HeaderPage(WebDriver driver) {
		super(driver);
	}

	public void logOut() {
		driver.findElement(By.className("logout")).click();
	}
	
	public String getLocation() {
		return driver.findElement(By.tagName("strong")).getText();
	}

}
