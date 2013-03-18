package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ArchivesRoomApp extends AbstractPageObject {

	public ArchivesRoomApp(WebDriver driver) {
		super(driver);
	}

	public boolean isPatientInList(String patientIdentifier, String list) {
		try {
			findPatientInTheList(patientIdentifier, list);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

	public WebElement findPatientInTheList(String patientIdentifier, String list) throws Exception {
		List<WebElement> elements = driver.findElement(By.id(list)).findElements(By.tagName("span"));
		for(WebElement element : elements) {
			if (element.getText().contains(patientIdentifier)) {
				return element;
			}
		}
		throw new Exception("Patient not found");
	}
	
}
