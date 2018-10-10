package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

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
    protected Integer getReligion() { return null; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }

    @Override
    protected Boolean getBiometricsEnabled() {
        return false;
    }

}
