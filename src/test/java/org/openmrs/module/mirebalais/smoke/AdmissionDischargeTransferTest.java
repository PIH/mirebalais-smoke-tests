package org.openmrs.module.mirebalais.smoke;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AwaitingAdmissionApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;

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
        testPatient = PatientDatabaseHandler.insertAdultTestPatient();
        adminPage.updateLuceneIndex();
        initBasicPageObjects();

        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
        clinicianDashboard.startVisit();
    }

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsClinicalUser() throws Exception {

        visitNote.addAdmissionNote(malaria);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

        visitNote.editExistingAdmissionNote(anemia);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

    }

	@Test
	public void shouldCreateTransferNote() throws Exception {

        visitNote.addAdmissionNote(malaria);
		visitNote.addConsultNoteWithTransferToLocation(rubella, 5);  // location 5 = Ijans

        assertThat(visitNote.countEncountersOfType(VisitNote.TRANSFER_CREOLE_NAME), is(1));


	}

	@Test
	public void shouldCreateDischargeNote() throws Exception {

        visitNote.addAdmissionNote(malaria);
		visitNote.addConsultNoteWithDischarge(anemia);

        assertThat(visitNote.countEncountersOfType(VisitNote.DISCHARGE_CREOLE_NAME), is(1));

	}

    @Test
    public void shouldAdmitPatientViaAwaitingAdmissionApp() throws Exception {

        visitNote.addConsultNoteWithAdmissionToLocation(malaria, 9);  // location 9 = Sal Gason, where this user logs in
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(testPatient);

        app.clickOnLastAdmitButton();  // new patient should be at the end of the list
        visitNote.getAdmissionNoteForm().fillFormWithDiagnosis(malaria);

        app.assertPatientNotInAwaitingAdmissionTable(testPatient);
    }

    @Test
    public void shouldCancelPatientFromAwaitingAdmissionApp() throws Exception {

        visitNote.addConsultNoteWithAdmissionToLocation(malaria, 9);  // location 9 = Sal Gason, where this user logs in
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(testPatient);
        app.cancelLastAdmission();
        app.assertPatientNotInAwaitingAdmissionTable(testPatient);

    }
}
