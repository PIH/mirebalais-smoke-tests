package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;

public class CaptureVitalsTest extends DbTest {

	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);

		login();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		appDashboard.openCaptureVitalsApp();
		vitals.captureVitalsForPatient(testPatient.getIdentifier());
		assertThat(vitals.isSearchPatientDisplayed(), is(true));
		
		appDashboard.goToPatientPage(testPatient.getId());
		assertThat(patientDashboard.countEncountersOfType(PatientDashboard.VITALS), is(1));
	}
	
}
