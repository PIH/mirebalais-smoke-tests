package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RequestAppointmentPage extends AbstractPageObject {

    public RequestAppointmentPage(WebDriver driver) {
        super(driver);
    }

    public void requestAppointment() throws InterruptedException {

        WebElement appointmentTypeField = driver.findElement(By.id("appointment-type"));
        appointmentTypeField.sendKeys("klinik");
        wait5seconds.until(visibilityOfElementLocated(By.tagName("strong")));
        appointmentTypeField.sendKeys(Keys.RETURN);

        WebElement providerField = driver.findElement(By.id("provider"));
        providerField.sendKeys("unknown");
        wait5seconds.until(visibilityOfElementLocated(By.tagName("strong")));
        providerField.sendKeys(Keys.RETURN);

        setTextToField("min-time-frame-value", "2");
        selectFromDropdown("min-time-frame-units", 1);
        setTextToField("max-time-frame-value", "4");
        selectFromDropdown("max-time-frame-units", 2);
        setTextToField("notes","some notes");

        clickOn(By.id("save-button"));
    }

}
