package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.SierraLeoneLoginPage;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientRegistrationSierraLeoneFlowTest extends PatientRegistrationFlowTest{

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
    protected LoginPage getLoginPage() { return new SierraLeoneLoginPage(driver); }

    @Override
    protected String getPersonAddressString() {
        return "Kongbora";
    }

    @Override
    protected String getPlaceOfBirthString() { return "Neverland"; }

    @Override
    protected String getContact() {
        return "George";
    }

    @Override
    protected String getRelationship() {
        return "cousin";
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
        return 1;
    }

    @Override
    protected String getOccupation() { return "Driver"; }

    @Override
    protected Integer getReligion() { return null; }

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

    @Override
    protected Integer getAdditionalIdentifiersCount() {
        return 2;
    }

    @Override
    protected String getLocalContact() {
        return "John Smith";
    }

    @Override
    protected String getLocalContactAddressString() {
        return "Kongbora";
    }

    @Override
    protected String getLocalContactPhoneNumber() {
        return "+231881112235";
    }

    @Override
    protected Boolean getRelationshipsEnabled() { return true; }
}
