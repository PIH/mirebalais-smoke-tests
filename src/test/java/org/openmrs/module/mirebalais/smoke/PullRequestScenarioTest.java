package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PullRequestScenarioTest extends BasicMirebalaisSmokeTest {

	private CheckIn checkIn;
	private String patientName;
	
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		checkIn = new CheckIn(driver);
	}

	@BeforeClass
    public static void setUpEnvironment() {
    	loginPage = new LoginPage(driver);
    	loginPage.logInAsAdmin();
    }

	@Test
	public void createsARecord() throws InterruptedException {
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		patientIdentifier = patientRegistrationDashboard.getIdentifier();
		patientName = patientRegistrationDashboard.getName();

        appDashboard.openStartClinicVisitApp();
		checkIn.checkInPatient(patientIdentifier, patientName);
		appDashboard.openArchivesRoomApp();

        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.textToBePresentInElement(By.id("create_requests_table"), patientName));
        wait.until(ExpectedConditions.textToBePresentInElement(By.id("create_requests_table"), patientIdentifier));
	}

}