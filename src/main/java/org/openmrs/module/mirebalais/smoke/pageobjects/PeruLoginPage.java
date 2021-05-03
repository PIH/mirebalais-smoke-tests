package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PeruLoginPage extends LoginPage {

    public PeruLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void logInAsAdmin() {
        this.logIn("admin", "test");
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='sessionLocation']/li[2]")).click();
        driver.findElement(By.id("login-button")).click();
    }
}
