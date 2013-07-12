package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConsultationTest extends DbTest {
	
	private WebDriverWait wait10seconds = new WebDriverWait(driver, 10);
	
	@BeforeClass
	public static void prepare() {
		new LoginPage(driver).logInAsAdmin();
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		wait10seconds.until(visibilityOfElementLocated(By.cssSelector("div.status-container")));
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		patientDashboard.addConsultNoteWithDischarge();
		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
	}
	
	@Test
	public void addConsultationNoteWithDeathAsDispositionClosesVisit() throws Exception {
		patientDashboard.addConsultNoteWithDeath();
		
		assertThat(patientDashboard.isDead(), is(true));
		assertThat(patientDashboard.hasActiveVisit(), is(false));
		assertThat(patientDashboard.startVisitButtonIsVisible(), is(false));
	}
	
	@Test
	public void addEDNote() throws Exception {
		patientDashboard.addEmergencyDepartmentNote();
		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
	}
}
