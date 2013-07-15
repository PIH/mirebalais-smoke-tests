package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.BasicReportData;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.BasicReportPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openmrs.module.mirebalais.smoke.pageobjects.ReportsHomePage;
import org.openqa.selenium.JavascriptExecutor;

public class EmergencyCheckinTest extends DbTest {
	
	private EmergencyCheckin emergencyCheckinPO;
	
	private ReportsHomePage reportsHomePage;
	
	private BasicReportPage basicReport;
	
	@Before
	public void setUp() throws Exception {
		initBasicPageObjects();
		emergencyCheckinPO = new EmergencyCheckin(driver);
		reportsHomePage = new ReportsHomePage(driver);
		basicReport = new BasicReportPage(driver);
		
		testPatient = new Patient(null, null, null, null, null, null, null, null);
	}
	
	@Test
	public void checkinOnEmergencyShouldCountOnReports() throws Exception {
		loginPage.logInAsAdmin();
		
		appDashboard.openReportApp();
		reportsHomePage.openBasicStatisticsReport();
		BasicReportData brd1 = basicReport.getData();
		
		appDashboard.openStartHospitalVisitApp();
		emergencyCheckinPO.checkinMaleUnindentifiedPatient();
		
		assertThat(patientRegistrationDashboard.getIdentifier(), notNullValue());
		assertThat(patientRegistrationDashboard.getGender(), is("M"));
		assertThat(patientRegistrationDashboard.getName(), stringContainsInOrder(Arrays.asList("UNKNOWN", "UNKNOWN")));
		
		populateTestPatientForTearDown();
		
		appDashboard.openReportApp();
		reportsHomePage.openBasicStatisticsReport();
		BasicReportData brd2 = basicReport.getData();
		
		assertThat(brd2.getOpenVisits(), greaterThan(brd1.getOpenVisits()));
		assertThat(brd2.getRegistrationToday(), greaterThan(brd1.getRegistrationToday()));
	}
	
	private void populateTestPatientForTearDown() throws Exception {
        String patientId = (String) ((JavascriptExecutor) driver).executeScript("return patientId");
        PatientDatabaseHandler.addTestPatientForDelete(new BigInteger(patientId));
	}
}
