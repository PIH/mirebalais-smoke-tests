package org.openmrs.module.mirebalais.smoke;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.DeathCertificateFormPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

@Ignore
@Deprecated
public class DeathCertificateTest extends DbTest {

    @Test
    public void testEnteringDeathNote() throws Exception {
        AppDashboard appDashboard = new AppDashboard(driver);
        VisitNote patientDashboard = new VisitNote(driver);
        ClinicianDashboard clinicianDashboard = new ClinicianDashboard(driver);

        Patient patient = PatientDatabaseHandler.insertAdultTestPatient();

        logInAsPhysicianUser();
        appDashboard.goToClinicianFacingDashboard(patient.getId());

        DeathCertificateFormPage deathCertificateForm = clinicianDashboard.goToEnterDeathCertificateForm();
        deathCertificateForm.waitToLoad();
        deathCertificateForm.pressEnter(); // to confirm patient
        deathCertificateForm.enterFieldByKeyboard("u"); // Urban
        deathCertificateForm.enterFieldByKeyboard("v"); // Civil status: Vèf (widowed)
        deathCertificateForm.enterFieldByKeyboard("Software Tester"); // occupation
        deathCertificateForm.enterFieldByKeyboard("n"); // No maternal death
        deathCertificateForm.enterFieldsByKeyboard("7", "f", "2014", "", "", ""); // Enter 6 times for default date + time
        deathCertificateForm.selectFromDropdown("form-signed-role",2);
        //deathCertificateForm.enterFieldsByKeyboard("", "d"); // Default provider, Doctor
        deathCertificateForm.enterFieldByKeyboard("l"); // died in hospital
        deathCertificateForm.enterFieldsByKeyboard("ijans", "1"); // died in ER, hospitalized 1 day
        deathCertificateForm.enterFieldByKeyboard("w"); // yes, received care
        deathCertificateForm.enterFieldsByKeyboardWithWait(1000, "hemorragie intracerebrale", "AVC", "k76.6"); // three death causes
        deathCertificateForm.pressEnter(); // skip to next question
        deathCertificateForm.enterFieldByKeyboardWithWait(1000, "Related cause"); // condition related to, but not causing, the death
        deathCertificateForm.enterFieldsByKeyboard(" ", ""); // select surgery, not autopsy
        deathCertificateForm.enterFieldByKeyboard("CERT-1234"); // death certificate number

        String text = driver.findElement(By.cssSelector("#confirmation #dataCanvas")).getText();
        assertTrue(text.contains("Urbaine"));
        assertTrue(text.contains("Vèf"));
        assertTrue(text.contains("Software Tester"));
        // Lanmò matènèl: Non
        assertTrue(text.contains("(Doktè)")); // captured by role
        assertTrue(text.contains("Lopital"));
        assertTrue(text.contains("Ijans, 1"));
        // te resevwa swen: Wi
        assertTrue(text.contains("Hémorragie intracérébrale"));
        assertTrue(text.contains("AVC"));
        assertTrue(text.contains("Hypertension portale"));
        assertTrue(text.contains("Related cause"));

        // TODO: uncomment this line after a new version of chrome is released with a fix for this issue: https://code.google.com/p/chromium/issues/detail?id=513768
        //assertTrue(text.contains("Chirurgie"));
        assertTrue(text.contains("CERT-1234"));
        deathCertificateForm.pressEnter(); // to confirm form

        // now we're back on the patient dashboard
        assertTrue(clinicianDashboard.isDead());
    }

}
