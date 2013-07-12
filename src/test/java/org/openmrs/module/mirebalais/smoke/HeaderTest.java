package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class HeaderTest extends BasicMirebalaisSmokeTest {

    private WebDriverWait waitASecond = new WebDriverWait(driver, 1);

    @Test
    public void shouldChangeLocation() throws Exception {
        login();

        HeaderPage headerPage = new HeaderPage(driver);

        headerPage.changeLocationTo(headerPage.fourthLocation());

        waitASecond.until(textToBePresentInElement(headerPage.location(), headerPage.fourthLocationText()));
    }
}
