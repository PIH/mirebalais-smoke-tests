package org.openmrs.module.mirebalais.smoke.pageobjects;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PatientDashboard extends AbstractPageObject {

	public PatientDashboard(WebDriver driver) {
		super(driver);
	}

	public void orderXRay(String study1, String study2) {
		driver.findElement(By.linkText("Order X-Ray")).click();
		
		driver.findElement(By.name("clinicalHistory")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu neque ut mi auctor pulvinar. Mauris in orci non sem consequat posuere.");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("study-search")).sendKeys(study1);
		driver.findElement(By.linkText(study1)).click();
		
		driver.findElement(By.id("study-search")).sendKeys(study2);
		driver.findElement(By.linkText(study2)).click();
		
		driver.findElement(By.id("next")).click();
	}
	
	public void orderUltrassound() {
		driver.findElement(By.linkText("Order Ultrasound")).click();
	}

	public void orderCTScan() {
		driver.findElement(By.linkText("Order CT Scan")).click();
	}
	
	// TODO: add more data to compare?
	public boolean hasOrder(String orderDetails) {
		return driver.findElement(By.id("content")).getText().contains(orderDetails);
	}

	public Set<String> getIdentifiers() {
		String temp = driver.findElement(By.className("identifiers")).getText().trim();
		StringTokenizer st = new StringTokenizer(temp.substring(3), ",");
		Set<String> result = new HashSet<String>();
		while(st.hasMoreTokens()) {
			result.add(st.nextToken());
		}
		return result;
	}
	
}