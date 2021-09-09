package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

public class PatientRegistrationMirebalaisFlowTest extends PatientRegistrationHaitiFlowTest {

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
    protected Integer getPrintIdCardOption() { return 1; }

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
    protected Boolean getBiometricsEnabled() { return true; }

}
