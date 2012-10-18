package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.Assert.assertEquals;

public class DossieNumberGenerationTest extends BasicMirebalaisSmokeTest {

	private LoginPage loginPage;
	private IdentificationSteps identificationSteps;
	private Registration registration;
	private PatientDashboard patientDashboard;

    @Override
    protected void specificSetUp() {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");

		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver, wait);
		registration = new Registration(driver, wait);
		patientDashboard = new PatientDashboard(driver);
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
