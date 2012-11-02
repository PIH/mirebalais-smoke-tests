package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
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
	private AppDashboard appDashboard;
	
	private String patientName;
	private String patientIdentifier;
	
	
	@Override
    public void specificSetUp() {
		identificationSteps = new IdentificationSteps(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientDashboard(driver);
		checkIn = new CheckIn(driver);
		appDashboard = new AppDashboard(driver);
	}

	@BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
    	loginPage.logIn("admin", "Admin123");
    }
	
	@Test
	public void pullsADossier() {
		appDashboard.openPatientRegistrationApp();
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		patientIdentifier = patientDashboard.getIdentifier();
		patientName = patientDashboard.getName();
		patientDashboard.generateDossieNumber();
		checkIn.setLocationAndChooseCheckInTask(patientIdentifier, patientName);
		appDashboard.openArchivesRoomApp();
		
		assertTrue(driver.findElement(By.className("dataTable")).getText().contains(patientName));
	}
	
	@Test
	public void createsARecord() {
		appDashboard.openPatientRegistrationApp();
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		patientIdentifier = patientDashboard.getIdentifier();
		patientName = patientDashboard.getName();
		checkIn.setLocationAndChooseCheckInTask(patientIdentifier, patientName);
		appDashboard.openArchivesRoomApp();
		
		// TODO: extract it!
		driver.findElement(By.linkText("Create Record Requests")).click();
		
		assertTrue(driver.findElement(By.id("create_requests_table")).getText().contains(patientName));
		assertTrue(driver.findElement(By.id("create_requests_table")).getText().contains(patientIdentifier));
	}
}
