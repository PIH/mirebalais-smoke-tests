package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.openqa.selenium.WebDriver;

public class MentalHealthLoginPage extends LoginPage {

    public MentalHealthLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocale() {
        return "ht";
    }

    @Override
    public String getDefaultLocationName() {
        return "Cerca";
    }
}
