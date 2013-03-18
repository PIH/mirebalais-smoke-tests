package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoomApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;

public class ArchivesRoomFlowTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private Registration registration;
	private PatientDashboard patientDashboard;
	private PatientRegistrationDashboard patientRegistrationDashboard;
	private AppDashboard appDashboard;
	private String patientIdentifier;
	private String patientName;
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
	public void requestRecord() {
		loginPage.logInAsAdmin();
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();
		patientName = patientRegistrationDashboard.getName();

		appDashboard.findPatientById(patientIdentifier);
		patientDashboard.requestRecord();
		 
		appDashboard.openArchivesRoomApp();
		
		try {
			archivesRoomApp.findPatientInTheList(patientIdentifier, "create_requests_table").click();
			driver.findElement(By.id("assign-to-create-button")).click();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(archivesRoomApp.isPatientInList(patientName, "assigned_create_requests_table"));
	}
	
}
