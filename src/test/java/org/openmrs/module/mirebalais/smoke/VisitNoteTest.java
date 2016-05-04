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
import org.openqa.selenium.By;

import static org.apache.commons.lang.StringUtils.replaceChars;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

        // TODO: hack, figure out why this is neceesary
        Thread.sleep(1000);

        // allergies section
        visitNote.openAllergiesSection();
        visitNote.addAllergy(1,1,1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.addAllergy(2,2,2);
        assertThat(visitNote.countOfAllergies(), is(2));
        visitNote.removeAllergy(1);
        assertThat(visitNote.countOfAllergies(), is(1));
        visitNote.returnFromAllergiesPage();
        visitNote.expandAllergiesSection();
        visitNote.clickFirstEncounterDetails();

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
