package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChiefComplaintForm extends BaseHtmlForm {

    public ChiefComplaintForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo(String chiefComplaintText) {
        WebElement history = driver.findElement(By.cssSelector("#chief-complaint textarea"));
        history.sendKeys(chiefComplaintText);
        confirmData();
    }

}
