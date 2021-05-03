package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PeruLoginPage;

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
        return "+513216547";
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
