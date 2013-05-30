package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;
import org.openqa.selenium.By;

public class CaptureVitalsTest extends BasicMirebalaisSmokeTest {

	private CheckIn checkIn;
	private VitalsApp vitals;
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		checkIn = new CheckIn(driver);
		vitals = new VitalsApp(driver);
	}
	
	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
		loginPage.logInAsAdmin();
		createPatient();

		appDashboard.openActiveVisitsApp();
		assertFalse(driver.findElement(By.id("content")).getText().contains(testPatient.getIdentifier()));

        appDashboard.openStartClinicVisitApp();
		checkIn.checkInPatient(testPatient);
		
		appDashboard.openActiveVisitsApp();
		String contentText = driver.findElement(By.id("content")).getText();
		assertThat(contentText.contains(testPatient.getName()), is(true));
		assertThat(contentText.contains(testPatient.getIdentifier()), is(true));
		
		appDashboard.openCaptureVitalsApp();
		vitals.captureVitalsForPatient(testPatient.getIdentifier());
        assertThat(vitals.isSearchPatientDisplayed(), is(true));
        
        appDashboard.findPatientById(testPatient.getIdentifier());
        assertThat(patientDashboard.countEncouters(PatientDashboard.VITALS), is(1));
    }

}
