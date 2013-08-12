package org.openmrs.module.mirebalais.smoke;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EditAdmissionTransferAndDischargeTest extends DbTest {
	
	private final String anemia = "D64.9";
	
	private final String malaria = "B54";
	
	private final String rubella = "B06.9";
	
	@BeforeClass
	public static void prepare() throws Exception {
		UserDatabaseHandler.insertNewClinicalUser();
		new LoginPage(driver).logInAsClinicalUser();
	}
	
	@Test
	public void shouldChangeProviderAndLocationForAdmission() throws Exception {
		startPatientVisit();
		
		patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 3);
		String provider = patientDashboard.providerForFirstEncounter();
		String location = patientDashboard.locationForFirstEncounter();
		
		PatientDashboard.ProviderAndLocation providerAndLocation = patientDashboard.editFirstEncounter(3, 4);
		
		assertThat(providerAndLocation.getProvider(), not(provider));
		assertThat(providerAndLocation.getLocation(), not(location));
		
	}
	
	@Test
	public void shouldChangeProviderAndLocationForTransferWithinHospital() throws Exception {
		startPatientVisit();
		
		patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 3);
		patientDashboard.addConsultNoteWithTransferToLocation(rubella, 3);
		
		String provider = patientDashboard.providerForFirstEncounter();
		String location = patientDashboard.locationForFirstEncounter();
		
		PatientDashboard.ProviderAndLocation providerAndLocation = patientDashboard.editFirstEncounter(3, 4);
		
		assertThat(providerAndLocation.getProvider(), not(provider));
		assertThat(providerAndLocation.getLocation(), not(location));
		
	}
	
	@Test
	public void shouldChangeProviderAndLocationForDischargeHospital() throws Exception {
		startPatientVisit();
		
		patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 3);
		patientDashboard.addConsultNoteWithDischarge(anemia);
		
		String provider = patientDashboard.providerForFirstEncounter();
		String location = patientDashboard.locationForFirstEncounter();
		
		PatientDashboard.ProviderAndLocation providerAndLocation = patientDashboard.editFirstEncounter(3, 4);
		
		assertThat(providerAndLocation.getProvider(), not(provider));
		assertThat(providerAndLocation.getLocation(), not(location));
		
	}
	
	private void startPatientVisit() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
	}
}
