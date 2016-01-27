package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckInFormPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CaptureVitalsTest extends DbTest {

	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);

		login();

        appDashboard.startClinicVisit();
        newCheckIn.enterInfoFillingTheFormTwice(testPatient.getIdentifier());
		
		appDashboard.openCaptureVitalsApp();
		vitals.captureVitalsForPatient(testPatient.getIdentifier());
		assertThat(vitals.isSearchPatientDisplayed(), is(true));
		
		appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(testPatient.getId());
		assertThat(visitNote.countEncountersOfType(VisitNote.VITALS_CREOLE_NAME), is(1));
	}
	
}
