package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SupplementsForm extends BaseHtmlForm {

    public SupplementsForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo() throws Exception {
        wait15seconds.until(visibilityOfElementLocated(By.cssSelector("#vitamin-a input")));
        clickOn(By.cssSelector("#vitamin-a input"));
        confirmData();
    }
}
