package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
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
        setLoginPage(new GeneralLoginPage(driver));  // because we want to use the General login page here, not the Mirebalais
        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Tom " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Jones";
        String nickname = "Tommy";

        login();
        appDashboard.openPatientRegistrationApp();
        //click on the Register Patient button
        driver.findElement(By.id("register-patient-button")).click();

        registration.registerPatient(givenName, familyName, nickname, PatientRegistration.Gender.MALE, 22, 4, 1975, "louise", getPlaceOfBirthString(), getPersonAddressString(), "123-4567"
                , 1, 1, getReligion(), "dan", "cousin", getContactAddressString(), placeOfBirthAndContactAddressUseHierarchy(), "4312533", automaticallyEnterIdentifier(), getPrintIdCardOption(),
                getSuccessElement());

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
    }

    public void editExistingPatient() throws Exception {

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();

        initBasicPageObjects();
        setLoginPage(new GeneralLoginPage(driver));  // because we want to use the General login page here, not the Mirebalais
        PatientRegistration registration = new PatientRegistration(driver);

        login();
        appDashboard.openPatientRegistrationApp();

        registration.editExistingPatient(testPatient, "Billy", "Thorton", "Bob", PatientRegistration.Gender.FEMALE, 10, 10, 1950, "Mary");

        populateTestPatientForTearDown();
    }

    protected String getPersonAddressString() {
        return "";
    }

    protected String getPlaceOfBirthString() { return ""; }

    protected String getContactAddressString() {
        return "";
    }

    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

    protected Integer getReligion() { return null; }

    protected Boolean automaticallyEnterIdentifier() { return true; }

    protected Integer getPrintIdCardOption() { return 1; }

    protected By getSuccessElement() {
        return By.id("register-patient-button");
    }

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = ((Long) ((JavascriptExecutor) driver).executeScript("return patient.id")).toString();
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
