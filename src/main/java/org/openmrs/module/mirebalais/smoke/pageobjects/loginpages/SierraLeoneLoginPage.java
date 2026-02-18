package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class SierraLeoneLoginPage extends LoginPage {

    public SierraLeoneLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "en";
    }

    @Override
    public String getDefaultLocationName() {
        return "Triage | KGH";
    }
}
