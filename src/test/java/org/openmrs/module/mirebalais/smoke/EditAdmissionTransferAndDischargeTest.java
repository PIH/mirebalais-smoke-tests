package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class EditAdmissionTransferAndDischargeTest extends DbTest {
	
	public static final String MALARIA = "B54";
	
	@BeforeClass
	public static void prepare() {
		new LoginPage(driver).logInAsDoctor();
	}
	
	@Test
	public void changeProviderAndLocationAdmission() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		patientDashboard.addConsultNoteWithAdmissionToLocation(MALARIA, 3);
		String provider = patientDashboard.providerForFirstEncounter();
		String location = patientDashboard.locationForFirstEncounter();
		
		PatientDashboard.ProviderAndLocation providerAndLocation = patientDashboard.editFirstEncounter(3, 4);
		
		assertThat(providerAndLocation.getProvider(), not(provider));
		assertThat(providerAndLocation.getLocation(), not(location));
		
	}
}
