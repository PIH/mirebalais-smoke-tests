package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.InPatientList;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InPatientTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";

	private InPatientList inPatientList;

	@Test
	public void admitPatientTransferInsideHospitalAndFilterWard() throws Exception {

		inPatientList = new InPatientList(driver);

        logInAsPhysicianUser("Sal Gason");

		appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
		clinicianDashboard.startVisit();

		String admissionPlace = visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 3);
		assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        visitNote.addAdmissionNoteWithDefaultLocation(PRIMARY_DIAGNOSIS);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

        visitNote.gotoAppDashboard();
		assertFirstAdmittedAndCurrentWardAre(adultTestPatient.getIdentifier(), admissionPlace, admissionPlace);

		appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(adultTestPatient.getId());

		String transferPlace = visitNote.addConsultNoteWithTransferToLocation(PRIMARY_DIAGNOSIS, 4);

		assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(2));
		assertThat(visitNote.countEncountersOfType(VisitNote.TRANSFER_CREOLE_NAME), is(1));
        visitNote.gotoAppDashboard();
		assertFirstAdmittedAndCurrentWardAre(adultTestPatient.getIdentifier(), admissionPlace, transferPlace);

		appDashboard.goToClinicianFacingDashboard(anotherAdultTestPatient.getId());
		clinicianDashboard.startVisit();
		visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 3);
		visitNote.waitUntilVisitNoteOpen();

		appDashboard.openInPatientApp();
		inPatientList.filterBy(transferPlace);
		inPatientList.waitUntilInpatientListIsFilteredBy(transferPlace);
	}

	private void assertFirstAdmittedAndCurrentWardAre(String identifier, String firstAdmitted, String currentWard)
	        throws Exception {
		appDashboard.openInPatientApp();

		assertThat(inPatientList.getCurrentWard(identifier), is(currentWard));
		assertThat(inPatientList.getFirstAdmitted(identifier), is(firstAdmitted));
	}
}
