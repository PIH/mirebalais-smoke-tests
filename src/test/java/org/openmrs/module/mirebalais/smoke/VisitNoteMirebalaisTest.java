package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.pihcore.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.MirebalaisLoginPage;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class VisitNoteMirebalaisTest extends VisitNoteTest {

    // TODO this will need to be tweaked further before it works--the Mirebalais app currently doesn't have waiting for consult, etc...

    // override to use the Mirebalais vitals app
    @Override
    protected String getVitalsAppIdentifier() {
        return replaceChars(CustomAppLoaderConstants.Apps.UHM_VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
    }

    @Override
    protected LoginPage getLoginPage() { return new MirebalaisLoginPage(driver); }

}
