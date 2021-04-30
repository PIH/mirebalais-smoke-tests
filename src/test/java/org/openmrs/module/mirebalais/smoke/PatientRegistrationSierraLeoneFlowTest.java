package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.SierraLeoneLoginPage;

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
    protected String getPlaceOfBirthString() { return null; }

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
        return null;
    }

    @Override
    protected Integer getOccupation() { return 5; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return false; }

    @Override
    protected Boolean getBiometricsEnabled() {
        return true;
    }

    @Override
    protected String getPhoneNumber() {
        return "+231881112233";
    }

    @Override
    protected String getContactPhoneNumber() {
        return "+231881112233";
    }

    @Override
    protected Boolean getAdditionalIdentifiersEnabled() {
        return true;
    }
}
