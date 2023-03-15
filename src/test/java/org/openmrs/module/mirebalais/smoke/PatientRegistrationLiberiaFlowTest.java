package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LiberiaLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientRegistrationLiberiaFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
    }

    @Test
    @Override
    public void editExistingPatient() throws Exception {
        super.editExistingPatient();
    }

    @Override
    protected LoginPage getLoginPage() { return new LiberiaLoginPage(driver); }

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
    protected Integer getPrintIdCardOption() { return 1; }

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
    protected String getOccupation() { return "Driver"; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean getPatientSupportEnabled() {
        return true;
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected List<Map.Entry<String, String>> getContactInfo() {
        return Arrays.asList(new AbstractMap.SimpleEntry("phoneNumber", "+231881112233"));
    }

    @Override
    protected String getContactPhoneNumber() {
        return "+231881112233";
    }

    protected By getSuccessElement() {
        return By.id("checkin");  // Liberia redirects to the check-in page
    }

}
