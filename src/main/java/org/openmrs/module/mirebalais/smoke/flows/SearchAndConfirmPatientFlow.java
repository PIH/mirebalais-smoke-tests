package org.openmrs.module.mirebalais.smoke.flows;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchAndConfirmPatientFlow {
    private static final By CONFIRM_PATIENT_BUTTON = By.className("icon-arrow-right");

    public static final By SEARCH_FIELD = By.id("patient-search");

    private WebDriver driver;

    public SearchAndConfirmPatientFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void confirmPatient(String identifier) {
        enterPatientIdentifier(identifier);
        confirm();
    }

    private void enterPatientIdentifier(String patientID) {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        searchField.sendKeys(patientID);
        searchField.sendKeys(Keys.RETURN);
    }

    private void confirm() {
        driver.findElement(CONFIRM_PATIENT_BUTTON).click();
    }
}
