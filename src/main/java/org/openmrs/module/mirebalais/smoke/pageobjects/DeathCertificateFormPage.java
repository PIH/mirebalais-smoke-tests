package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeathCertificateFormPage extends AbstractSimpleFormUiPageObject {

    public DeathCertificateFormPage(WebDriver driver) {
        super(driver);
    }

    public void waitToLoad() {
        WebDriverWait wait5seconds = new WebDriverWait(driver, 5);
        wait5seconds.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#death")));
    }

    public void cancel() {
        driver.findElement(By.cssSelector(".patient-header .demographics .name")).click();
    }
}
