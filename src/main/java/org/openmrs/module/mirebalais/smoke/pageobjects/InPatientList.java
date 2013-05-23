package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.mirebalais.smoke.dataModel.Visit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InPatientList extends AbstractPageObject {

	public InPatientList(WebDriver driver) {
		super(driver);
	}

	public List<Visit> getVisits() {
		List<Visit> visits = new ArrayList<Visit>();
		List<WebElement> tds = driver.findElements(By.cssSelector("#active-visits td"));
		for (int i = 0; i < tds.size(); i+=4) {
			visits.add(new Visit(tds.get(i).getText(), 
								 tds.get(i+1).getText(), 
								 cleanDispositionPlace(tds.get(i+2).getText()), 
								 cleanDispositionPlace(tds.get(i+3).getText())));
		}
		return visits;
	}
	
	private String cleanDispositionPlace(String rawText) {
		return rawText.substring(0,rawText.indexOf('\n')).trim();
	}
}
