package org.openmrs.module.mirebalais.smoke.flows;

import org.openmrs.module.mirebalais.smoke.pageobjects.CheckinFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

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

        new WebDriverWait(driver, 15).until(visibilityOfElementLocated(By.className("confirm")));
        driver.findElement(By.className("confirm")).click();
    }

    public void confirmPatient(String patientId) {
        searchAndConfirmPatientFlow.confirmPatient(patientId);
    }

    public void checkInWithMultipleEnterKeystrokesOnSubmit(String patientId) {
        confirmPatient(patientId);
        checkinFormPage.enterInfoWithMultipleEnterKeystrokesOnSubmit();

        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(By.className("confirm")));
        driver.findElement(By.className("confirm")).click();
    }
}
