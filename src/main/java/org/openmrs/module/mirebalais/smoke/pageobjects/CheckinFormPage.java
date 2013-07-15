package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.RETURN;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CheckinFormPage extends AbstractPageObject {
    public CheckinFormPage(WebDriver driver) {
		super(driver);
	}

	public void enterInfo() {
        selectFirstOptionFor("typeOfVisit");
        selectFirstOptionFor("paymentAmount");

        WebElement confirmButton = driver.findElement(By.id("confirmationQuestion")).findElement(By.className("confirm"));
        confirmButton.click();

        new WebDriverWait(driver, 10).until(stalenessOf(confirmButton));
    }

    private void selectFirstOptionFor(String spanId) {
        findSelectInsideSpan(spanId).sendKeys(ARROW_DOWN, RETURN);
    }

    private WebElement findSelectInsideSpan(String spanId) {
        return driver.findElement(By.id(spanId)).findElement(By.tagName("select"));
    }
	
}