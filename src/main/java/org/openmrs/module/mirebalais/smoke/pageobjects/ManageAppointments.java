package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageAppointments {

    private WebDriver driver;

    public ManageAppointments(WebDriver driver) {
        this.driver = driver;
    }

    public void enterPatientIdentifier(String patientID) {
        WebElement searchField = driver.findElement(By.id("patient-search"));
        searchField.sendKeys(patientID);
        searchField.sendKeys(Keys.ARROW_DOWN);
        searchField.sendKeys(Keys.RETURN);
    }

    public boolean isSelectServiceTypeDefined() {
        return (driver.findElement(By.id("selectAppointmentType")) != null) ? true : false;
    }

    public boolean isDateRangePickerDefined() {
        return (driver.findElement(By.tagName("daterangepicker")) != null) ? true : false;
    }

}
