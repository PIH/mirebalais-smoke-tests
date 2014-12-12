package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class LegacyRegistrationFlowTest extends DbTest {
	
	private final static String SCAN_MESSAGE = "Tanpri skane kat idantifikasyon kontinye...";

	@Test
	public void registerPatientdPrintingCard() throws Exception {
		initBasicPageObjects();

        login();
		
		appDashboard.openLegacyPatientRegistrationApp();
		legacyRegistration.goThruRegistrationProcessPrintingCard();
        populateTestPatientForTearDown();

		assertThat(driver.findElement(By.tagName("body")).getText(), containsString(SCAN_MESSAGE));
	}

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = (String) ((JavascriptExecutor) driver).executeScript("return patientId");
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
