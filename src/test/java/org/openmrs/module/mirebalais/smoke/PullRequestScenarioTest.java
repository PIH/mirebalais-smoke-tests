package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.ArchivesRoom;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;

public class PullRequestScenarioTest extends BasicMirebalaisSmokeTest {

	private CheckIn checkIn;
	private static LoginPage loginPage;
	private IdentificationSteps identificationSteps;
	private Registration registration;
	private PatientDashboard patientDashboard;
	private ArchivesRoom archivesRoom;

	
	private String patientName;
	private String patientIdentifier;
	
	
	@Override
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		registration = new Registration(driver, wait);
		patientDashboard = new PatientDashboard(driver, wait);
		checkIn = new CheckIn(driver, wait);
		archivesRoom = new ArchivesRoom(driver);
	}

	
	@Test
	public void pullsADossier() {
		loginPage.logIn("admin", "Admin123");
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		patientIdentifier = patientDashboard.getIdentifier();
		patientName = patientDashboard.getName();
		checkIn.setLocationAndChooseCheckInTask(patientIdentifier, patientName);
		archivesRoom.openArchivesRoomApp();
		
		assertTrue(driver.findElement(By.className("dataTable")).getText().contains(patientName));
	}
	
}
