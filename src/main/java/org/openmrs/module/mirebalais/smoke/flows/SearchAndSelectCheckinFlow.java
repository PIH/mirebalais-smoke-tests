package org.openmrs.module.mirebalais.smoke.flows;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchAndSelectCheckinFlow {
    private static final By CHECK_IN_PATIENT_BUTTON = By.id("pih.checkin.registrationAction");

    public static final By SEARCH_FIELD = By.id("patient-search");

    private WebDriver driver;

    public SearchAndSelectCheckinFlow(WebDriver driver) {
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
        driver.findElement(CHECK_IN_PATIENT_BUTTON).click();
    }
}
