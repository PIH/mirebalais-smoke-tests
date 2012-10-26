package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ArchivesRoom {

	private WebDriver driver;
	
	public ArchivesRoom(WebDriver driver) {
		this.driver = driver;
	}

	public void openArchivesRoomApp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
		driver.findElement(By.linkText("Archives Room")).click();
	}
	
}
