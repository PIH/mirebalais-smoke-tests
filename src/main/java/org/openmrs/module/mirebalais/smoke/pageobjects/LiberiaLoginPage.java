package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LiberiaLoginPage extends LoginPage {

    public LiberiaLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[contains(text(), '" + (StringUtils.isBlank(location) ? "Records Room" : location) + "')]")).click();
        driver.findElement(By.id("login-button")).click();
    }
}
