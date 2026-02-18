package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class MexicoLoginPage extends LoginPage {

    public MexicoLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "es";
    }

    @Override
    public String getDefaultLocationName() {
        return "CES Oficina";
    }
}
