package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EDTriageEditPatientPage {

    private WebDriver driver;

    public EDTriageEditPatientPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterChiefComplaintAndSave(String complaint) {
        String buttonXpath;
        WebDriverWait wait2;

        // find element with id "complaint" and enter input
        driver.findElement(By.id("complaint")).sendKeys(complaint);

        // find button with label "Save" and click it to save
        buttonXpath = "//button[@ng-click='confirmSave()']";
        wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXpath)));
        driver.findElement(By.xpath(buttonXpath)).click();

        // click "Confirm"
        buttonXpath = "//button[@class='confirm']";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXpath)));
        driver.findElement(By.xpath(buttonXpath)).click();
    }
}
