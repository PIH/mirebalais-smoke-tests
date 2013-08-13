package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard.RADIOLOGY;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class OrdersTest extends DbTest {
	
	private static final String STUDY_1 = "Hanche - Gauche, 2 vues (Radiographie)";
	
	private static final String STUDY_2 = "Humérus - Gauche, 2 vues (Radiographie)";

    private static final String START_DATE = "01-08-2013";
    private static final String START_DATE_FIELD = "2013-08-01 00:00:00";
    private static final String END_DATE = "02-08-2013";
    private static final String END_DATE_FIELD = "2013-08-02 00:00:00";

    private By confirmButton = By.cssSelector("#retrospective-visit-creation-dialog .confirm");

    @Test
	public void orderSingleXRay() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
		initBasicPageObjects();

        login();
		
		appDashboard.goToPatientPage(testPatient.getId());
		patientDashboard.startVisit();
		
		patientDashboard.orderXRay(STUDY_1, STUDY_2);
		
		assertThat(patientDashboard.countEncountersOfType(RADIOLOGY), is(1));
	}

    @Test
    public void orderRetroSingleXRay() throws Exception {
        Patient testPatient = PatientDatabaseHandler.insertNewTestPatient();
        initBasicPageObjects();

        login();

        appDashboard.goToPatientPage(testPatient.getId());
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("visit.showRetrospectiveVisitCreationDialog();");
        jse.executeScript("document.getElementById('retrospectiveVisitStartDate-display').removeAttribute('readonly', 0);");
        jse.executeScript("document.getElementById('retrospectiveVisitStopDate-display').removeAttribute('readonly', 0);");
        driver.findElement(By.cssSelector("#retrospective-visit-creation-dialog i.icon-plus")).click();
        jse.executeScript("document.getElementById('retrospectiveVisitStartDate-display').value='" + START_DATE + "';");
        jse.executeScript("document.getElementById('retrospectiveVisitStartDate-field').value='" + START_DATE_FIELD + "';");
        jse.executeScript("document.getElementById('retrospectiveVisitStopDate-display').value='" + END_DATE + "';");
        jse.executeScript("document.getElementById('retrospectiveVisitStopDate-field').value='" + END_DATE_FIELD + "';");

        new WebDriverWait(driver, 20).until(visibilityOfElementLocated(confirmButton));
        driver.findElement(confirmButton).click();
        patientDashboard.orderXRay(STUDY_1, STUDY_2);

        assertThat(patientDashboard.countEncountersOfType(RADIOLOGY), is(1));
    }
}
