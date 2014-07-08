package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdmissionNoteForm extends BaseHtmlForm {

    public AdmissionNoteForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithDiagnosisAndDefaultLocation(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
    }

    public void fillFormWithDiagnosis(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        selectLocation(2);
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
    }

    public void fillFormWithBasicEncounterInfoAndDiagnosis(String primaryDiagnosis) throws Exception {
        assertThat(submitButtonIsEnabled(),is(false));
        selectProvider(2);
        selectLocation(2);
        assertThat(submitButtonIsEnabled(),is(false));
        choosePrimaryDiagnosis(primaryDiagnosis);
        assertThat(submitButtonIsEnabled(),is(true));
        confirmData();
    }

}
