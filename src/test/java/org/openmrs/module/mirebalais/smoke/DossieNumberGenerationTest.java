package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@Ignore
public class DossieNumberGenerationTest {

	private static WebDriver driver;
	private static Wait<WebDriver> wait;

	private LoginPage loginPage;
	private IdentificationSteps identificationSteps;
	private Registration registration;
	private PatientDashboard patientDashboard;

	@Before
	public void setUp() {
		driver = new FirefoxDriver();

		wait = new WebDriverWait(driver, 30);
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");

		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		registration = new Registration(driver, wait);
		patientDashboard = new PatientDashboard(driver);
	}

	@After
    public void tearDown() {
    	driver.close();
    }
	
	@Test
	public void createsTwoSequentialAndUniqueDossieNumbers() {
		loginPage.logIn("admin", "Admin123");
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		Integer dossieNumberOne = getDossieNumberWithoutLetters(patientDashboard.generateDossieNumber());
		
		registration.goThruRegistrationProcessWithoutPrintingCard();
		Integer dossieNumberTwo = getDossieNumberWithoutLetters(patientDashboard.generateDossieNumber());

		assertEquals(++dossieNumberOne, dossieNumberTwo);
	}
	
	private Integer getDossieNumberWithoutLetters(String dossieNumber) {
		return Integer.parseInt(dossieNumber.substring(1,dossieNumber.length()));
	}
	

}
