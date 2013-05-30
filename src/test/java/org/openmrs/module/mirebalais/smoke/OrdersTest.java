package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.Waiter;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;

public class OrdersTest extends BasicMirebalaisSmokeTest {

    private static final String STUDY_1 = "Hanche - Gauche, 2 vues (Radiographie)";
    private static final String STUDY_2 = "Humérus - Gauche, 2 vues (Radiographie)";
    
    @Before
	public void setUp() {
    	initBasicPageObjects();
    }
    
	@Test
	public void orderSingleXRay() throws Exception{
        loginPage.logInAsAdmin();
        createPatient();

        appDashboard.findPatientById(testPatient.getIdentifier());
        patientDashboard.startVisit();

        Waiter.waitForElementToDisplay(By.cssSelector("div.status-container"), 5, driver);
        assertTrue(patientDashboard.hasActiveVisit());
		patientDashboard.orderXRay(STUDY_1, STUDY_2);
		
		assertThat(patientDashboard.countEncouters(PatientDashboard.RADIOLOGY), not(0));
	}
}

