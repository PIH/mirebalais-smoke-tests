package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.HSNLoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;

public class CaptureVitalsHSNTest extends CaptureVitalsTest {

    @Override
    protected LoginPage getLoginPage() {
        return new HSNLoginPage(driver);
    }

}
