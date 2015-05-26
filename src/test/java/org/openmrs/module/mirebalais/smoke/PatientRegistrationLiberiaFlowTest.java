package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

public class PatientRegistrationLiberiaFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
    }

    @Override
    protected String getPersonAddressSearchString() {
        return "Zeno";
    }

    @Override
    protected String getContactAddressSearchString() {
        return "Zeno";
    }

}
