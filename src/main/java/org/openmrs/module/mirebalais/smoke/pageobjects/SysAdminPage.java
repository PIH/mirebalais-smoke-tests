package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class SysAdminPage extends AbstractPageObject {

    public static final String SYSTEM_ADMINISTRATION_APP_LINK_SUFFIX = "-systemAdministration" + AppDashboard.APP_LINK_SUFFIX;
	public SysAdminPage(WebDriver driver) {
		super(driver);
	}

	public void openManageAccounts() {
		openApp(replaceChars(CustomAppLoaderConstants.Apps.MANAGE_ACCOUNTS, ".", "-") + SYSTEM_ADMINISTRATION_APP_LINK_SUFFIX);
	}
	
	public void openMergePatients() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.MERGE_PATIENTS, ".", "-") + SYSTEM_ADMINISTRATION_APP_LINK_SUFFIX);
	}

    public void openApp(String appId) {
        driver.findElement(By.id(appId)).click();
    }
	
}
