package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class AdmissionTransferAndDischargeTest extends DbTest {
	
	private final String anemia = "D64.9";
	
	private final String malaria = "B54";
	
	private final String rubella = "B06.9";
	

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsClinicalUser() throws Exception {
        new LoginPage(driver).logInAsClinicalUser();

        startPatientVisit();

        patientDashboard.addAdmissionNote(malaria);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        patientDashboard.editExistingAdmissionNote(anemia);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));
    }

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsAdminUser() throws Exception {
        new LoginPage(driver).logInAsAdmin();

        startPatientVisit();

        patientDashboard.addAdmissionNoteAsAdminUser(malaria);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        String previousProvider = patientDashboard.providerForFirstEncounter();
        String previousLocation = patientDashboard.locationForFirstEncounter();
        patientDashboard.editExistingAdmissionNote(anemia, 3, 4);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        String currentProvider = patientDashboard.getAdmissionNoteForm().getProvider();
        String currentLocation = patientDashboard.getAdmissionNoteForm().getLocation();

        assertThat(currentProvider, not(previousProvider));
        assertThat(currentLocation, not(previousLocation));
    }

	
	@Test
	public void shouldCreateTransferNote() throws Exception {

        new LoginPage(driver).logInAsClinicalUser();

		startPatientVisit();

		patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 3);
		patientDashboard.addConsultNoteWithTransferToLocation(rubella, 3);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.TRANSFER_CREOLE_NAME), is(1));
		
	}
	
	@Test
	public void shouldCreateDischargeNote() throws Exception {

        new LoginPage(driver).logInAsClinicalUser();

		startPatientVisit();

        patientDashboard.addAdmissionNote(malaria);
		patientDashboard.addConsultNoteWithDischarge(anemia);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.DISCHARGE_CREOLE_NAME), is(1));
		
	}


	private void startPatientVisit() throws Exception {
		Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
	}
}
