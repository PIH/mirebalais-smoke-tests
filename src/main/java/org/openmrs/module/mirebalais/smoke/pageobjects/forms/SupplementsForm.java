package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SupplementsForm extends BaseHtmlForm {

    public SupplementsForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo() throws Exception {
        clickOn(By.cssSelector("#vitamin-a input"));
        confirmData();
    }
}
