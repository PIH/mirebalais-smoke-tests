package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MirebalaisLoginPage extends LoginPage {

    public MirebalaisLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void logIn(String user, String password, Integer location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElements(By.cssSelector("#sessionLocation li")).get(location != null ? location : 16).click();    // 16 = Klinik Ekstèn, which we use as our default login value for Mirebalais
        driver.findElement(By.id("login-button")).click();
    }
}

