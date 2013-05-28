package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.InPatientList;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InPatientTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private Registration registration;
	private PatientRegistrationDashboard patientRegistrationDashboard;
	private PatientDashboard patientDashboard;
	private AppDashboard appDashboard;
	private String patientIdentifier;
	private HeaderPage headerPage;
	private InPatientList ipl;
	
	
	@Before
    public void setUp() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
		patientDashboard = new PatientDashboard(driver);
		appDashboard = new AppDashboard(driver);
		headerPage = new HeaderPage(driver);
		ipl  = new InPatientList(driver);
	}
	
	@Test
	public void admitPatientAndTrasnfer() throws Exception {
		loginPage.logInAsAdmin();
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();

		appDashboard.findPatientById(patientIdentifier);
		patientDashboard.startVisit();
		 
		Wait<WebDriver> wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.cssSelector("div.status-container")).isDisplayed();
			}
		});
		assertTrue(patientDashboard.hasActiveVisit());
		
		String admissionPlace = patientDashboard.addConsultNoteWithAdmission();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
		assertThat(patientDashboard.countEncouters(PatientDashboard.ADMISSION), is(1));
		
		assurePlaces(admissionPlace, admissionPlace);
		
		appDashboard.findPatientById(patientIdentifier);
		
		String transferPlace = patientDashboard.addConsultNoteWithTransfer();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(2));
		assertThat(patientDashboard.countEncouters(PatientDashboard.TRANSFER), is(1));
		
		assurePlaces(admissionPlace, transferPlace);
		
		headerPage.logOut();
	}
	
	private void assurePlaces(String firstAdmitted, String currentWard) throws Exception {
		appDashboard.openInPatientApp();
		
		assertThat(ipl.getCurrentWard(patientIdentifier), is(currentWard));
		assertThat(ipl.getFirstAdmitted(patientIdentifier), is(firstAdmitted));
	}

}
