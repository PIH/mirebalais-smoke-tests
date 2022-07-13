package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.pihcore.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ClinicianDashboard extends AbstractPageObject {

    public static final String ACTIVE_VISIT_CREOLE_MESSAGE = "aktif";

    private static final By startVisitAction = By.id("coreapps.createVisit");

    private static final By confirmStartVisit = By.cssSelector("#quick-visit-creation-dialog .confirm");

    private static final By addRetroVisit = By.id("coreapps.createRetrospectiveVisit");

    private static final By retroStartDate = By.cssSelector("#retrospectiveVisitStartDate-display");

    private static final By retroStopDate = By.cssSelector("#retrospectiveVisitStopDate-display");

    private static final By confirmRetroVisit = By.cssSelector("#retrospective-visit-creation-dialog .confirm");

    private static final By requestAppointment = By.id("appointmentschedulingui.requestAppointment");

    private static final By deathCertificate = By.id(CustomAppLoaderConstants.Extensions.DEATH_CERTIFICATE_OVERALL_ACTION);

    private static final By visitDetails = By.id("visit-details");

    public ClinicianDashboard(WebDriver driver) {
        super(driver);
    }

    public boolean isOpenForPatient(Patient patient) {

        try {
            // just make sure that a few core page elements exist
            driver.findElement(By.className("info-section"));
            driver.findElement(By.className("action-section"));
            driver.findElement(By.className("patient-header"));

            // patient specific information
            if (!driver.findElement(By.className("patient-header")).getText().contains(patient.getIdentifier())) {
                System.out.println("Did not find identifier " + patient.getIdentifier() + " in patient header.");
                return false;
            }

            return true;
        }
        catch (NoSuchElementException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

    }

    public boolean isOpenForPatient(String givenName, String familyName) {

        // just make sure that a few core page elements exist
        driver.findElement(By.className("info-section"));
        driver.findElement(By.className("action-section"));
        driver.findElement(By.className("patient-header"));


        try {
            // patient specific information
            if (!driver.findElement(By.className("patient-header")).getText().contains(familyName) ||
                    !driver.findElement(By.className("patient-header")).getText().contains(givenName)) {
                System.out.println("Did not find name " + givenName + " " + familyName + " in patient header.");
                return false;
            }

            return true;
        }
        catch (NoSuchElementException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

    }

    public String getDossierNumber() {
        List<WebElement> elements = driver.findElements(By.cssSelector(".identifiers span"));
        return elements.get(2).getText();
    }

    public boolean hasActiveVisit() {
        return driver.findElements(By.cssSelector(".lozenge.active")).get(0).getText().toLowerCase().contains(ACTIVE_VISIT_CREOLE_MESSAGE);
    }

    public void startVisit() {
        clickOn(startVisitAction);
        clickOn(confirmStartVisit);
        wait15seconds.until(visibilityOfElementLocated(visitDetails));  // visit note should open
    }

    public void addRetroVisit() {
        clickOn(addRetroVisit);
        hitTabKey(retroStartDate);
        hitTabKey(retroStopDate);
        clickOn(confirmRetroVisit);
    }

    public void openRequestAppointmentForm() {
        clickOn(requestAppointment);
    }

    public void requestRecord() {
        clickOn(By.id(CustomAppLoaderConstants.Extensions.REQUEST_PAPER_RECORD_OVERALL_ACTION));
        clickOn(By.cssSelector("#request-paper-record-dialog .confirm"));
    }

    public boolean canRequestRecord() {
        return driver.findElement((By.id(CustomAppLoaderConstants.Extensions.REQUEST_PAPER_RECORD_OVERALL_ACTION))).isDisplayed();
    }

    public DeathCertificateFormPage goToEnterDeathCertificateForm() {
        clickOn(deathCertificate);
        return new DeathCertificateFormPage(driver);
    }


    public boolean isDead() {
        try {
            driver.findElement(By.className("death-message"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }


}
