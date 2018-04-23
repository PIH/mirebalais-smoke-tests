package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EDTriageEditPatientPage {

    private WebDriver driver;

    public EDTriageEditPatientPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterChiefComplaintAndSave(String complaint) {
        // find element with id "complaint" and enter input
        driver.findElement(By.id("complaint")).sendKeys(complaint);

        // find button with label "Save" and click it to save
        driver.findElement(By.xpath("//button[@ng-click='confirmSave()']")).click();

        // click "Confirm"
        driver.findElement(By.xpath("//button[@class='confirm']")).click();
    }
}
