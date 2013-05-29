package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.Toast;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConsultationTest extends BasicMirebalaisSmokeTest {

	private HeaderPage headerPage;
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		headerPage = new HeaderPage(driver);
	}
	
	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {
		loginPage.logInAsAdmin();
		createPatient();

		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();
		 
		Wait<WebDriver> wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.cssSelector("div.status-container")).isDisplayed();
			}
		});
		assertTrue(patientDashboard.hasActiveVisit());
		
		patientDashboard.addConsultNoteWithDischarge();		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CONSULTATION), is(1));
		Toast.closeToast(driver);
		headerPage.logOut();
	}
	
}
