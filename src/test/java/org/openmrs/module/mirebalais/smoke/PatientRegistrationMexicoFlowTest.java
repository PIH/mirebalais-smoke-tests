package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.MexicoLoginPage;

public class PatientRegistrationMexicoFlowTest extends PatientRegistrationTest {

    @Test
    @Override
    public void goToRegistration() throws Exception {
        super.goToRegistration();
    }

    @Override
    protected LoginPage getLoginPage() {
        return new MexicoLoginPage(driver);
    }

}
