package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MirebalaisLoginPage;

public class CheckInMirebalaisTest extends CheckInTest {

    @Override
    protected LoginPage getLoginPage() {
        return new MirebalaisLoginPage(driver);
    }

    @Override
    protected Boolean getPaperRecordEnabled() { return true; }
}
