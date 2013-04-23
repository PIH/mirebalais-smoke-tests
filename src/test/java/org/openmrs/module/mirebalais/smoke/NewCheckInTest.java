package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.NewCheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

public class NewCheckInTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private AppDashboard appDashboard;
	private Registration registration;
	private PatientRegistrationDashboard patientRegistrationDashboard; 
	private NewCheckIn newCheckIn;
	
	private String patientIdentifier;
    
	@Before
	public void setUp() {
        loginPage = new LoginPage(driver);
        registration = new Registration(driver);
        patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
        appDashboard = new AppDashboard(driver);
        newCheckIn = new NewCheckIn(driver);
	}
    
	@Test
	public void startAClinicVisit() {
        loginPage.logInAsAdmin();
        appDashboard.openPatientRegistrationApp();
        registration.goThruRegistrationProcessWithoutPrintingCard();
        patientIdentifier = patientRegistrationDashboard.getIdentifier();
        
        appDashboard.startClinicVisit();
        newCheckIn.checkInPatient(patientIdentifier);
        
        assertThat(newCheckIn.isPatientSearchDisplayed(), is(true));
	}

}
