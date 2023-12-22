package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.HaitiMultiLocationLoginPage;

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
    protected Boolean getBiometricsEnabled() { return true; }

    @Override
    protected LoginPage getLoginPage() {
        return new HaitiMultiLocationLoginPage(driver);
    }

}
