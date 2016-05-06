package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckInFormPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.BaseHtmlForm;
import org.openqa.selenium.By;

import static org.apache.commons.lang.StringUtils.replaceChars;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class VisitNoteTest extends DbTest {

    @Test
    public void testCreateAdultInitialOutpatientEncounter() throws Exception{

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);
        HeaderPage header = new HeaderPage(driver);

        setLoginPage(getLoginPage());
        login();

        appDashboard.startClinicVisit();
        newCheckIn.findPatientAndClickOnCheckIn(testPatient.getIdentifier());
        newCheckIn.enterInfo(getPaperRecordEnabled());

        header.home();
        appDashboard.openApp(getVitalsAppIdentifier());
        findPatient(testPatient.getIdentifier());
        vitals.enterVitals();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addAdultInitialOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.ADULT_INITIAL_OUTPATIENT_CREOLE_NAME), is(1));

        // vaccine section
        visitNote.addAndRemoveVaccine(2,2);

        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.clickFirstEncounterDetails();
        visitNote.expandAllergiesSection();

        visitNote.expandFirstEncounter();

        // history section
        visitNote.editSection("pihcore-history");
        visitNote.fillOutHistoryForm("Some history");
        visitNote.expandSection("pihcore-history");
        assertTrue(visitNote.containsText("Some history"));

        // exam section
        visitNote.editSection("pihcore-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("pihcore-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan");
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

        // disposition section
        visitNote.editSection("pihcore-disposition");
        visitNote.fillOutDispositionForm(BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-disposition");
        assertTrue(visitNote.containsText(BaseHtmlForm.DISCHARGE));

    }

    @Test
    public void testCreateAdultFollowupOutpatientEncounter() throws Exception{

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);
        HeaderPage header = new HeaderPage(driver);

        setLoginPage(getLoginPage());
        login();

        appDashboard.startClinicVisit();
        newCheckIn.findPatientAndClickOnCheckIn(testPatient.getIdentifier());
        newCheckIn.enterInfo(getPaperRecordEnabled());

        header.home();
        appDashboard.openApp(getVitalsAppIdentifier());
        findPatient(testPatient.getIdentifier());
        vitals.enterVitals();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addAdultFollowupOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.ADULT_FOLLOWUP_OUTPATIENT_CREOLE_NAME), is(1));

        // vaccine section
        visitNote.addAndRemoveVaccine(2,2);

        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.clickFirstEncounterDetails();
        visitNote.expandAllergiesSection();

        visitNote.expandFirstEncounter();

        // exam section
        visitNote.editSection("pihcore-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("pihcore-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan");
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

        // disposition section
        visitNote.editSection("pihcore-disposition");
        visitNote.fillOutDispositionForm(BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-disposition");
        assertTrue(visitNote.containsText(BaseHtmlForm.DISCHARGE));

    }

    @Test
    public void testCreatePedsInitialOutpatientEncounter() throws Exception{

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);
        HeaderPage header = new HeaderPage(driver);

        setLoginPage(getLoginPage());
        login();

        appDashboard.startClinicVisit();
        newCheckIn.findPatientAndClickOnCheckIn(testPatient.getIdentifier());
        newCheckIn.enterInfo(getPaperRecordEnabled());

        header.home();
        appDashboard.openApp(getVitalsAppIdentifier());
        findPatient(testPatient.getIdentifier());
        vitals.enterVitals();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addPedsInitialOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.PEDS_INITIAL_OUTPATIENT_CREOLE_NAME), is(1));

        // vaccine section
        visitNote.addAndRemoveVaccine(2,2);

        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.clickFirstEncounterDetails();
        visitNote.expandAllergiesSection();

        visitNote.expandFirstEncounter();

        // supplements section
        visitNote.editSection("pihcore-supplements");
        visitNote.fillOutSupplementsForm();
        visitNote.expandSection("pihcore-supplements");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack

        // history section
        visitNote.editSection("pihcore-history");
        visitNote.fillOutHistoryForm("Some history");
        visitNote.expandSection("pihcore-history");
        assertTrue(visitNote.containsText("Some history"));

        // feeding section
        visitNote.editSection("pihcore-feeding");
        visitNote.fillOutFeedingForm();
        visitNote.expandSection("pihcore-feeding");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack

        // exam section
        visitNote.editSection("pihcore-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("pihcore-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan");
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

        // disposition section
        visitNote.editSection("pihcore-disposition");
        visitNote.fillOutDispositionForm(BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-disposition");
        assertTrue(visitNote.containsText(BaseHtmlForm.DISCHARGE));

    }

    @Test
    public void testCreatePedsFollowupOutpatientEncounter() throws Exception{

        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        VitalsApp vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);
        HeaderPage header = new HeaderPage(driver);

        setLoginPage(getLoginPage());
        login();

        appDashboard.startClinicVisit();
        newCheckIn.findPatientAndClickOnCheckIn(testPatient.getIdentifier());
        newCheckIn.enterInfo(getPaperRecordEnabled());

        header.home();
        appDashboard.openApp(getVitalsAppIdentifier());
        findPatient(testPatient.getIdentifier());
        vitals.enterVitals();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addPedsFollowupOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.PEDS_FOLLOWUP_OUTPATIENT_CREOLE_NAME), is(1));

        // vaccine section
        visitNote.addAndRemoveVaccine(2,2);

        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.clickFirstEncounterDetails();
        visitNote.expandAllergiesSection();

        visitNote.expandFirstEncounter();

        // supplements section
        visitNote.editSection("pihcore-supplements");
        visitNote.fillOutSupplementsForm();
        visitNote.expandSection("pihcore-supplements");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack

        // feeding section
        visitNote.editSection("pihcore-feeding");
        visitNote.fillOutFeedingForm();
        visitNote.expandSection("pihcore-feeding");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack

        // exam section
        visitNote.editSection("pihcore-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("pihcore-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan");
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

        // disposition section
        visitNote.editSection("pihcore-disposition");
        visitNote.fillOutDispositionForm(BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-disposition");
        assertTrue(visitNote.containsText(BaseHtmlForm.DISCHARGE));

    }

    protected LoginPage getLoginPage() { return new GeneralLoginPage(driver); }

    protected String getVitalsAppIdentifier() {
        return replaceChars(CustomAppLoaderConstants.Apps.VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
    }

    protected void findPatient(String identifier) {
        driver.findElement(By.partialLinkText(identifier)).click();
    }

    protected Boolean getPaperRecordEnabled() { return false; }
}
