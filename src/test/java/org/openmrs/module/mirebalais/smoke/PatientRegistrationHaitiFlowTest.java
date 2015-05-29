package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openqa.selenium.By;

public class PatientRegistrationHaitiFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
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
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return true; }

    @Override
    protected Integer getReligion() { return 1; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Integer getPrintIdCardOption() { return 1; }

    @Override
    protected By getSuccessElement() {
        return By.id("register-patient-button");    // Haiti redirects to Register Patient search page
    }

}
