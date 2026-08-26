package org.openmrs.module.pihemr.smoke.pageobjects.forms;

import org.openqa.selenium.WebDriver;

public class DiagnosisForm extends BaseHtmlForm {

    public DiagnosisForm(WebDriver driver) {
        super(driver);
    }


    public void fillFormWithBasicInfo(String diagnosis) {
        choosePrimaryDiagnosis(diagnosis);
        confirmData();
    }

}
