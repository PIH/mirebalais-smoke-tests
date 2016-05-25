package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openqa.selenium.By;

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
    protected String getPersonAddressString() {
        return "Zeno";
    }

    @Override
    protected String getPlaceOfBirthString() { return "Zeno"; }

    @Override
    protected String getContactAddressString() {
        return "Zeno";
    }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return false; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }

    @Override
    protected Integer getPrintIdCardOption() { return 1; }

    protected By getSuccessElement() {
        return By.className("register-patient-button");  // Liberia redirects to registration home page
    }

}
