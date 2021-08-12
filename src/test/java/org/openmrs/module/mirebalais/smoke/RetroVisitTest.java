package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RetroVisitTest extends DbTest {

    @Test
    public void shouldAddRetroVisit() throws Exception {
        login();
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
        clinicianDashboard.addRetroVisit();
        assertThat(visitNote.countVisits(), is(1));
    }

}
