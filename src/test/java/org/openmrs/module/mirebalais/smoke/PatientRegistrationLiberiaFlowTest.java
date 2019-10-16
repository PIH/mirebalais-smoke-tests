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
    protected String getContact() {
        return null;
    }

    @Override
    protected Boolean placeOfBirthAndContactAddressUseHierarchy() { return false; }

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
    protected Integer getOccupation() { return null; }

    @Override
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Boolean getBiometricsEnabled() {
        return true;
    }

    @Override
    protected String getContactPhoneNumber() {
        return "+231881112233";
    }

    protected By getSuccessElement() {
        return By.id("checkin");  // Liberia redirects to the check-in page
    }

}
