package org.openmrs.module.mirebalais.smoke;

import org.hamcrest.Matchers;
import org.openmrs.module.mirebalais.smoke.DbTest;
import org.openmrs.module.mirebalais.smoke.dataModel.Visit;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public abstract class SimpleFormTest extends DbTest {

    public abstract void testForms() throws Exception;

    public void testSectionedFormLoads(String location, String formLabel) throws Exception {
        testSectionedFormLoads(location, formLabel, adultTestPatient.getId());
    }

    /**
     * Checks that every section of a multi-section form loads without an error. Does not input data or submit.
     * @param location The login location to use
     * @param formLabel The text of the action button that opens the form
     * @param patientId
     * @throws Exception
     */
    public void testSectionedFormLoads(String location, String formLabel, BigInteger patientId) throws Exception {

        logInAsPhysicianUser(location);
        appDashboard.goToClinicianFacingDashboard(patientId);
        clinicianDashboard.startVisit();

        visitNote.openForm(By.linkText(formLabel));
        int numSections = driver.findElements(By.className("visit-element")).size();
        for (int i = 0; i < numSections; i++) {
            // driver.findElements(By.xpath("(//*[contains(@class, 'visit-element')])[3]//*[contains(@class, 'edit-encounter')]")).get(1).isDisplayed();
            WebElement sectionHeader = driver.findElements(By.className("visit-element")).get(i);
            List<WebElement> editButtonCandidates = sectionHeader.findElements(By.className("edit-encounter"));
            WebElement editButton = null;
            for (WebElement candidate : editButtonCandidates) {
                if (candidate.isDisplayed()) {
                    editButton = candidate;
                }
            }
            if (editButton != null) {
                editButton.click();
                assertThat(driver.findElement(By.id("htmlform")), Matchers.notNullValue());
                driver.navigate().back();
            } else {
                fail("No edit button was visible for form section " + sectionHeader.getText());
            }
        }
    }

}
