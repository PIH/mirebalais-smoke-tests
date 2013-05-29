package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CaptureVitalsTest extends BasicMirebalaisSmokeTest {

	private CheckIn checkIn;
	private String patientName;
	private VitalsApp vitals;
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		checkIn = new CheckIn(driver);
		vitals = new VitalsApp(driver);
	}
	
	@Test
	public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
		loginPage.logInAsAdmin();
		
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();
		patientName = patientRegistrationDashboard.getName();

		appDashboard.openActiveVisitsApp();
		assertFalse(driver.findElement(By.id("content")).getText().contains(patientIdentifier));

        appDashboard.openStartClinicVisitApp();
		checkIn.checkInPatient(patientIdentifier, patientName);
		
		appDashboard.openActiveVisitsApp();
		String contentText = driver.findElement(By.id("content")).getText();
		assertThat(contentText.contains(patientName), is(true));
		assertThat(contentText.contains(patientIdentifier), is(true));
		
		appDashboard.openCaptureVitalsApp();
		vitals.enterPatientIdentifier(patientIdentifier);
		vitals.confirmPatient();
		vitals.enterVitals();

        Wait<WebDriver> wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return driver.findElement(By.className("scan-input")).isDisplayed();
            }
        });

        appDashboard.findPatientById(patientIdentifier);
        assertThat(patientDashboard.countEncouters(PatientDashboard.VITALS), is(1));
    }

}
