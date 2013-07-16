package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard.RADIOLOGY;

public class OrdersTest extends DbTest {
	
	private static final String STUDY_1 = "Hanche - Gauche, 2 vues (Radiographie)";
	
	private static final String STUDY_2 = "Humérus - Gauche, 2 vues (Radiographie)";
	
	@Test
	public void orderSingleXRay() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();

        login();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		patientDashboard.orderXRay(STUDY_1, STUDY_2);
		
		assertThat(patientDashboard.countEncountersOfType(RADIOLOGY), is(1));
	}
}
