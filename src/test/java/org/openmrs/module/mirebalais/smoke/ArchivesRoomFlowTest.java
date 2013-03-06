package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

public class ArchivesRoomFlowTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private Registration registration;
	private PatientDashboard patientDashboard;
	private PatientRegistrationDashboard patientRegistrationDashboard;
	private AppDashboard appDashboard;
	private String patientIdentifier;
	private ArchivesRoomApp archivesRoomApp;
	
	
	@Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientDashboard(driver);
		patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
		appDashboard = new AppDashboard(driver);
		archivesRoomApp = new ArchivesRoomApp(driver);
	}

	
	@Test
	public void checkInPatientDeleteEncounterAndKeepsActiveVisit() {
		loginPage.logInAsAdmin();
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();

		appDashboard.findPatientById(patientIdentifier);
		patientDashboard.requestRecord();
		 
		appDashboard.openArchivesRoomApp();
		assertTrue(archivesRoomApp.isNewRecordRequested(patientIdentifier));
	}
	
}
