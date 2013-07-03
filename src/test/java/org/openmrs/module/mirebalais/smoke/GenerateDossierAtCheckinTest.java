package org.openmrs.module.mirebalais.smoke;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestDriver;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.WebDriver;

@Ignore
public class GenerateDossierAtCheckinTest extends DbTest {
    private static WebDriver driver;

    @Override
    public void setUp() throws Exception {
        driver = new SmokeTestDriver().getDriver();
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    @Test
    public void test_shouldCreateDossierLocallyAtCheckinWhenDossierIsMissing() throws Exception {
        new LoginPage(driver).logIn("admin", "Admin123", 10);

        new AppDashboard(driver).openCheckinApp();

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInAndCreateLocalDossierFor("TESTIDTEST");
        checkInPatientFlow.enterPatientIdentifier("TESTIDTEST");

        PatientDashboard patientDashboard = new PatientDashboard(driver);
        assertTrue(patientDashboard.getDossieNumber().matches("A\\d{6}"));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("datasets/patients_dataset.xml"));
    }
}
