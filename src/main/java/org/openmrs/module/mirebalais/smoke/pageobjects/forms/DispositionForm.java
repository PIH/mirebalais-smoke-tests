package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.WebDriver;

public class DispositionForm extends BaseHtmlForm {

    public DispositionForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo(String dispositionText) throws Exception {
        chooseDisposition(dispositionText);
        confirmData();
    }
}
