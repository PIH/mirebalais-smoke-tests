package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.PeruLoginPage;

public class PatientRegistrationPeruFlowTest extends PatientRegistrationTest {

    @Test
    @Override
    public void goToRegistration() throws Exception {
        super.goToRegistration();
    }

    @Override
    protected LoginPage getLoginPage() {
        return new PeruLoginPage(driver);
    }

}
