package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MexicoLoginPage;

public class PatientRegistrationMexicoTest extends PatientRegistrationTest {

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
