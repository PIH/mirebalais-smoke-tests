package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public class CheckIn {

	private WebDriver driver;
	private Wait<WebDriver> wait;

	public CheckIn(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void setLocationAndChooseCheckInTask(String identifier, final String patientName) {
		driver.get("http://bamboo.pih-emr.org:8080/mirebalais");
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.linkText("Patient Registration")) != null;
			}
		});
		driver.findElement(By.linkText("Patient Registration")).click();
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.xpath("//div[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td")) != null;
			}
		});
		driver.findElement(By.xpath("//div[@id='locationDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td")).click();
		driver.findElement(By.xpath("//*[@id='taskDiv']/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td[1]")).click();
		
		driver.findElement(By.id("patientIdentifier")).sendKeys(identifier);
		driver.findElement(By.id("right-arrow-yellow")).click();
		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.id("okBtn")).isDisplayed() &&
						webDriver.findElement(By.className("confirmExistingPatientModalList")).getText().contains(patientName) ;
			}
		});
		driver.findElement(By.id("okBtn")).click();

		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("right-arrow-yellow")).isDisplayed();
			}
		});
		driver.findElement(By.id("right-arrow-yellow")).click();
		
		driver.findElement(By.id("right-arrow-yellow")).click();
		driver.findElement(By.xpath("//*[@id='visitReasonStatus121']")).click();
		driver.findElement(By.id("right-arrow-yellow")).click();
		driver.findElement(By.xpath("//*[@id='paymentAmountStatus50']")).click();
		driver.findElement(By.id("receiptInput")).sendKeys("123456789");
		driver.findElement(By.id("right-arrow-yellow")).click();
		
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return 	webDriver.findElement(By.id("checkmark-yellow")).isDisplayed();
			}
		});
		driver.findElement(By.id("checkmark-yellow")).click();

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				return webDriver.findElement(By.id("okDialog")).isDisplayed();
			}
		});
		driver.findElement(By.id("okDialog")).click();
	}

}
