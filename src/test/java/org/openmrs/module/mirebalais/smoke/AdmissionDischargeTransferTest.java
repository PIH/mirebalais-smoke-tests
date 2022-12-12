package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AwaitingAdmissionApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdmissionDischargeTransferTest extends DbTest {

	private final String anemia = "D64.9";

	private final String malaria = "B54";

	private final String rubella = "B06.9";

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsClinicalUser() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addAdmissionNote(malaria);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

        visitNote.editExistingAdmissionNote(anemia);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

    }

	@Test
	public void shouldCreateTransferNote() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addAdmissionNote(malaria);
		visitNote.addConsultNoteWithTransferToLocation(rubella, "Ijans");

        assertThat(visitNote.countEncountersOfType(VisitNote.TRANSFER_CREOLE_NAME), is(1));
	}

	@Test
	public void shouldCreateDischargeNote() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addAdmissionNote(malaria);
		visitNote.addConsultNoteWithDischarge(anemia);

        assertThat(visitNote.countEncountersOfType(VisitNote.DISCHARGE_CREOLE_NAME), is(1));
	}

    @Test
    public void shouldAdmitPatientViaAwaitingAdmissionApp() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addConsultNoteWithAdmissionToLocation(malaria, "Sal Gason");
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(adultTestPatient);

        app.clickOnLastAdmitButton();  // new patient should be at the end of the list
        visitNote.getAdmissionNoteForm().fillFormWithDiagnosis(malaria);

        app.assertPatientNotInAwaitingAdmissionTable(adultTestPatient);
    }

    @Test
    public void shouldCancelPatientFromAwaitingAdmissionApp() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addConsultNoteWithAdmissionToLocation(malaria, "Sal Gason");
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        header.home();
        appDashboard.openAwaitingAdmissionApp();

        AwaitingAdmissionApp app = new AwaitingAdmissionApp(driver);

        app.assertPatientInAwaitingAdmissionTable(adultTestPatient);
        app.cancelLastAdmission();
        app.assertPatientNotInAwaitingAdmissionTable(adultTestPatient);

    }
}
