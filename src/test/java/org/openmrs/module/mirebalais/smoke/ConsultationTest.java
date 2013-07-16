package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class ConsultationTest extends DbTest {
	
	@BeforeClass
	public static void prepare() {
		new LoginPage(driver).logInAsAdmin();
	}
	
	@Before
	public void setUp() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		patientDashboard.addConsultNoteWithDischarge();
		
		assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION), is(1));
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
		
		assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION), is(1));
	}
}
