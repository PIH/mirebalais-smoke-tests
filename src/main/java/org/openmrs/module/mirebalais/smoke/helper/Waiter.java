package org.openmrs.module.mirebalais.smoke.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiter {

	public static void waitForElementToDisplay(final By element, int seconds, WebDriver driver) {
		Wait<WebDriver> wait = new WebDriverWait(driver, seconds);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(element).isDisplayed();
			}
		});
	}
	
}
