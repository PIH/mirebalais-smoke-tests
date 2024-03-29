package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.flows.CheckInPatientFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MultipleSubmitTest extends DbTest {


    @Test
    public void testMultipleEnterClicksOnCheckInForm() throws Exception {

        logInAsAdmin();
        appDashboard = new AppDashboard(driver);
        appDashboard.openCheckinApp();

        VisitNote patientDashboard = new VisitNote(driver);

        CheckInPatientFlow checkInPatientFlow = new CheckInPatientFlow(driver);
        checkInPatientFlow.checkInWithMultipleEnterKeystrokesOnSubmit(adultTestPatient.getIdentifier());

        new WebDriverWait(driver, 10).until(visibilityOfElementLocated(By.id("create-paper-record-dialog")));
        appDashboard.goToVisitNoteVisitListAndSelectFirstVisit(adultTestPatient.getId());

        assertThat(patientDashboard.countEncountersOfType(VisitNote.CHECKIN_CREOLE_NAME), is(1));
    }

}
