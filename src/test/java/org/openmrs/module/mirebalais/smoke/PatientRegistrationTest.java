package org.openmrs.module.mirebalais.smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * A minimalistic test for Patient Registration. This simply navigates to the registration page
 * and asserts that the page loaded correctly.
 */
public class PatientRegistrationTest extends BasicSmokeTest {

    public void goToRegistration() throws Exception {
        login();
        appDashboard.openPatientRegistrationApp();
        driver.findElement(By.id("register-patient-button")).click();
        new WebDriverWait(driver, 15).until(visibilityOfElementLocated(By.id("checkbox-enable-registration-date")));
    }
}
