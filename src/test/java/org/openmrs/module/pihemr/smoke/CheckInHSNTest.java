package org.openmrs.module.pihemr.smoke;

import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.HSNLoginPage;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.LoginPage;

public class CheckInHSNTest extends CheckInTest {

    @Override
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
