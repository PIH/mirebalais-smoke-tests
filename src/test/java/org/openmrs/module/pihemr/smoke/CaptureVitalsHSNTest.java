package org.openmrs.module.pihemr.smoke;

import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.HSNLoginPage;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.LoginPage;

public class CaptureVitalsHSNTest extends CaptureVitalsTest {

    @Override
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
