package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

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
    protected String getContactAddressString() {
        return "Hinche";
    }

    protected Boolean contactAddressUsesHierarchy() { return true; }

    @Override
    protected Integer getReligion() { return 1; }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return true; }

    @Override
    protected Integer getPrintIdCardOption() { return 1; }

}
