package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;
import org.openqa.selenium.By;

public class CaptureVitalsTest extends BasicMirebalaisSmokeTest {

	private CheckIn checkIn;
	private static LoginPage loginPage;
	private Registration registration;
	private PatientRegistrationDashboard patientDashboard;
	private AppDashboard appDashboard;
	private String patientName;
	private String patientIdentifier;
	private VitalsApp vitals;
	
	@Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientRegistrationDashboard(driver);
		checkIn = new CheckIn(driver);
		appDashboard = new AppDashboard(driver);
		vitals = new VitalsApp(driver);
	}

	
	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() {
		loginPage.logInAsAdmin();
		
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); // TODO: transform it in a sql script
		patientIdentifier = patientDashboard.getIdentifier();
		patientName = patientDashboard.getName();

		appDashboard.openActiveVisitsApp();
		assertFalse(driver.findElement(By.id("content")).getText().contains(patientIdentifier));

        appDashboard.openStartClinicVisitApp();
		checkIn.checkInPatient(patientIdentifier, patientName);
		
		appDashboard.openActiveVisitsApp();
		assertTrue(driver.findElement(By.id("content")).getText().contains(patientName));
		assertTrue(driver.findElement(By.id("content")).getText().contains(patientIdentifier));
		
		appDashboard.openCaptureVitalsApp();
		vitals.enterPatientIdentifier(patientIdentifier);
		vitals.confirmPatient();
		vitals.enterVitals();
		
		assertTrue(driver.findElement(By.id("content")).isDisplayed());
	}

}
