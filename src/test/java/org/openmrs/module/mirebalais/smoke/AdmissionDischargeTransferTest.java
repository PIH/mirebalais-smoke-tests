package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AwaitingAdmissionApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdmissionDischargeTransferTest extends DbTest {
	
	private final String anemia = "D64.9";
	
	private final String malaria = "B54";
	
	private final String rubella = "B06.9";

    private Patient testPatient;

    @BeforeClass
    public static void prepare() throws Exception {
        logInAsPhysicianUser("Sal Gason");
    }

    @Before
    public void setUp() throws Exception {
        testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();

        appDashboard.goToPatientPage(testPatient.getId());
        patientDashboard.startVisit();
    }

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsClinicalUser() throws Exception {

        patientDashboard.addAdmissionNote(malaria);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

        patientDashboard.editExistingAdmissionNote(anemia);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.ADMISSION_CREOLE_NAME), is(1));

    }

	@Test
	public void shouldCreateTransferNote() throws Exception {

        patientDashboard.addAdmissionNote(malaria);
		patientDashboard.addConsultNoteWithTransferToLocation(rubella, 3);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.TRANSFER_CREOLE_NAME), is(1));

		
	}
	
	@Test
	public void shouldCreateDischargeNote() throws Exception {

        patientDashboard.addAdmissionNote(malaria);
		patientDashboard.addConsultNoteWithDischarge(anemia);

        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.DISCHARGE_CREOLE_NAME), is(1));

	}

    @Test
    public void shouldAdmitPatientViaAwaitingAdmissionApp() throws Exception {

        patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 6);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(testPatient);

        app.clickOnLastAdmitButton();  // new patient should be at the end of the list
        patientDashboard.getAdmissionNoteForm().fillFormWithDiagnosis(malaria);

        app.assertPatientNotInAwaitingAdmissionTable(testPatient);
    }

    @Test
    public void shouldCancelPatientFromAwaitingAdmissionApp() throws Exception {

        patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 6);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(testPatient);
        app.cancelLastAdmission();
        app.assertPatientNotInAwaitingAdmissionTable(testPatient);

    }
}
