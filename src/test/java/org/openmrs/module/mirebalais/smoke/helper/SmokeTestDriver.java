package org.openmrs.module.mirebalais.smoke.helper;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.mirebalais.smoke.BasicMirebalaisSmokeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.util.Arrays;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SmokeTestDriver {

    private SmokeTestProperties properties = new SmokeTestProperties();

    private final WebDriver driver;

    public SmokeTestDriver() {
        setupChromeDriver();

        // these options allow us to run chrome as root (which we really shouldn't do) see https://bbs.archlinux.org/viewtopic.php?id=196353
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("no-sandbox"));
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(SmokeTestProperties.IMPLICIT_WAIT_TIME, SECONDS);
        driver.get(new SmokeTestProperties().getWebAppUrl());

    }

    private void setupChromeDriver() {
        URL resource = null;
        ClassLoader classLoader = BasicMirebalaisSmokeTest.class.getClassLoader();

        if (StringUtils.isBlank(properties.getChromeDriverExecutable())) {
            if(SystemUtils.IS_OS_MAC_OSX) {
                resource = classLoader.getResource("chromedriver/mac/chromedriver");
            } else if(SystemUtils.IS_OS_LINUX) {
                resource = classLoader.getResource("chromedriver/linux/chromedriver");
            } else if(SystemUtils.IS_OS_WINDOWS) {
                resource = classLoader.getResource("chromedriver/windows/chromedriver.exe");
            }
        }
        else {
            resource = classLoader.getResource(properties.getChromeDriverExecutable());
        }

        System.setProperty("webdriver.chrome.driver", resource.getPath());
    }

    public WebDriver getDriver() {
        return driver;
    }
}
