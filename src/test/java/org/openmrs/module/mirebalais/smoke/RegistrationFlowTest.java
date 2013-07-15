package org.openmrs.module.mirebalais.smoke;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigInteger;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class RegistrationFlowTest extends DbTest {
	
	private final static String SCAN_MESSAGE = "Tanpri skane kat idantifikasyon kontinye...";
	
	@Before
	public void setUp() {
		initBasicPageObjects();
	}
	
	@Test
	public void registerPatientdPrintingCard() throws Exception {
		login();
		
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessPrintingCard();
        populateTestPatientForTearDown();

		assertThat(driver.findElement(By.tagName("body")).getText(), containsString(SCAN_MESSAGE));
	}

    private void populateTestPatientForTearDown() throws Exception {
        String patientId = (String) ((JavascriptExecutor) driver).executeScript("return patientId");
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
    }
}
