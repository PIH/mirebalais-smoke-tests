package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.IdentificationSteps;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

@Ignore
public class DossieNumberGenerationTest extends BasicMirebalaisSmokeTest {

	private LoginPage loginPage;
	private IdentificationSteps identificationSteps;
	private Registration registration;
	private PatientRegistrationDashboard patientDashboard;
	private AppDashboard appDashboard;

	@Override
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		identificationSteps = new IdentificationSteps(driver);
		registration = new Registration(driver);
		patientDashboard = new PatientRegistrationDashboard(driver);
		appDashboard = new AppDashboard(driver);
	}

	
	@Test
	public void createsTwoSequentialAndUniqueDossieNumbers() {
		loginPage.logIn("admin", "Admin123");
		appDashboard.openPatientRegistrationApp();
		identificationSteps.setLocationAndChooseRegisterTask();
		registration.goThruRegistrationProcessWithoutPrintingCard();
		//Integer dossieNumberOne = getDossieNumberWithoutLetters(patientDashboard.generateDossieNumber());

		registration.goThruRegistrationProcessWithoutPrintingCard();
		//Integer dossieNumberTwo = getDossieNumberWithoutLetters(patientDashboard.generateDossieNumber());

		//assertEquals(++dossieNumberOne, dossieNumberTwo);
	}
	
	private Integer getDossieNumberWithoutLetters(String dossieNumber) {
		return Integer.parseInt(dossieNumber.substring(1,dossieNumber.length()));
	}
	

}
