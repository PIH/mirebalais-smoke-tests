package org.openmrs.module.mirebalais.smoke.flows;

import org.openmrs.module.mirebalais.smoke.pageobjects.CheckInFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckInPatientFlow {
    private WebDriver driver;
    private CheckInFormPage checkinFormPage;

    public static final By SEARCH_FIELD = By.id("patient-search");
    public static final By CHECK_IN_PATIENT_BUTTON = By.id("pih.checkin.registrationAction");
    public static final By CONTINUE_BUTTON = By.id("continue");

    public CheckInPatientFlow(WebDriver driver) {
        this.driver = driver;
        checkinFormPage = new CheckInFormPage(driver);
    }

    public void checkInAndCreateLocalDossierFor(String patientId) {
        enterPatientIdentifier(patientId);
        checkIn();
    }

    public void checkIn() {
        driver.findElement(CHECK_IN_PATIENT_BUTTON).click();
        checkinFormPage.enterInfo(false);
        // click on the confirm button of either the request or create dialog (whichever one opens)
        driver.findElement(By.cssSelector("#request-paper-record-dialog .confirm, #create-paper-record-dialog .confirm")).click();
    }

    public void findPatientAndSelectContinue(String patientId) {
        enterPatientIdentifier(patientId);
        driver.findElement(CONTINUE_BUTTON).click();
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
