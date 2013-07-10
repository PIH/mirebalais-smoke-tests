package org.openmrs.module.mirebalais.smoke;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasicMirebalaisSmokeTest {

    protected static LoginPage loginPage;
    protected AppDashboard appDashboard;
    protected Registration registration;
    protected PatientRegistrationDashboard patientRegistrationDashboard;
    protected PatientDashboard patientDashboard;
    protected Patient testPatient;
	
	protected static WebDriver driver;

    @BeforeClass
    public static void startWebDriver() {
        driver = new SmokeTestDriver().getDriver();
    }

	@AfterClass
    public static void stopWebDriver() {
        driver.quit();
    }
	
	protected void initBasicPageObjects() {
		loginPage = new LoginPage(driver);
		registration = new Registration(driver);
		patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
		patientDashboard = new PatientDashboard(driver);
		appDashboard = new AppDashboard(driver);
	}
	
	protected void createPatient() {
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		testPatient = new Patient(patientRegistrationDashboard.getIdentifier(), patientRegistrationDashboard.getName(), null, null, null, null, null);
	}
	
	protected void startVisit() throws Exception {
		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.status-container")));
    }
	

}