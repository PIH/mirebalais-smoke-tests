package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderPage extends AbstractPageObject {

	public HeaderPage(WebDriver driver) {
		super(driver);
	}

	public void logOut() {
		driver.findElement(By.className("logout")).click();
	}
	
	public String getLocation() {
		return driver.findElement(By.cssSelector("li.change-location span")).getText();
	}
	
	public void changeLocation(String location) {
		driver.findElement(By.className("icon-map-marker")).click();
    	List<WebElement> options = driver.findElements(By.cssSelector("ul.select li"));
		for (WebElement option : options) {
			System.out.println(option.getText());
	        if(option.getText().contains(location)) {
	            option.click();
	        }
	    }
	}

}
