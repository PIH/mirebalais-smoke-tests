package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AwaitingAdmissionApp extends AbstractPageObject {

    private By awaitingAdmissionTable = By.id("awaiting-admission");

    private By admitButtons = By.cssSelector(".fa-hospital-symbol");

    private By cancelButtons = By.cssSelector(".fa-user-minus");

    private By cancelReasonSelector = By.id("cancel-reason");

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

    public void clickOnLastAdmitButton() {
        clickOnLast(admitButtons);
    }

    public void clickOnLastCancelButton() {
        clickOnLast(cancelButtons);
    }

    public void cancelLastAdmission() {
        clickOnLastCancelButton();
        driver.findElement(cancelReasonSelector).findElement(By.cssSelector("option:nth-of-type(6)")).click(); // hack, depends on ordering of elements
        clickOn(By.className("submitButton"));
        wait5seconds.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm-cancel-admission-button")));
        clickOn(By.id("confirm-cancel-admission-button"));
    }

}
