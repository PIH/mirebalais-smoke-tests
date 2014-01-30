package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SysAdminPage extends AbstractPageObject {

	
	public SysAdminPage(WebDriver driver) {
		super(driver);
	}

	public void openManageAccounts() {
		driver.findElement(By.cssSelector(".task .icon-book")).click();
	}
	
	public void openManagePatientRecords() {
		driver.findElement(By.cssSelector(".task .icon-group")).click();
	}

    public void openManageServiceTypes(){
        driver.findElement(By.cssSelector(".task .icon-calendar")).click();
    }
	
}
