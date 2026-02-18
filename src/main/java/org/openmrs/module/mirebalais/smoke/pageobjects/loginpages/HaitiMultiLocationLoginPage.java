package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class HaitiMultiLocationLoginPage extends LoginPage {

    public HaitiMultiLocationLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "ht";
    }

    @Override
    public String getDefaultLocationName() {
        return "Klinik Ekstèn Jeneral";
    }
}

