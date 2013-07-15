package org.openmrs.module.mirebalais.smoke;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
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
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        String patientId = (String) executor.executeScript("return patientId");

        ITable personName = getConnection().createQueryTable("person_name",
                "select * from person_name where person_id = " + patientId);
        Object personNameId = personName.getValue(0, "person_name_id");

        ITable personAddress = getConnection().createQueryTable("person_address",
                "select * from person_address where person_id = " + patientId);
        Object personAddressId = personAddress.getValue(0, "person_address_id");

        testPatient = new Patient("123", null, new BigInteger(patientId), null, -1, new BigInteger(personNameId.toString()),
                new BigInteger(personAddressId.toString()), new BigInteger("-1"));
    }
}
