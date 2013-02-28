package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.*;

public class ConsultationTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private Registration registration;
	private PatientRegistrationDashboard patientDashboard;
	private AppDashboard appDashboard;
	private String patientIdentifier;
	
	
	@Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientRegistrationDashboard(driver);
		appDashboard = new AppDashboard(driver);
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() {
		loginPage.logInAsAdmin();
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientDashboard.getIdentifier();

		appDashboard.findPatientById(patientIdentifier);
		patientDashboard.startVisit();
		
		 
		Wait<WebDriver> wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.cssSelector("div.visit-status")).isDisplayed();
			}
		});
		assertTrue(patientDashboard.hasActiveVisit());
		
		
		patientDashboard.addConsultationNote();
		assertThat(patientDashboard.countEncouters(PatientRegistrationDashboard.CONSULTATION), is(1));
	
	}
}
