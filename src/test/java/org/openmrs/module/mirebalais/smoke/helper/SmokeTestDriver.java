package org.openmrs.module.mirebalais.smoke.helper;

import org.apache.commons.lang.SystemUtils;
import org.openmrs.module.mirebalais.smoke.BasicMirebalaisSmokeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SmokeTestDriver {
    private final WebDriver driver;

    public SmokeTestDriver() {
        setupChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("no-sandbox", "user-data-dir"));



        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(SmokeTestProperties.IMPLICIT_WAIT_TIME, SECONDS);
        driver.get(new SmokeTestProperties().getWebAppUrl());
    }

    private static void setupChromeDriver() {
        URL resource = null;
        ClassLoader classLoader = BasicMirebalaisSmokeTest.class.getClassLoader();

        if(SystemUtils.IS_OS_MAC_OSX) {
            resource = classLoader.getResource("chromedriver/mac/chromedriver");
        } else if(SystemUtils.IS_OS_LINUX) {
            resource = classLoader.getResource("chromedriver/linux/chromedriver");
        } else if(SystemUtils.IS_OS_WINDOWS) {
            resource = classLoader.getResource("chromedriver/windows/chromedriver.exe");
        }
        System.setProperty("webdriver.chrome.driver", resource.getPath());
    }

    public WebDriver getDriver() {
        return driver;
    }
}
