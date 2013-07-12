package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class HeaderTest extends BasicMirebalaisSmokeTest {
	
	private WebDriverWait wait5Second = new WebDriverWait(driver, 5);

	@Test
	public void shouldChangeLocation() throws Exception {
		login();
		
		HeaderPage headerPage = new HeaderPage(driver);
		
		headerPage.changeLocationTo(headerPage.fourthLocation());
		
		wait5Second.until(textToBePresentInElement(headerPage.location(), headerPage.fourthLocationText()));
	}
}
