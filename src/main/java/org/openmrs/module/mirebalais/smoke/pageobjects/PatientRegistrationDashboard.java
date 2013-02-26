package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PatientRegistrationDashboard extends AbstractPageObject{

	
	public static final String CHECKIN = "Check-in";
	public static final String CONSULTATION = "Consultation";
	public static final String VITALS = "Vitals";

	public PatientRegistrationDashboard(WebDriver driver) {
		super(driver);
	}

	public String getIdentifier() {
		return driver.findElement(By.id("patientPreferredId")).getText();
	}

	public String getName() {
		return driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText();
	}
	
	public String getGender() {
		return driver.findElement(By.id("tdGenderId")).getText();
	}

	public boolean hasActiveVisit() {
		return driver.findElement(By.id("visit-details")).getText().contains("Active Visit");
	}

	public void deleteEncounter(String encounterName) throws Exception {
		String encounterId = findEncounterId(encounterName);
		List<WebElement> encounters = driver.findElements(By.cssSelector("i.deleteEncounterId"));
		for (WebElement encounter : encounters) {
	        if (encounter.getAttribute("data-encounter-id").equals(encounterId))
	        	encounter.click();
	    }
		driver.findElement(By.cssSelector("button.confirm")).click();
	}
	
	public String findEncounterId(String encounterName) throws Exception {
		List<WebElement> encounters = driver.findElements(By.cssSelector("span.encounter-name"));
		for (WebElement encounter : encounters) {
	        if (encounter.getText().equals(encounterName))
	        	return encounter.getAttribute("data-encounter-id");
	    }
		
		throw new Exception("No encounter of this type found.");
	}

	
}
