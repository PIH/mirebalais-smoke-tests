package org.openmrs.module.mirebalais.smoke.flows;

import org.openmrs.module.mirebalais.smoke.pageobjects.CheckinFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckInPatientFlow {
    private WebDriver driver;
    private SearchAndConfirmPatientFlow searchAndConfirmPatientFlow;
    private CheckinFormPage checkinFormPage;

    public CheckInPatientFlow(WebDriver driver) {
        this.driver = driver;
        searchAndConfirmPatientFlow = new SearchAndConfirmPatientFlow(driver);
        checkinFormPage = new CheckinFormPage(driver);
    }

    public void checkInAndCreateLocalDossierFor(String patientId) {
        confirmPatient(patientId);
        checkIn();
    }

    public void checkIn() {
        checkinFormPage.enterInfo();

        // click on the confirm button of either the request or create dialog (whichever one opens)
        driver.findElement(By.cssSelector("#request-paper-record-dialog .confirm, #create-paper-record-dialog .confirm")).click();
    }

    public void confirmPatient(String patientId) {
        searchAndConfirmPatientFlow.confirmPatient(patientId);
    }

    public void checkInWithMultipleEnterKeystrokesOnSubmit(String patientId) {
        confirmPatient(patientId);
        checkinFormPage.enterInfoWithMultipleEnterKeystrokesOnSubmit();
    }
}
