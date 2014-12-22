package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistration;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;

public class PatientRegistrationFlowTest extends DbTest {

    @Test
    public void registerNewPatient() throws Exception {

        initBasicPageObjects(new GeneralLoginPage(driver));
        PatientRegistration registration = new PatientRegistration(driver);

        login();
        appDashboard.openPatientRegistrationApp();
        registration.registerPatient("Tom", "Jones", PatientRegistration.Gender.MALE, 22, 1, 1975, "123-4567");

        // TODO: re-implement once updated for new address hierarchy

        //assertTrue(new ClinicianDashboard(driver).isOpenForPatient("Tom", "Jones"));
        // TODO: test that identifier has been assigned?

        //populateTestPatientForTearDown();
    }

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = ((Long) ((JavascriptExecutor) driver).executeScript("return patient.id")).toString();
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
