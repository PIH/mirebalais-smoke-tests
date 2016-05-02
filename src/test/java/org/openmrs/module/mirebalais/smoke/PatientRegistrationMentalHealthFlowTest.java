package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MentalHealthLoginPage;

public class PatientRegistrationMentalHealthFlowTest extends PatientRegistrationHaitiFlowTest {

    @Override
    protected LoginPage getLoginPage() {
        return new MentalHealthLoginPage(driver);
    }

    @Override
    protected Boolean automaticallyEnterIdentifier() { return null; }

}
