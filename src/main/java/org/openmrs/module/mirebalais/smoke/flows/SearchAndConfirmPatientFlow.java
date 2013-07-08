package org.openmrs.module.mirebalais.smoke.flows;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchAndConfirmPatientFlow {
    private static final By CONFIRM_PATIENT_BUTTON = By.className("icon-arrow-right");

    private WebDriver driver;

    public SearchAndConfirmPatientFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void confirmPatient(String identifier) {
        enterPatientIdentifier(identifier);
        confirm();
    }

    private void enterPatientIdentifier(String patientID) {
        WebElement searchField = driver.findElement(By.id("patient-search-field-search"));
        searchField.sendKeys(patientID);
        searchField.sendKeys(Keys.RETURN);
    }

    private void confirm() {
        driver.findElement(CONFIRM_PATIENT_BUTTON).click();
    }
}
