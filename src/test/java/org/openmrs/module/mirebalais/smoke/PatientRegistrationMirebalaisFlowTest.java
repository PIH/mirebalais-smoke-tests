package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;

public class PatientRegistrationMirebalaisFlowTest extends PatientRegistrationHaitiFlowTest {

    @Override
    protected LoginPage getLoginPage() {
        return new MirebalaisLoginPage(driver);
    }

}
