package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openmrs.module.mirebalais.smoke.pageobjects.loginpages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SierraLeoneLoginPage extends LoginPage {

    public SierraLeoneLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getLocale() {
        return "en";
    }

    @Override
    public void logIn(String user, String password, String location) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id='sessionLocation']/li[14]")).click();  // 14th element in list, should be "Triage | KGH"
        driver.findElement(By.id("login-button")).click();
    }
}
