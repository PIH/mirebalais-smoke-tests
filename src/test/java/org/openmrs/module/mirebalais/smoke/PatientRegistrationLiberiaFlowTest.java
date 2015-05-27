package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

public class PatientRegistrationLiberiaFlowTest extends PatientRegistrationFlowTest {

    @Test
    @Override
    public void registerNewPatient() throws Exception {
        super.registerNewPatient();
    }

    @Override
    protected String getPersonAddressString() {
        return "Zeno";
    }

    @Override
    protected String getContactAddressString() {
        return "Zeno";
    }

    protected Boolean contactAddressUsesHierarchy() { return false; }

    @Override
    protected Integer getReligion() { return null; }

}
