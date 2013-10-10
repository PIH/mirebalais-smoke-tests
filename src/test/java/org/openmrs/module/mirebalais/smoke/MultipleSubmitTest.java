package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultipleSubmitTest extends DbTest {


    @Test
    public void testMultipleEnterClicksOnCheckInForm() throws Exception {
        logInAsAdmin();
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();

        appDashboard = new AppDashboard(driver);
        appDashboard.openCheckinApp();

        PatientDashboard patientDashboard = new PatientDashboard(driver);

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInWithMultipleEnterKeystrokesOnSubmit(testPatient.getIdentifier());

        appDashboard.goToPatientPage(testPatient.getId());

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CHECKIN), is(1));
    }

}
