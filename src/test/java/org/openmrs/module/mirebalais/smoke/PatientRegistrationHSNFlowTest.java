package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HSNLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;

public class PatientRegistrationHSNFlowTest extends PatientRegistrationHaitiFlowTest {

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
        return new HSNLoginPage(driver);
    }

    @Override
    protected String getMothersFirstName() {
        return null; // not enabled on HSN
    }

    @Override
    protected String getMothersFirstNameForEdit() {
        return "Cathy";
    }

    @Override
    protected String getPersonAddressString() {
        return "Cange";
    }

    @Override
    protected String getPlaceOfBirthString() { return null; }


    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

    @Override
    protected String getOccupation() { return null; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected String getRelationship() {
        return null;
    }

    @Override
    protected String getContact() {
        return null;
    }

    @Override
    protected String getContactAddressString() {
        return null;
    }

    @Override
    protected String getContactPhoneNumber() {
        return null;
    }

    @Override
    protected Integer getMaritalStatus() {
        return null;
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Boolean getRelationshipsEnabled() {
        return false;
    }

    @Override
    protected Boolean getAdditionalIdentifiersEnabled() { return false; }

    @Override
    protected By getSuccessElement() {
        return By.id("register-patient-button");    // Haiti redirects to Register Patient search page
    }


}
