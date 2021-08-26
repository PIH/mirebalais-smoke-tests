package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PeruLoginPage;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientRegistrationPeruFlowTest extends PatientRegistrationFlowTest {

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
    protected LoginPage getLoginPage() {
        return new PeruLoginPage(driver);
    }

    @Override
    protected List<String> getIdUuids() {
        return Arrays.asList("ffbd60fb-599a-4a57-b2f1-4b55847cd938", "3fa0990e-900d-4a80-a4c1-dcf01a0d4f9c");
    }

    @Override
    protected String getPersonAddressString() {
        return "Yucay";
    }

    @Override
    protected String getMothersFirstName() {
        return null;
    }

    @Override
    protected String getMothersFirstNameForEdit() {
        return null;
    }

    @Override
    protected String getPlaceOfBirthString() { return null; }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return false; }

    @Override
    protected Integer getPrintIdCardOption() { return null; }

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
        return 1;
    }

    @Override
    protected Integer getEducation() {
        return 3;
    }

    @Override
    protected String getOccupation() { return "Chofeur"; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return false; }

    @Override
    protected List<Map.Entry<String, String>> getContactInfo() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry("phoneNumber", "+513216547"),
                new AbstractMap.SimpleEntry("personAttributeTypef0d3a22f-95b5-4054-9c04-31d5abc2edb4", "+513216547"),
                new AbstractMap.SimpleEntry("personAttributeType404a5a8f-ac7b-448d-bf6a-e78bb6e1b840", "abc@example.com")
        );
    }

    @Override
    protected String getContact() {
        return null;
    }

    @Override
    protected String getContactPhoneNumber() {
        return null;
    }

    @Override
    protected Boolean getAdditionalIdentifiersEnabled() {
        return false;
    }
}
