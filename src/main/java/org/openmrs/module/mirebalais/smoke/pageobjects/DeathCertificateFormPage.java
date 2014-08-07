package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeathCertificateFormPage extends AbstractPageObject {

    public DeathCertificateFormPage(WebDriver driver) {
        super(driver);
    }

    public void cancel() {
        driver.findElement(By.cssSelector("#buttons .cancel")).click();
    }
}
