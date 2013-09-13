package org.openmrs.module.mirebalais.smoke;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.EditEncounterForm;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EditAdmissionTransferAndDischargeTest extends DbTest {
	
	private final String anemia = "D64.9";
	
	private final String malaria = "B54";
	
	private final String rubella = "B06.9";
	
	@BeforeClass
	public static void prepare() throws Exception {
		new LoginPage(driver).logInAsAdmin();
	}
	
	@Test
	public void shouldChangeProviderAndLocationForAdmission() throws Exception {
		startPatientVisit();
		
		patientDashboard.addRetroConsultNoteWithAdmissionToLocation(malaria, 3);
		
		String previousProvider = patientDashboard.providerForFirstEncounter();
		String previousLocation = patientDashboard.locationForFirstEncounter();
		
		changeProviderAndLocationAndAssertThatTheyAreDifferentFrom(previousProvider, previousLocation);
		
	}
	
	@Test
	public void shouldChangeProviderAndLocationForTransferWithinHospital() throws Exception {
		startPatientVisit();
		
		patientDashboard.addRetroConsultNoteWithAdmissionToLocation(malaria, 3);
		patientDashboard.addRetroConsultNoteWithTransferToLocation(rubella, 3);
		
		String previousProvider = patientDashboard.providerForFirstEncounter();
		String previousLocation = patientDashboard.locationForFirstEncounter();
		
		changeProviderAndLocationAndAssertThatTheyAreDifferentFrom(previousProvider, previousLocation);
		
	}
	
	@Test
	public void shouldChangeProviderAndLocationForDischargeHospital() throws Exception {
		startPatientVisit();
		
		patientDashboard.addRetroConsultNoteWithAdmissionToLocation(malaria, 3);
		patientDashboard.addRetroConsultNoteWithDischarge(anemia);
		
		String previousProvider = patientDashboard.providerForFirstEncounter();
		String previousLocation = patientDashboard.locationForFirstEncounter();
		
		changeProviderAndLocationAndAssertThatTheyAreDifferentFrom(previousProvider, previousLocation);
		
	}
	
	private void changeProviderAndLocationAndAssertThatTheyAreDifferentFrom(String previousProvider, String previousLocation) {
		EditEncounterForm.SelectedProviderAndLocation selected = patientDashboard
		        .editFirstEncounter(3, 4);
		
		String currentProvider = selected.provider();
		String currentLocation = selected.location();
		
		assertThat(currentProvider, not(previousProvider));
		assertThat(currentLocation, not(previousLocation));
	}
	
	private void startPatientVisit() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
	}
}
