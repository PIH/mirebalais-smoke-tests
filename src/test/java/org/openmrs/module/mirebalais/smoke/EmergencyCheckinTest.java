package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

@Deprecated
public class EmergencyCheckinTest extends DbTest {
	
	@Test
	public void checkinOnEmergencyShouldCheckInUnidentifiedPatient() throws Exception {

        initBasicPageObjects();
        EmergencyCheckin emergencyCheckinPO = new EmergencyCheckin(driver);

        login();

		appDashboard.openStartHospitalVisitApp();
		emergencyCheckinPO.checkinMaleUnindentifiedPatient();
		
		assertThat(patientRegistrationDashboard.getIdentifier(), notNullValue());
		assertThat(patientRegistrationDashboard.getGender(), is("M"));
		assertThat(patientRegistrationDashboard.getName(), stringContainsInOrder(Arrays.asList("UNKNOWN", "UNKNOWN")));
		
		populateTestPatientForTearDown();
	}
	
	private void populateTestPatientForTearDown() throws Exception {
        String patientId = (String) ((JavascriptExecutor) driver).executeScript("return patientId");
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
	}
}
