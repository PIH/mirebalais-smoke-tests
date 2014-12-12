package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ClinicianDashboard extends AbstractPageObject {

    public ClinicianDashboard(WebDriver driver) {
        super(driver);
    }

    /**
     * Confirm that the patient dashboard is currently open for the specified patient
     * @param patient
     * @return
     */
    public boolean isOpenForPatient(String givenName, String familyName) {

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
}
