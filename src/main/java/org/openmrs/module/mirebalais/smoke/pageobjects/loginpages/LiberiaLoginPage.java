package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class LiberiaLoginPage extends LoginPage {

    public LiberiaLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "en";
    }

    @Override
    public String getDefaultLocationName() {
        return "Records Room";
    }
}
