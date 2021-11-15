package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PeruLoginPage;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientRegistrationPeruTest extends PatientRegistrationTest {

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
