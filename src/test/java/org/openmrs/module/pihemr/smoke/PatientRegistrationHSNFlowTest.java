package org.openmrs.module.pihemr.smoke;

import org.junit.Test;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.HSNLoginPage;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.LoginPage;

public class PatientRegistrationHSNFlowTest extends PatientRegistrationHaitiFlowTest {

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
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
