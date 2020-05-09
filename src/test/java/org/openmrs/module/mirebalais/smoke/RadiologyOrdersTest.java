package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.Toast;
import org.openqa.selenium.By;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote.RADIOLOGY_CREOLE_NAME;

public class RadiologyOrdersTest extends DbTest {
	
	private static final String STUDY_1 = "Hanche - Gauche, 2 vues (Radiographie)";
	
	private static final String STUDY_2 = "Humérus - Gauche, 2 vues (Radiographie)";

    private static final String START_DATE = "01-08-2013";
    private static final String START_DATE_FIELD = "2013-08-01 00:00:00";
    private static final String END_DATE = "02-08-2013";
    private static final String END_DATE_FIELD = "2013-08-02 00:00:00";

    private By confirmButton = By.cssSelector("#retrospective-visit-creation-dialog .confirm");

    @Before
    public void setUp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
        initBasicPageObjects();
        login();
        appDashboard.goToClinicianFacingDashboard(testPatient.getId());
    }
    @After
    public void tearDown() {
        Toast.closeToast(driver);
        logout();
    }

    @Test
	public void orderSingleXRay() throws Exception {

		clinicianDashboard.startVisit();
		visitNote.orderXRay(STUDY_1, STUDY_2);
		
		assertThat(visitNote.countEncountersOfType(RADIOLOGY_CREOLE_NAME), is(1));
	}

    @Test
    public void orderRetroSingleXRay() throws Exception {

        clinicianDashboard.addRetroVisit();
        visitNote.orderXRay(STUDY_1, STUDY_2);

        assertThat(visitNote.countEncountersOfType(RADIOLOGY_CREOLE_NAME), is(1));
    }
}
