package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

public class EmergencyCheckin extends AbstractPageObject {

	private Registration registration;
	
    public EmergencyCheckin(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
    }

    @Override
    public void initialize() {
        gotoPage("module/patientregistration/workflow/selectLocationAndService.form");
        driver.findElement(By.xpath("//*[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[1]/td[1]")).click();
        driver.findElement(By.xpath("//*[@id='taskDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td[1]")).click();
        registration = new Registration(driver, wait);
    }

    public void checkinMaleUnindentifiedPatient() {
        driver.findElement(By.id("registerJdBtn")).click();

        registration.enterSexData();
    }


}
