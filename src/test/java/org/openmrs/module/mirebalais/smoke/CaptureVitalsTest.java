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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.apache.commons.lang.StringUtils.replaceChars;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CaptureVitalsTest extends DbTest {

    protected VitalsApp vitals;

    @Test
    public void checkInAndCaptureVitalsThruVitalsApp() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();
        vitals = new VitalsApp(driver);
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
        assertThat(isVitalsHomePageDisplayed(), is(true));

        appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(testPatient.getId());
        assertThat(visitNote.countEncountersOfType(VisitNote.VITALS_CREOLE_NAME), is(1));
    }

    protected LoginPage getLoginPage() { return new GeneralLoginPage(driver); }

    protected String getVitalsAppIdentifier() {
        return replaceChars(CustomAppLoaderConstants.Apps.VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
    }

    protected void findPatient(String identifier) {
        driver.findElement(By.partialLinkText(identifier)).click();
    }

    protected boolean isVitalsHomePageDisplayed() {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("vitals-list")));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected Boolean getPaperRecordEnabled() { return false; }

}

