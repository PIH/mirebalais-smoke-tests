package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

public class DossieNumberGenerationTest extends BasicMirebalaisSmokeTest {

    private LoginPage loginPage;
    private IdentificationSteps identificationSteps;
    private Registration registration;
    private PatientDashboard patientDashboard;
    
    public void specificSetUp() {
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
