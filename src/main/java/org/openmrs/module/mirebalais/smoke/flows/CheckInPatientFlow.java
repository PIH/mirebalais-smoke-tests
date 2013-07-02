package org.openmrs.module.mirebalais.smoke.flows;

import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckinFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckInPatientFlow {
    private WebDriver driver;
    private SearchAndConfirmPatientFlow searchAndConfirmPatientFlow;

    public CheckInPatientFlow(WebDriver driver) {
        this.driver = driver;
        searchAndConfirmPatientFlow = new SearchAndConfirmPatientFlow(driver);
    }

    public void checkInAndCreateLocalDossierFor(String patientId) {
        searchAndConfirmPatientFlow.confirmPatient(patientId);
        new CheckinFormPage(driver).enterInfo();

        Waiter.waitForElementToDisplay(By.className("confirm"), 5, driver);
        driver.findElement(By.className("confirm")).click();
    }

    public void enterPatientIdentifier(String patientIdentifier) {
        searchAndConfirmPatientFlow.enterPatientIdentifier(patientIdentifier);
    }
}
