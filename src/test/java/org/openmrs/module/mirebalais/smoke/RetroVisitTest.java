package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RetroVisitTest extends DbTest {

    @Test
    public void shouldAddRetroVisit() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
        initBasicPageObjects();

        login();

        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
        clinicianDashboard.addRetroVisit();

        assertThat(visitNote.countVisits(), is(1));
    }

}
