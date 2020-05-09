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

    //@Test
    public void testCreateAdultInitialOutpatientEncounter() throws Exception{

        // make sure we are logged out to start with
        logout();

        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
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

        // chief complaint section
     /*   visitNote.editSection("pihcore-chief-complaint");
        visitNote.fillOutChiefComplaint("some complaint");
        visitNote.expandSection("pihcore-chief-complaint");
        assertTrue(visitNote.containsText("some complaint"));
*/
        // allergies section
/*        visitNote.openAllergiesSection();
        visitNote.addAllergy(1, 1, 1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2, 2, 2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.waitUntilVisitNoteOpen();
        Thread.sleep(2000);  // hack, seems to be needed for some reason,
        visitNote.expandFirstEncounter();*/

        // history section
        visitNote.editSection("pihcore-history");
        visitNote.fillOutHistoryForm("Some history");
        visitNote.expandSection("pihcore-history");
        assertTrue(visitNote.containsText("Some history"));

        // exam section
        visitNote.editSection("physical-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("physical-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan", BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));
    }

    //@Test
    public void testCreateAdultFollowupOutpatientEncounter() throws Exception{

        // make sure we are logged out to start with
        logout();

        Patient testPatient = PatientDatabaseHandler.insertAdultTestPatient();
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

        // chief complaint section
        visitNote.editSection("pihcore-chief-complaint");
        visitNote.fillOutChiefComplaint("some complaint");
        visitNote.expandSection("pihcore-chief-complaint");
        assertTrue(visitNote.containsText("some complaint"));

/*        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.waitUntilVisitNoteOpen();
        Thread.sleep(2000);  // hack, seems to be needed for some reason,
        visitNote.expandFirstEncounter();*/

        // exam section
        visitNote.editSection("physical-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("physical-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan", BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

    }

    //@Test
    public void testCreatePedsInitialOutpatientEncounter() throws Exception{

        // make sure we are logged out to start with
        logout();

        Patient testPatient = PatientDatabaseHandler.insertNewbornTestPatient();
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
        vitals.enterVitalsForInfant();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addPedsInitialOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.PEDS_INITIAL_OUTPATIENT_CREOLE_NAME), is(1));

        // chief complaint section
    /*    visitNote.editSection("pihcore-chief-complaint");
        visitNote.fillOutChiefComplaint("some complaint");
        visitNote.expandSection("pihcore-chief-complaint");
        assertTrue(visitNote.containsText("some complaint"));*/

        // vaccine section
        visitNote.addAndRemoveVaccine(2,2);

        // allergies section
       /* visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.waitUntilVisitNoteOpen();
        Thread.sleep(2000);  // hack, seems to be needed for some reason,
        visitNote.expandFirstEncounter();*/

        // TODO commenting this out for now because it's been very flaky and we haven't been able to fix it--skipping it seems the lesser of two evils
        // supplements section
   /*     visitNote.editSection("pihcore-supplements");
        visitNote.fillOutSupplementsForm();
        visitNote.expandSection("pihcore-supplements");
        assertTrue(visitNote.containsText("[X]"));*/  // bit of a hack

        // history section
        visitNote.editSection("pihcore-history");
        visitNote.fillOutHistoryForm("Some history");
        visitNote.expandSection("pihcore-history");
        assertTrue(visitNote.containsText("Some history"));

        // feeding section
/*        visitNote.editSection("pihcore-feeding");
        visitNote.fillOutFeedingForm();
        visitNote.expandSection("pihcore-feeding");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack*/

        // exam section
        visitNote.editSection("physical-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("physical-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan", BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

    }

    //@Test
    public void testCreatePedsFollowupOutpatientEncounter() throws Exception{

        // make sure we are logged out to start with
        logout();

        Patient testPatient = PatientDatabaseHandler.insertNewbornTestPatient();
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
        vitals.enterVitalsForInfant();

        header.home();
        appDashboard.openWaitingForConsultApp();
        findPatient(testPatient.getIdentifier());

        visitNote.addPedsFollowupOutpatient();
        assertThat(visitNote.countEncountersOfType(VisitNote.PEDS_FOLLOWUP_OUTPATIENT_CREOLE_NAME), is(1));

        // chief complaint section
        visitNote.editSection("pihcore-chief-complaint");
        visitNote.fillOutChiefComplaint("some complaint");
        visitNote.expandSection("pihcore-chief-complaint");
        assertTrue(visitNote.containsText("some complaint"));

        // vaccine section
        visitNote.addAndRemoveVaccine(2, 2);

      /*  // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1, 1, 1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2, 2, 2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.waitUntilVisitNoteOpen();
        Thread.sleep(2000);  // hack, seems to be needed for some reason,
        visitNote.expandFirstEncounter();*/

        // TODO commenting this out for now because it's been very flaky and we haven't been able to fix it--skipping it seems the lesser of two evils
        // supplements section
   /*     visitNote.editSection("pihcore-supplements");
        visitNote.fillOutSupplementsForm();
        visitNote.expandSection("pihcore-supplements");
        assertTrue(visitNote.containsText("[X]"));  */// bit of a hack

        // feeding section
/*        visitNote.editSection("pihcore-feeding");
        visitNote.fillOutFeedingForm();
        visitNote.expandSection("pihcore-feeding");
        assertTrue(visitNote.containsText("[X]"));  // bit of a hack*/

        // exam section
        visitNote.editSection("physical-exam");
        visitNote.fillOutExamForm("Some comment");
        visitNote.expandSection("physical-exam");
        assertTrue(visitNote.containsText("Some comment"));

        // diagnosis section
        visitNote.editSection("pihcore-diagnosis");
        visitNote.fillOutDiagnosisForm("IGU");
        visitNote.expandSection("pihcore-diagnosis");
        assertTrue(visitNote.containsText("Douleur"));

        // plan section
        visitNote.editSection("pihcore-plan");
        visitNote.fillOutPlanForm("Some plan", BaseHtmlForm.DISCHARGE);
        visitNote.expandSection("pihcore-plan");
        assertTrue(visitNote.containsText("Some plan"));

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
