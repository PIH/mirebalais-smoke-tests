package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PlanForm extends BaseHtmlForm {

    public PlanForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo(String planText) {
        WebElement history = driver.findElement(By.cssSelector("#clinical-management-plan textarea"));
        history.sendKeys(planText);
        confirmData();
    }
}
