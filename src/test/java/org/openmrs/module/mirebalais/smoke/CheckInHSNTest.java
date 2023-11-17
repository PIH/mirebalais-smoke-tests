package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.HSNLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;

public class CheckInHSNTest extends CheckInTest {

    @Override
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
