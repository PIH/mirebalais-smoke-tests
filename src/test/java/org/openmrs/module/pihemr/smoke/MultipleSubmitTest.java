package org.openmrs.module.pihemr.smoke;

import org.junit.Test;
import org.openmrs.module.pihemr.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.pihemr.smoke.pageobjects.AppDashboard;
import org.openmrs.module.pihemr.smoke.pageobjects.VisitNote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultipleSubmitTest extends DbTest {


    @Test
    public void testMultipleEnterClicksOnCheckInForm() throws Exception {

        logInAsAdmin();
        appDashboard = new AppDashboard(driver);
        appDashboard.openCheckinApp();

        VisitNote patientDashboard = new VisitNote(driver);

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInWithMultipleEnterKeystrokesOnSubmit(adultTestPatient.getIdentifier());

        appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(adultTestPatient.getId());

        assertThat(patientDashboard.countEncountersOfType(VisitNote.CHECKIN_CREOLE_NAME), is(1));
    }

}
