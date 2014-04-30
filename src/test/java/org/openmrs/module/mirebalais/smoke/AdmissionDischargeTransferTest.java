package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AwaitingAdmissionApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;

public class AdmissionDischargeTransferTest extends DbTest {
	
	private final String anemia = "D64.9";
	
	private final String malaria = "B54";
	
	private final String rubella = "B06.9";

    private Patient testPatient;

    @BeforeClass
    public static void prepare() throws Exception {
        logInAsClinicalUser();
    }

    @Before
    public void setUp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
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


		patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 3);
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
    @Ignore
    public void shouldAdmitPatientViaAwaitingAdmissionApp() throws Exception {

        patientDashboard.addConsultNoteWithAdmissionToLocation(malaria, 2);
        assertThat(patientDashboard.countEncountersOfType(PatientDashboard.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(testPatient);
    }
}
