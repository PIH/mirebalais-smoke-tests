package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MexicoLoginPage;
import org.openqa.selenium.By;

public class PatientRegistrationMexicoFlowTest extends PatientRegistrationFlowTest {

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
        return new MexicoLoginPage(driver);
    }

    @Override
    protected String getPersonAddressString() {
        return "Chihuilón";
    }

    @Override
    protected String getPlaceOfBirthString() { return ""; }

    @Override
    protected String getContactAddressString() {
        return "Chihuilón";
    }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return false; }

    @Override
    protected Integer getInsuranceName() {
        return 2;
    }

    @Override
    protected String getInsuranceNumberString() {
        return "43986512";
    }

    @Override
    protected String getOtherInsuranceNameString() {
        return "987321212";
    }

    @Override
    protected String getNationalId() {
        return "MAGH441128MOCTDL02";
    }

    @Override
    protected Boolean getSocioInfoEnabled() {
        return true;
    }

    @Override
    protected Integer getMaritalStatus() {
        return 2;
    }


    @Override
    protected Integer getOccupation() {
        return 3;
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
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }

    @Override
    protected Boolean getBiometricsEnabled() {
        return false;
    }

    @Override
    protected String getRelationship() {
        return "cousin";
    }

    @Override
    protected Boolean getRelationshipsEnabled() {
        return true;
    }

    @Override
    protected String getContactPhoneNumber() {
        return "983642321";
    }

    @Override
    protected By getSuccessElement() {
        return By.className("patient-header");
    }
}
