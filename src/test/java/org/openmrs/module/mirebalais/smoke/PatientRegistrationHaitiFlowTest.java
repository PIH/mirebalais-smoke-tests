package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

public class PatientRegistrationHaitiFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
    }

    @Override
    protected String getPersonAddressSearchString() {
        return "Cange";
    }

    @Override
    protected String getContactAddressSearchString() {
        return "Hinche";
    }


    @Override
    protected Integer getReligion() { return 1; }
}
