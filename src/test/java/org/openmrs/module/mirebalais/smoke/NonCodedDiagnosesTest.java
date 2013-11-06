package org.openmrs.module.mirebalais.smoke;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.NonCodedDiagnosesList;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ReportsHomePage;
import org.openqa.selenium.By;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class NonCodedDiagnosesTest extends DbTest {

    private static final String NON_CODED_DIAGNOSIS = "nonCodedDiagnosis";
    private static final String CODED_DIAGNOSIS = "IGU";
    private static final String CODE_DIAGNOSIS_DIALOG = "code-diagnosis-dialog";


    private By cancelCodeDiagnosis = By.cssSelector("#" + CODE_DIAGNOSIS_DIALOG + " .cancel");
    private By confirmCodeDiagnosis = By.cssSelector("#" + CODE_DIAGNOSIS_DIALOG + " .confirm");


    Patient testPatient;
    ReportsHomePage reportsHomePage;

    private void createConsultNote() throws Exception {
        patientDashboard.addConsultNoteWithDischarge(NON_CODED_DIAGNOSIS);
        showEncounterAtPatientDashboard();
    }

    private void createEDNote() throws Exception {
        patientDashboard.addEmergencyDepartmentNote(NON_CODED_DIAGNOSIS);
        showEncounterAtPatientDashboard();
    }

    private void showEncounterAtPatientDashboard(){
        patientDashboard.wait5seconds.until(visibilityOfElementLocated(By.className("encounter-name")));
        appDashboard.goToPatientPage(testPatient.getId());
    }

    @BeforeClass
    public static void prepare() throws Exception {
        logInAsClinicalUser();
    }

    @Before
    public void setUp() throws Exception {
        testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();

        appDashboard.goToPatientPage(testPatient.getId());
        patientDashboard.startVisit();
        reportsHomePage = new ReportsHomePage(driver);
    }

    @Test
    public void shouldShowNonCodedDiagnosesPageUsingConsultNote() throws Exception {
        createConsultNote();
        appDashboard.openReportApp();
        reportsHomePage.openNonCodedDiagnosesReport();
        NonCodedDiagnosesList nonCodedDiagnosesList = new NonCodedDiagnosesList(driver);
        List<String> nonCodedDiagnoses = nonCodedDiagnosesList.getNonCodedDiagnoses();

        Assert.assertTrue(nonCodedDiagnoses.contains(NON_CODED_DIAGNOSIS));
    }

    @Test
        public void shouldShowNonCodedDiagnosesPageUsingEdNote() throws Exception {
        createEDNote();
        appDashboard.openReportApp();
        reportsHomePage.openNonCodedDiagnosesReport();
        NonCodedDiagnosesList nonCodedDiagnosesList = new NonCodedDiagnosesList(driver);
        List<String> nonCodedDiagnoses = nonCodedDiagnosesList.getNonCodedDiagnoses();

        Assert.assertTrue(nonCodedDiagnoses.contains(NON_CODED_DIAGNOSIS));
    }

    @Test
    public void shouldNotShowNonCodedDiagnosisAfterReplaceforExistingCodesForConsultNote() throws Exception {
        createConsultNote();
        appDashboard.openReportApp();
        reportsHomePage.openNonCodedDiagnosesReport();
        NonCodedDiagnosesList nonCodedDiagnosesList = new NonCodedDiagnosesList(driver);
        nonCodedDiagnosesList.openCodeDiagnosisDialog(NON_CODED_DIAGNOSIS);
        nonCodedDiagnosesList.setClearTextToField("diagnosis-search", CODED_DIAGNOSIS);
        driver.findElement(By.cssSelector("strong.matched-name")).click();
        nonCodedDiagnosesList.clickOn(confirmCodeDiagnosis);
        nonCodedDiagnosesList.wait5seconds.until(invisibilityOfElementLocated(By.id(CODE_DIAGNOSIS_DIALOG)));
        List<String>  nonCodedDiagnoses = nonCodedDiagnosesList.getNonCodedDiagnoses();

        Assert.assertFalse(nonCodedDiagnoses.contains(NON_CODED_DIAGNOSIS));
    }

    @Test
    public void shouldNotShowNonCodedDiagnosisAfterReplaceforExistingCodesforEDNote() throws Exception {
        createEDNote();
        appDashboard.openReportApp();
        reportsHomePage.openNonCodedDiagnosesReport();
        NonCodedDiagnosesList nonCodedDiagnosesList = new NonCodedDiagnosesList(driver);
        nonCodedDiagnosesList.openCodeDiagnosisDialog(NON_CODED_DIAGNOSIS);
        nonCodedDiagnosesList.setClearTextToField("diagnosis-search", CODED_DIAGNOSIS);
        driver.findElement(By.cssSelector("strong.matched-name")).click();
        nonCodedDiagnosesList.clickOn(confirmCodeDiagnosis);
        nonCodedDiagnosesList.wait5seconds.until(invisibilityOfElementLocated(By.id(CODE_DIAGNOSIS_DIALOG)));
        List<String> nonCodedDiagnoses = nonCodedDiagnosesList.getNonCodedDiagnoses();

        Assert.assertFalse(nonCodedDiagnoses.contains(NON_CODED_DIAGNOSIS));
    }

}
