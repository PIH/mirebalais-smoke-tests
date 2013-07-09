package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ArchivesRoomApp extends AbstractPageObject {

	public ArchivesRoomApp(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPatientInList(String patientIdentifier, String list) {
		try {
			findPatientInTheList(patientIdentifier, list);
		    return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement findPatientInTheList(String patientIdentifier, String list) {
		return driver.findElement(By.id(list)).findElement(By.id(patientIdentifier));
	}
	
	public void sendDossier(String dossieNumber) {
		driver.findElement(By.name("mark-as-pulled-identifier")).sendKeys(dossieNumber);
		driver.findElement(By.name("mark-as-pulled-identifier")).sendKeys(Keys.RETURN);
	}

	public void returnRecord(String dossieNumber) {
		driver.findElement(By.id("tab-selector-return")).click();
		driver.findElement(By.name("mark-as-returned-identifier")).sendKeys(dossieNumber);
		driver.findElement(By.name("mark-as-returned-identifier")).sendKeys(Keys.RETURN);
	}

    public void goToPullTab() {
        driver.findElement(By.id("tab-selector-pull")).click();
    }

	public String createRecord(String patientIdentifier) throws Exception {
		findPatientInTheList(patientIdentifier, "create_requests_table").click();
		clickOnAssign();
        Waiter.waitForElementToDisplay(By.cssSelector("#assigned_create_requests_table #" + patientIdentifier), 5, driver);
        return getDossieNumber(patientIdentifier);
	}

    private void clickOnAssign() {
		driver.findElement(By.id("assign-to-create-button")).click();
	}

	private String getDossieNumber(String patientIdentifier) throws Exception {
        return driver.findElement(By.id(patientIdentifier)).findElement(By.cssSelector("td:nth-child(2)")).getText();
	}
}
