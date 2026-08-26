package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.ZlCentralLoginPage;

public class PatientRegistrationZlCentralFlowTest extends PatientRegistrationHaitiFlowTest {

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
    protected Integer getAdditionalIdentifiersCount() { return 0; }

    @Override
    protected LoginPage getLoginPage() {
        return new ZlCentralLoginPage(driver);
    }

}
