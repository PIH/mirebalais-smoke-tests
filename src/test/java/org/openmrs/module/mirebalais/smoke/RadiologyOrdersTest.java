package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote.RADIOLOGY_CREOLE_NAME;

public class RadiologyOrdersTest extends DbTest {

	private static final String STUDY_1 = "Bassin et hanche gauche (Radiographie)";

	private static final String STUDY_2 = "Humérus - Gauche (Radiographie)";

    @Test
	public void orderSingleXRay() throws Exception {

        login();
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());

		clinicianDashboard.startVisit();
		visitNote.orderXRay(STUDY_1, STUDY_2);

		assertThat(visitNote.countEncountersOfType(RADIOLOGY_CREOLE_NAME), is(1));
	}

    @Test
    public void orderRetroSingleXRay() throws Exception {

        login();
        appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());

        clinicianDashboard.addRetroVisit();
        visitNote.orderXRay(STUDY_1, STUDY_2);

        assertThat(visitNote.countEncountersOfType(RADIOLOGY_CREOLE_NAME), is(1));
    }
}
