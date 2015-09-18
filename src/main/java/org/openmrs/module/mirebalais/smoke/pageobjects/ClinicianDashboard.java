package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ClinicianDashboard extends AbstractPageObject {

    public static final String ACTIVE_VISIT_CREOLE_MESSAGE = "Aktif";

    private static final By actions = By.className("action-section");

    private static final By startVisitAction = By.id("coreapps.createVisit");

    private static final By confirmStartVisit = By.cssSelector("#quick-visit-creation-dialog .confirm");

    public ClinicianDashboard(WebDriver driver) {
        super(driver);
    }

    public boolean isOpenForPatient(Patient patient) {

        try {
            // just make sure that a few core page elements exist
            driver.findElement(By.className("info-section"));
            driver.findElement(By.className("action-section"));
            driver.findElement(By.className("patient-header"));

            // patient specific information
            if (!driver.findElement(By.className("patient-header")).getText().contains(patient.getIdentifier())) {
                System.out.println("Did not find identifier " + patient.getIdentifier() + " in patient header.");
                return false;
            }

            return true;
        }
        catch (NoSuchElementException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

    }

    public boolean isOpenForPatient(String givenName, String familyName) {

        // just make sure that a few core page elements exist
        driver.findElement(By.className("info-section"));
        driver.findElement(By.className("action-section"));
        driver.findElement(By.className("patient-header"));


        try {
            // patient specific information
            if (!driver.findElement(By.className("patient-header")).getText().contains(familyName) ||
                    !driver.findElement(By.className("patient-header")).getText().contains(givenName)) {
                System.out.println("Did not find name " + givenName + " " + familyName + " in patient header.");
                return false;
            }

            return true;
        }
        catch (NoSuchElementException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

    }

    public boolean hasActiveVisit() {
        return driver.findElements(By.cssSelector(".tag")).get(0).getText().contains(ACTIVE_VISIT_CREOLE_MESSAGE);
    }


    public boolean isDead() {
        try {
            driver.findElement(By.className("death-message"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }


    public void startVisit() {
        clickOn(startVisitAction);
        clickOn(confirmStartVisit);
        wait15seconds.until(visibilityOfElementLocated(By.id("visit-details")));  // visit note should open
    }

    public void openRequestAppointmentForm() {
        clickOn(By.id("appointmentschedulingui.requestAppointment"));
    }

}
