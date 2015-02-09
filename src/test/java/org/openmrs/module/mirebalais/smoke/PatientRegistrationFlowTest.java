package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistration;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PatientRegistrationFlowTest extends DbTest {

    @Test
    public void registerNewPatient() throws Exception {

        initBasicPageObjects(new GeneralLoginPage(driver));
        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Tom " + new Random().nextInt(1000);  // append a randon number so patient name is (more or less) unique
        String familyName = "Jones";

        login();
        appDashboard.openPatientRegistrationApp();
        registration.registerPatient(givenName, familyName, PatientRegistration.Gender.MALE, 22, 1, 1975, "cange", "123-4567");

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByExactName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
    }

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = ((Long) ((JavascriptExecutor) driver).executeScript("return patient.id")).toString();
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
