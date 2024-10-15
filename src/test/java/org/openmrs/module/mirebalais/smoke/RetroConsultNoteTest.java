package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RetroConsultNoteTest extends DbTest {

    private static final String PRIMARY_DIAGNOSIS = "IGU";
    private static final String EDITED_PRIMARY_DIAGNOSIS = "Asthme";

    @Test
    public void addConsultationToAnActiveVisit() throws Exception {

        logInAsAdmin("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());

        clinicianDashboard.startVisit();
        visitNote.addRetroConsultNoteWithAdmissionToLocation(PRIMARY_DIAGNOSIS, null,"Sal Gason");
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));
    }

    @Test
    public void addConsultationToARetroVisit() throws Exception {

        logInAsAdmin("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());

        clinicianDashboard.addRetroVisit();
        visitNote.addRetroConsultNoteWithDischarge(PRIMARY_DIAGNOSIS);
        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));
    }

    @Test
    public void editRetroConsultationNote() throws Exception {

        logInAsAdmin("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());

        clinicianDashboard.addRetroVisit();
        visitNote.addRetroConsultNoteWithDischarge(PRIMARY_DIAGNOSIS);
        visitNote.editExistingConsultNote(EDITED_PRIMARY_DIAGNOSIS);

        assertThat(visitNote.countEncountersOfType(VisitNote.CONSULTATION_CREOLE_NAME), is(1));

        //visitNote.viewConsultationDetails();
        new WebDriverWait(driver, 5).until(visibilityOfElementLocated(VisitNote.consultNoteSectionHeader));
        assertThat(visitNote.containsText(EDITED_PRIMARY_DIAGNOSIS), is(true));
        assertThat(visitNote.containsText(PRIMARY_DIAGNOSIS), is(false));
    }

}
