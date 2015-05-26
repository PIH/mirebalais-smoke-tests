package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public abstract class PatientRegistrationFlowTest extends DbTest {

    public void registerNewPatient() throws Exception {
        initBasicPageObjects();
        setLoginPage(new GeneralLoginPage(driver));  // because we want to use the General login page here, not the Mirebalaison
        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Tom " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Jones";
        String nickname = "Tommy";

        login();
        appDashboard.openPatientRegistrationApp();
        //click on the Register Patient button
        driver.findElement(By.id("register-patient-button")).click();

        registration.registerPatient(givenName, familyName, nickname, PatientRegistration.Gender.MALE, 22, 4, 1975, "louise", "mirebalais", getPersonAddressSearchString(), "123-4567"
                , 1, 1, 2, "dan", "cousin", getContactAddressSearchString(), "4312533", 1);

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
    }

    protected String getPersonAddressSearchString() {
        return "";
    }

    protected String getContactAddressSearchString() {
        return "";
    }

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = ((Long) ((JavascriptExecutor) driver).executeScript("return patient.id")).toString();
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
