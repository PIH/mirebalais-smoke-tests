package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;

public class CaptureVitalsTest extends DbTest {
	
	private VitalsApp vitals;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		initBasicPageObjects();
		vitals = new VitalsApp(driver);
	}
	
	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
		loginPage.logInAsAdmin();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		appDashboard.openCaptureVitalsApp();
		vitals.captureVitalsForPatient(testPatient.getIdentifier());
		assertThat(vitals.isSearchPatientDisplayed(), is(true));
		
		appDashboard.goToPatientPage(testPatient.getId());
		assertThat(patientDashboard.countEncouters(PatientDashboard.VITALS), is(1));
	}
	
}
