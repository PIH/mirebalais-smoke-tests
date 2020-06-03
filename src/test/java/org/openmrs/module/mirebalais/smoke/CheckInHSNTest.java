package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.HSNLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

public class CheckInHSNTest extends CheckInTest {

    @Override
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
