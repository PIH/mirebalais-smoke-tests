package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistration;
import org.openqa.selenium.By;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PatientRegistrationLiberiaFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        initBasicPageObjects();
        setLoginPage(getLoginPage());
        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Tom " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Jones";
        String nickname = "Tommy";

        login();
        appDashboard.openPatientRegistrationApp();
        //click on the Register Patient button
        driver.findElement(By.id("register-patient-button")).click();

        registration.registerPatient(givenName, familyName, nickname, PatientRegistration.Gender.MALE, 22, 4, 1975, "louise", getPlaceOfBirthString(),
                getPersonAddressString(), getContactPhoneNumber(), getInsuranceName(),getInsuranceNumberString(), getOtherInsuranceNameString(), getMaritalStatus(), getOccupation(), getReligion(),
                getContact(), getRelationship(), getContactAddressString(), placeOfBirthAndContactAddressUseHierarchy(), "0885778899", automaticallyEnterIdentifier(), getRelationshipsEnabled(), getBiometricsEnabled(), getPrintIdCardOption(),
                getAdditionalIdentifiersEnabled(), getSuccessElement());

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
        logout();
    }

    @Test
    @Override
    public void editExistingPatient() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();

        initBasicPageObjects();
        setLoginPage(getLoginPage());
        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Billy " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Thorton";
        String nickname = "Bob";

        login();
        header.home();
        appDashboard.openPatientRegistrationApp();

        registration.editExistingPatient(testPatient, givenName, familyName, nickname, PatientRegistration.Gender.FEMALE, 10, 10, 1950, "Mary",
                getPersonAddressString(), getContact(), getRelationship(), placeOfBirthAndContactAddressUseHierarchy(), "231775334455");

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
        logout();
    }

    @Override
    protected String getPersonAddressString() {
        return "Zeno";
    }

    @Override
    protected String getPlaceOfBirthString() { return "Zeno"; }

    @Override
    protected String getContact() {
        return null;
    }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return false; }

    @Override
    protected Integer getInsuranceName() {
        return null;
    }

    @Override
    protected String getInsuranceNumberString() {
        return "";
    }

    @Override
    protected String getOtherInsuranceNameString() {
        return null;
    }

    @Override
    protected Integer getMaritalStatus() {
        return null;
    }

    @Override
    protected Integer getOccupation() { return null; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Boolean getBiometricsEnabled() {
        return true;
    }

    @Override
    protected String getContactPhoneNumber() {
        return "+231881112233";
    }

    protected By getSuccessElement() {
        return By.id("register-patient-button");  // Liberia redirects to registration home page
    }

}
