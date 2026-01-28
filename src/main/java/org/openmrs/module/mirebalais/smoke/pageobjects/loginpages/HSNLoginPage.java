package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class HSNLoginPage  extends LoginPage {

    public HSNLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "ht";
    }

    @Override
    public String getDefaultLocationName() {
        return "Klinik MNT";
    }
}

