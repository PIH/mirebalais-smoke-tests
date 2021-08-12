package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsultNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

	@Test
	public void addConsultationToAVisitWithoutCheckin() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

		visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
		assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));
	}

	@Test
	public void addConsultationNoteWithDeathAsDispositionClosesVisit() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

	    visitNote.addConsultNoteWithDeath(PRIMARY_DIAGNOSIS);
		assertThat(clinicianDashboard.isDead(), is(true));
		assertThat(clinicianDashboard.hasActiveVisit(), is(false));
	}

    @Test
    public void editConsultationNote() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, 2);
        visitNote.editExistingConsultNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        //visitNote.viewConsultationDetails();
        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(VisitNote.consultNoteSectionHeader));
        assertThat(visitNote.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(visitNote.containsText(PRIMARY_DIAGNOSIS), is(false));
    }

}
