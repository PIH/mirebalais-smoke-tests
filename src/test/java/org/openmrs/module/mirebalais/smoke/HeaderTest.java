package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class HeaderTest extends BasicSmokeTest {

	private final WebDriverWait wait15Seconds = new WebDriverWait(driver, 15);

	@Test
	public void shouldChangeLocation() throws Exception {
		login();
		HeaderPage headerPage = new HeaderPage(driver);
		WebElement header = driver.findElement(headerPage.location());
        String locationName = headerPage.changeLocationTo(4);
		wait15Seconds.until(textToBePresentInElement(header, locationName));
	}
}
