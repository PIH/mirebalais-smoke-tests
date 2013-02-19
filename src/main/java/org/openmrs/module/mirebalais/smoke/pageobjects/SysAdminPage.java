package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SysAdminPage extends AbstractPageObject {

	
	public SysAdminPage(WebDriver driver) {
		super(driver);
	}

	public void openManageAccounts() {
		driver.findElement(By.linkText("Manage Accounts")).click();
	}
	
	public void openManagePatientRecords() {
		driver.findElement(By.linkText("Merge Patient Electronic Records")).click();
	}
	
}
