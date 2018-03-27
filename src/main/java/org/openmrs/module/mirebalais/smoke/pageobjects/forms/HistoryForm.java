package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HistoryForm extends BaseHtmlForm {

    public HistoryForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo(String historyText) {
        wait5seconds.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#presenting-history textarea")));
        WebElement history = driver.findElement(By.cssSelector("#presenting-history textarea"));
        history.sendKeys(historyText);
        confirmData();
    }
}


