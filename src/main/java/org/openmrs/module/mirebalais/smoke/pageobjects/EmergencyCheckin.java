package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmergencyCheckin extends AbstractPageObject{

    public EmergencyCheckin(WebDriver driver) {
        super(driver);
    }

    @Override
    public void initialize() {
        login("admin", "Admin123");
        gotoPage("module/patientregistration/workflow/selectLocationAndService.form");
        driver.findElement(By.xpath("//*[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[1]/td[1]")).click();
        driver.findElement(By.xpath("//*[@id='taskDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td[1]")).click();
        driver.findElement(By.id("registerJdBtn")).click();
    }

    public void checkinMaleUnindentifiedPatient() {
        driver.findElement(By.id("rdioM")).click();
        driver.findElement(By.id("right-arrow-yellow")).click();
    }
}
