package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersTest extends BasicMirebalaisSmokeTest {
	
	private static final String STUDY_1 = "Hanche - Gauche, 2 vues (Radiographie)";
	
	private static final String STUDY_2 = "Humérus - Gauche, 2 vues (Radiographie)";
	
	@Before
	public void setUp() {
		initBasicPageObjects();
	}
	
	@Test
	public void orderSingleXRay() throws Exception {
		loginPage.logInAsAdmin();
		createPatient();
		
		appDashboard.findPatientById(testPatient.getIdentifier());
		patientDashboard.startVisit();
		
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By
		        .cssSelector("div.status-container")));
		assertTrue(patientDashboard.hasActiveVisit());
		patientDashboard.orderXRay(STUDY_1, STUDY_2);
		
		assertThat(patientDashboard.countEncoutersOfType(PatientDashboard.RADIOLOGY), not(0));
	}
}
