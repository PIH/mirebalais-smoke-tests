package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HSNLoginPage  extends LoginPage {

    public HSNLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "ht";
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        termsAndConditionsPage.acceptTermsIfPresent();
        driver.findElement(By.xpath("//*[contains(text(), '" + (StringUtils.isBlank(location) ? "Klinik MNT" : location) + "')]")).click();
        appDashboard.cancelTwoFactorPopupIfPresent();
    }
}

