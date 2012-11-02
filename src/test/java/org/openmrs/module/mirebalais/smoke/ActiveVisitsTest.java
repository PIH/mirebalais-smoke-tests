package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;

public class ActiveVisitsTest extends BasicMirebalaisSmokeTest{

	private CheckIn checkIn;
	private static LoginPage loginPage;
	private IdentificationSteps identificationSteps;
	private Registration registration;
	private PatientDashboard patientDashboard;
	private AppDashboard appDashboard;
	private String patientName;
	private String patientIdentifier;
	
	
	@Override
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientDashboard(driver);
		checkIn = new CheckIn(driver);
		appDashboard = new AppDashboard(driver);
	}

	
	@Test
	public void patientHasAnActiveVisiteWithoutPullingADossier() {
		loginPage.logIn("admin", "Admin123");
		appDashboard.openPatientRegistrationApp();
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard(); // TODO: transform it in a sql script
		patientIdentifier = patientDashboard.getIdentifier();
		patientName = patientDashboard.getName();
		patientDashboard.generateDossieNumber();
		checkIn.setLocationAndChooseCheckInTask(patientIdentifier, patientName);
		
		/* this should work when the pull request button works again
		appDashboard.openArchivesRoomApp();
		assertFalse(driver.findElement(By.className("dataTable")).getText().contains(patientName));
		*/
		
		appDashboard.openActiveVisitsApp();
		assertTrue(driver.findElement(By.id("content")).getText().contains(patientName));
		assertTrue(driver.findElement(By.id("content")).getText().contains(patientIdentifier));
	}

}
