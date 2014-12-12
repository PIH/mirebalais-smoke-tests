package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GeneralLoginPage extends LoginPage {

    public GeneralLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void logIn(String user, String password, int location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
}
