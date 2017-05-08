package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openqa.selenium.By;

public class PatientRegistrationHaitiFlowTest extends PatientRegistrationFlowTest {

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
    protected String getPersonAddressString() {
        return "Cange";
    }

    @Override
    protected String getPlaceOfBirthString() { return "Cange"; }

    @Override
    protected String getContactAddressString() {
        return "Hinche";
    }

    @Override
    protected Integer getInsuranceName() {
        return 2;
    }

    @Override
    protected String getInsuranceNumberString() {
        return "076-098765";
    }

    @Override
    protected String getOtherInsuranceNameString() {
        return "non-coded insurance";
    }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

    @Override
    protected Integer getReligion() { return 1; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Boolean getRelationshipsEnabled() {
        return false;
    }

    @Override
    protected By getSuccessElement() {
        return By.id("register-patient-button");    // Haiti redirects to Register Patient search page
    }

}
