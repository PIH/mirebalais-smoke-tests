package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.BasicReportData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasicReportPage extends AbstractPageObject{

	
	public BasicReportPage(WebDriver driver) {
		super(driver);
	}

	public BasicReportData getData() {
		String openVisits = driver.findElements(By.cssSelector(".reportBox .data")).get(0).getText();
		String registrationToday = driver.findElements(By.cssSelector(".reportBox .data")).get(1).getText();
		
		return new BasicReportData(new Integer(openVisits), new Integer(registrationToday));
	}

}
