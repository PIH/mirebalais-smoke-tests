package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class PeruLoginPage extends LoginPage {

    public PeruLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "es_PE";
    }

    @Override
    public String getDefaultLocationName() {
        return "Centro Medico Oscar Romero (COR)\"";
    }
}
