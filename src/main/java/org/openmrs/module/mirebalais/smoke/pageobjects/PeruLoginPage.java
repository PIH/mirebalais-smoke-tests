package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PeruLoginPage extends LoginPage {

    private SmokeTestProperties properties = new SmokeTestProperties();

    public PeruLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void logInAsAdmin() {
        this.logIn("admin", properties.getAdminUserPassword());
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='sessionLocation']/li[2]")).click();
        driver.findElement(By.id("login-button")).click();
    }
}
