package org.openmrs.module.mirebalais.smoke.pageobjects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AwaitingAdmissionApp extends AbstractPageObject {

    private By awaitingAdmissionTable = By.id("awaiting-admission");

    private By admitButton = By.className("icon-h-sign");

    public AwaitingAdmissionApp(WebDriver driver) {
        super(driver);
    }

    public void assertPatientInAwaitingAdmissionTable(Patient testPatient) {
        assertTrue(patientInAwaitingAdmissionTable(testPatient));
    }

    public void assertPatientNotInAwaitingAdmissionTable(Patient testPatient) {
        assertFalse(patientInAwaitingAdmissionTable(testPatient));
    }

    private boolean patientInAwaitingAdmissionTable(Patient testPatient) {
        return driver.findElement(awaitingAdmissionTable).getText().contains(testPatient.getIdentifier());
    }

    public void clickOnFirstAdmitButton() {
        clickOn(admitButton);
    }


}
