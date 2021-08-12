package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class AdmissionDischargeTransferRetroTest extends DbTest {

    private final String anemia = "D64.9";

    private final String malaria = "B54";

    @Test
    public void shouldEnterAndEditAnAdmissionNoteAsAdminUser() throws Exception {

        logInAsPhysicianUser("Sal Gason");
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.startVisit();

        visitNote.addAdmissionNoteAsAdminUser(malaria);
        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

        String previousProvider = visitNote.providerForFirstEncounter();
        String previousLocation = visitNote.locationForFirstAdmission();
        visitNote.editExistingAdmissionNote(anemia, 3, 1);

        assertThat(visitNote.countEncountersOfType(VisitNote.ADMISSION_CREOLE_NAME), is(1));

        String currentProvider = visitNote.getAdmissionNoteForm().getProvider();
        String currentLocation = visitNote.getAdmissionNoteForm().getLocation();

        // this is a bit of a hack
        assertFalse(previousProvider.contains(currentProvider));
        assertFalse(previousLocation.contains(currentLocation));

    }


}
