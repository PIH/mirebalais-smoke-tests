package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class VisitNoteMirebalaisTest extends VisitNoteTest {

    // override to use the Mirebalais vitals app
    @Override
    protected String getVitalsAppIdentifier() {
        return replaceChars(CustomAppLoaderConstants.Apps.UHM_VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
    }

}
