package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.Toast;
import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;

public class ConsultationTest extends BasicMirebalaisSmokeTest {

	private HeaderPage headerPage;
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		headerPage = new HeaderPage(driver);
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		loginPage.logInAsAdmin();
		createPatient();

		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();
		
		Waiter.waitForElementToDisplay(By.cssSelector("div.status-container"), 5, driver);
		assertThat(patientDashboard.hasActiveVisit(), is(true));
		
		patientDashboard.addConsultNoteWithDischarge();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
		Toast.closeToast(driver);
		headerPage.logOut();
	}
	
	@Test
	public void addConsultationNoteWithDeathAsDispositionClosesVisit() throws Exception {
		loginPage.logInAsAdmin();
		createPatient();

		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();
		
		Waiter.waitForElementToDisplay(By.cssSelector("div.status-container"), 5, driver);
		assertThat(patientDashboard.hasActiveVisit(), is(true));
		
		patientDashboard.addConsultNoteWithDeath();		
		assertThat(patientDashboard.hasActiveVisit(), is(false));
		assertThat(patientDashboard.showStartVisitButton(), is(true));
		Toast.closeToast(driver);
		headerPage.logOut();
	}
	
}
