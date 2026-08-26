package org.openmrs.module.pihemr.smoke;

import org.openmrs.module.pihcore.apploader.CustomAppLoaderConstants;
import org.openmrs.module.pihemr.smoke.pageobjects.AppDashboard;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.LoginPage;
import org.openmrs.module.pihemr.smoke.pageobjects.loginpages.ZlCentralLoginPage;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class VisitNoteZlCentralTest extends VisitNoteTest {

    // TODO this will need to be tweaked further before it works--the Mirebalais app currently doesn't have waiting for consult, etc...

    // override to use the Mirebalais vitals app
    @Override
    protected String getVitalsAppIdentifier() {
        return replaceChars(CustomAppLoaderConstants.Apps.UHM_VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
    }

    @Override
    protected LoginPage getLoginPage() { return new ZlCentralLoginPage(driver); }

}
