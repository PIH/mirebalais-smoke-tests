package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class PatientDashboard {

	private WebDriver driver;
	private Wait<WebDriver> wait;
	
	public PatientDashboard(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
	}
	
	public String generateDossieNumber() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("generateDossierNumber")).isDisplayed();
			}
		});
		driver.findElement(By.id("generateDossierNumber")).click();
		String result = driver.findElement(By.id("dossierNumber")).getText();
		driver.findElement(By.id("checkmark-yellow")).click();
		return result;
	}

	public String getIdentifier() {
		return driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[2]/table/tbody/tr[2]/td[1]")).getText();
	}

	public String getName() {
		return driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[2]/td[1]")).getText();
	}
	
	public String getGender() {
		return driver.findElement(By.xpath("//*[@id='overviewTable']/tbody/tr/td[1]/table/tbody/tr[4]/td[1]")).getText();
	}
}
