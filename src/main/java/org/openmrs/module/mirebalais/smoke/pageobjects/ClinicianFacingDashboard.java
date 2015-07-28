package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ClinicianFacingDashboard extends AbstractPageObject {

    public ClinicianFacingDashboard(WebDriver driver) {
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

}
