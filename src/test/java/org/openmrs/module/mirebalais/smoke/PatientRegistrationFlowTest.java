package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public abstract class PatientRegistrationFlowTest extends DbTest {

    public void registerNewPatient() throws Exception {

        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Tom " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Jones";
        String nickname = "Tommy";

        login();
        appDashboard.openPatientRegistrationApp();
        //click on the Register Patient button
        driver.findElement(By.id("register-patient-button")).click();

        registration.registerPatient(givenName, familyName, nickname, PatientRegistration.Gender.MALE, 22, 4, 1975, getMothersFirstName(), getPlaceOfBirthString(),
                getPersonAddressString(), getContactInfo(), getInsuranceName(), getInsuranceNumberString(), getOtherInsuranceNameString(), getMaritalStatus(), getEducation(), getOccupation(), getReligion(),
                getContact(), getRelationship(), getContactAddressString(), placeOfBirthAndContactAddressUseHierarchy(), getContactPhoneNumber(), automaticallyEnterIdentifier(), getRelationshipsEnabled(), getBiometricsEnabled(), getPrintIdCardOption(),
                getAdditionalIdentifiersEnabled(), getIdUuids(), getSocioInfoEnabled(),getSuccessElement());

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
        logout();
    }

    public void editExistingPatient() throws Exception {

        PatientRegistration registration = new PatientRegistration(driver);

        String givenName = "Billy " + new Random().nextInt(1000);  // append a random number so patient name is (more or less) unique
        String familyName = "Thorton";
        String nickname = "Bob";

        login();
        header.home();
        appDashboard.openPatientRegistrationApp();

        registration.editExistingPatient(adultTestPatient, givenName, familyName, nickname, PatientRegistration.Gender.FEMALE, 10, 10, 1950, getMothersFirstNameForEdit(),
                getPersonAddressString(), getContact(), getRelationship(), getContactPhoneNumber(), placeOfBirthAndContactAddressUseHierarchy(), getContactInfo());

        appDashboard.goToAppDashboard();
        appDashboard.findPatientByGivenAndFamilyName(givenName, familyName);
        assertTrue(new ClinicianDashboard(driver).isOpenForPatient(givenName, familyName));

        populateTestPatientForTearDown();
        logout();
    }

    protected String getMothersFirstName() { return "Mary"; }

    protected String getMothersFirstNameForEdit() {
        return "Cathy";
    }

    protected String getPersonAddressString() {
        return "";
    }

    protected String getPlaceOfBirthString() { return ""; }

    protected List<Map.Entry<String, String>> getContactInfo() {
        return Arrays.asList(new AbstractMap.SimpleEntry("phoneNumber", "12341111"));
    }

    protected Integer getMaritalStatus() {
        return 1;
    }

    protected String getContact() {
        return "dan";
    }

    protected String getRelationship() {
        return "cousin";
    }

    protected String getContactAddressString() {
        return "";
    }

    protected String getContactPhoneNumber() {
        return "4312533";
    }

    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

    protected Integer getEducation() { return null; }

    protected String getOccupation() { return null; }

    protected Integer getReligion() { return null; }

    protected Integer getInsuranceName() { return null; }

    protected String getOtherInsuranceNameString() {
        return null;
    }

    protected String getInsuranceNumberString() {
        return "";
    }

    protected Boolean automaticallyEnterIdentifier() { return true; }

    protected Boolean getRelationshipsEnabled() { return false; }

    protected Integer getPrintIdCardOption() { return null; }

    protected Boolean getBiometricsEnabled() { return false; }

    protected Boolean getAdditionalIdentifiersEnabled() { return false; }

    protected Boolean getSocioInfoEnabled() { return false; }

    protected List<String> getIdUuids() {
        return new ArrayList<>();
    }

    protected By getSuccessElement() {
        return By.id("register-patient-button");
    }

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = ((Long) ((JavascriptExecutor) driver).executeScript("return patient.id")).toString();
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
