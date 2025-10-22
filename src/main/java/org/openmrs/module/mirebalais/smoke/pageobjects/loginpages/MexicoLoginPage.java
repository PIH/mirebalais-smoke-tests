package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MexicoLoginPage extends LoginPage {

    public MexicoLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getLocale() {
        return "es";
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.xpath("//*[contains(text(), '" + (StringUtils.isBlank(location) ? "CES Oficina" : location) + "')]")).click();
    }
}
