package org.openmrs.module.pihemr.smoke.flows;

import org.openmrs.module.pihemr.smoke.pageobjects.CheckInFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckInPatientFlow {
    private WebDriver driver;
    private CheckInFormPage checkinFormPage;

    public static final By SEARCH_FIELD = By.id("patient-search");
    public static final By CHECK_IN_PATIENT_BUTTON = By.id("pih.checkin.registrationAction");

    public CheckInPatientFlow(WebDriver driver) {
        this.driver = driver;
        checkinFormPage = new CheckInFormPage(driver);
    }

    public void checkInWithMultipleEnterKeystrokesOnSubmit(String patientId) {
        enterPatientIdentifier(patientId);
        driver.findElement(CHECK_IN_PATIENT_BUTTON).click();
        checkinFormPage.enterInfoWithMultipleEnterKeystrokesOnSubmit();
    }

    private void enterPatientIdentifier(String patientID) {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        searchField.sendKeys(patientID);
        searchField.sendKeys(Keys.RETURN);
    }



}
