package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExamForm extends BaseHtmlForm {

    public ExamForm(WebDriver driver) {
        super(driver);
    }

    public void fillFormWithBasicInfo(String comments) {
        WebElement history = driver.findElement(By.cssSelector("#general-exam-comments textarea"));
        history.sendKeys(comments);

        // just tab to the end of the form
        int i = 0;
        while (i < 50) {
            hitTabKey();
            i++;
        }

        clickOn(By.cssSelector("#confirmation .confirm"));
    }

}
