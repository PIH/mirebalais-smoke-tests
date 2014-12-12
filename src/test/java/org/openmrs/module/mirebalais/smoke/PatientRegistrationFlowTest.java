package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

public class PatientRegistrationFlowTest extends DbTest {

    @Test
    public void registerNewPatient() throws Exception {
        initBasicPageObjects();

        login();

        //appDashboard.openPatientRegistrationApp();
        //registration.goThruRegistrationProcessPrintingCard();
        //populateTestPatientForTearDown();

        //assertThat(driver.findElement(By.tagName("body")).getText(), containsString(SCAN_MESSAGE));
    }

    private void populateTestPatientForTearDown() throws Exception {
        //String patientId = (String) ((JavascriptExecutor) driver).executeScript("return patientId");
        //PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
