package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckInFormPage;
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

        vitals = new VitalsApp(driver);
        CheckInFormPage newCheckIn = new CheckInFormPage(driver);

        login();

        appDashboard.startClinicVisit();
        newCheckIn.findPatientAndClickOnCheckIn(adultTestPatient.getIdentifier());
        newCheckIn.enterInfo(getPaperRecordEnabled());

        header.home();
        Thread.sleep(3000);  // hack, sleep 3 second, sometimes if the page is opened to quickly user is not in queue yet
        appDashboard.openApp(getVitalsAppIdentifier());
        findPatient(adultTestPatient.getIdentifier());

        vitals.enterVitals();
        assertThat(isVitalsHomePageDisplayed(), is(true));

        appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(adultTestPatient.getId());
        assertThat(visitNote.countEncountersOfType(VisitNote.VITALS_CREOLE_NAME), is(1));
    }

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

