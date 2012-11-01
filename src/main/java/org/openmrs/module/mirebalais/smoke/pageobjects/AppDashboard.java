package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AppDashboard extends AbstractPageObject {

	public void openActiveVisitsApp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
		driver.findElement(By.linkText("Active Visits")).click();
	}
	
	public AppDashboard(WebDriver driver) {
		super(driver);
	}

	public void openArchivesRoomApp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
		driver.findElement(By.linkText("Archives Room")).click();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
