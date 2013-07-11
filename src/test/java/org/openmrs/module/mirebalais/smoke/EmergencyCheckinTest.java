package org.openmrs.module.mirebalais.smoke;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.BasicReportData;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.pageobjects.BasicReportPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.EmergencyCheckin;
import org.openmrs.module.mirebalais.smoke.pageobjects.ReportsHomePage;
import org.openqa.selenium.JavascriptExecutor;

import java.math.BigInteger;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

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

        testPatient = new Patient(null, null, null, null, null, null, null);
    }

    @Test
    public void checkinOnEmergencyShouldCountOnReports() throws Exception {
        loginPage.logInAsAdmin();

    	appDashboard.openReportApp();
		reportsHomePage.openBasicStatisticsReport();
    	BasicReportData brd1 = basicReport.getData();
    	
    	appDashboard.openStartHospitalVisitApp();
        emergencyCheckinPO.checkinMaleUnindentifiedPatient();

        populateTestPatientForTearDown();

        assertThat(patientRegistrationDashboard.getIdentifier(), notNullValue());
        assertThat(patientRegistrationDashboard.getGender(), is("M"));
        assertThat(patientRegistrationDashboard.getName(), stringContainsInOrder(Arrays.asList("UNKNOWN", "UNKNOWN")));
        
        appDashboard.openReportApp();
		reportsHomePage.openBasicStatisticsReport();
        BasicReportData brd2 = basicReport.getData();
        
        assertThat(brd2.getOpenVisits(), greaterThan(brd1.getOpenVisits()));
        assertThat(brd2.getRegistrationToday(), greaterThan(brd1.getRegistrationToday()));
    }

    private void populateTestPatientForTearDown() throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        String patientId = (String) executor.executeScript("return patientId");

        ITable personName = getConnection().createQueryTable("person_name",
                "select * from person_name where person_id = " + patientId);
        Object personNameId = personName.getValue(0, "person_name_id");

        testPatient = new Patient("123", null, new BigInteger(patientId), -1,
                new BigInteger(personNameId.toString()), new BigInteger("-1"), new BigInteger("-1"));
    }
}
