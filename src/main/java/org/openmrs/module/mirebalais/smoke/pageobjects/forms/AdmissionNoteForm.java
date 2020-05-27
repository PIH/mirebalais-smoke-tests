package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AdmissionNoteForm extends BaseHtmlForm {

    public AdmissionNoteForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithDiagnosisAndDefaultLocation(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
        // note that a wristband printer must be set for this location on the server the tests are run against
        chooseNotToPrintWristband();
    }

    public void fillFormWithDiagnosis(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        selectLocation(2);
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
        // note that a wristband printer must be set for this location on the server the tests are run against
        chooseNotToPrintWristband();
    }

    public void fillFormWithBasicEncounterInfoAndDiagnosis(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        selectProvider(2);
        selectLocation(2);
        assertThat(submitButtonIsEnabled(),is(false));
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
        // note that a wristband printer must be set for this location on the server the tests are run against
        chooseNotToPrintWristband();
    }

    public void chooseNotToPrintWristband() {
        wait5seconds.until(visibilityOfElementLocated(By.cssSelector("#print-wristband-dialog .cancel")));
        clickOn(By.cssSelector("#print-wristband-dialog .cancel"));
    }

}
