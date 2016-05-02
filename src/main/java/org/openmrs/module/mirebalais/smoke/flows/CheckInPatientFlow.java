package org.openmrs.module.mirebalais.smoke.flows;

import org.openmrs.module.mirebalais.smoke.pageobjects.CheckInFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckInPatientFlow {
    private WebDriver driver;
    private SearchAndSelectCheckinFlow searchAndSelectCheckinFlow;
    private CheckInFormPage checkinFormPage;

    public CheckInPatientFlow(WebDriver driver) {
        this.driver = driver;
        searchAndSelectCheckinFlow = new SearchAndSelectCheckinFlow(driver);
        checkinFormPage = new CheckInFormPage(driver);
    }

    public void checkInAndCreateLocalDossierFor(String patientId) {
        confirmPatient(patientId);
        checkIn();
    }

    public void checkIn() {
        checkinFormPage.enterInfo(false);

        // click on the confirm button of either the request or create dialog (whichever one opens)
        driver.findElement(By.cssSelector("#request-paper-record-dialog .confirm, #create-paper-record-dialog .confirm")).click();
    }

    public void confirmPatient(String patientId) {
        searchAndSelectCheckinFlow.confirmPatient(patientId);
    }

    public void checkInWithMultipleEnterKeystrokesOnSubmit(String patientId) {
        confirmPatient(patientId);
        checkinFormPage.enterInfoWithMultipleEnterKeystrokesOnSubmit();
    }
}
