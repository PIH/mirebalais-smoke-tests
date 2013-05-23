package org.openmrs.module.mirebalais.smoke.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Toast {

	public static void closeToast(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return jQuery('.toast-item').hide();");
	}
	
}
