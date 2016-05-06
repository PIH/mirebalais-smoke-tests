package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FeedingForm extends BaseHtmlForm {

    public FeedingForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo() throws Exception {
        clickOn(By.cssSelector("#breastfeeding-yes input"));
        confirmData();
    }
}
