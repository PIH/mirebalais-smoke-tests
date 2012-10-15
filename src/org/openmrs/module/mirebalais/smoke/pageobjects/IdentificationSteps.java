package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class IdentificationSteps {

	private WebDriver driver;
	private Wait<WebDriver> wait;
    
	public IdentificationSteps(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
	}
	
	public void setLocationAndChooseRegisterTask() {
		driver.findElement(By.linkText("Patient Registration")).click();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.xpath("//div[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td")) != null;
			}
		});
		driver.findElement(By.xpath("//div[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td")).click();
		driver.findElement(By.cssSelector("td.taskListItem")).click();
		
		
	}
}
