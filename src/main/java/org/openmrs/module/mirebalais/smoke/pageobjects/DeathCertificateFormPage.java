package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeathCertificateFormPage extends AbstractPageObject {

    public static final String FORM_HEADING = "Certificat / Constat de deces";

    public DeathCertificateFormPage(WebDriver driver) {
        super(driver);
    }

    public void waitToLoad() {
        WebDriverWait wait5seconds = new WebDriverWait(driver, 5);
        wait5seconds.until(ExpectedConditions.textToBePresentInElement(By.cssSelector("htmlform h2 label"), FORM_HEADING));
    }

    public void cancel() {
        driver.findElement(By.cssSelector("#buttons .cancel")).click();
    }
}
