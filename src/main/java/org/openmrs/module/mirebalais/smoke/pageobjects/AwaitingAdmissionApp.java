package org.openmrs.module.mirebalais.smoke.pageobjects;

import static org.junit.Assert.assertTrue;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AwaitingAdmissionApp extends AbstractPageObject {

    By awaitingAdmissionTable = By.id("awaiting-admission");

    public AwaitingAdmissionApp(WebDriver driver) {
        super(driver);
    }

    public void assertPatientInAwaitingAdmissionTable(Patient testPatient) {
        assertTrue(driver.findElement(awaitingAdmissionTable).getText().contains(testPatient.getIdentifier()));
    }
}
