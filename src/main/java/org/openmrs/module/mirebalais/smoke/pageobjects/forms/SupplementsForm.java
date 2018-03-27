package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SupplementsForm extends BaseHtmlForm {

    public SupplementsForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo() throws Exception {
        try {
            wait15seconds.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#vitamin-a input")));
        }
        catch (Exception e) {
            wait15seconds.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#vitamin-a input")));
        }
        clickOn(By.cssSelector("#vitamin-a input"));
        confirmData();
    }
}
