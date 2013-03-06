package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ArchivesRoomApp extends AbstractPageObject {

	public ArchivesRoomApp(WebDriver driver) {
		super(driver);
	}

	public boolean isNewRecordRequested(String patientIdentifier) {
		List<WebElement> elements = driver.findElement(By.id("create_requests_table")).findElements(By.tagName("span"));
		for(WebElement element : elements) {
			if (element.getText().contains(patientIdentifier)) {
				return true;
			}
		}
		return false;
	}

	
	
}
