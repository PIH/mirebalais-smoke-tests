package org.openmrs.module.mirebalais.smoke.pageobjects.loginpages;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.mirebalais.smoke.dataModel.User;
import org.openmrs.module.mirebalais.smoke.helper.SmokeTestProperties;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.TermsAndConditionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class LoginPage {

	protected WebDriver driver;

	protected static SecretQuestionLoginPage secretQuestionLoginPage;
	protected static TermsAndConditionsPage termsAndConditionsPage;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		termsAndConditionsPage = new TermsAndConditionsPage(driver);
		secretQuestionLoginPage = new SecretQuestionLoginPage(driver);
	}

	public void logIn(String user, String password, String location) {
		driver.findElement(By.id("username")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("login-button")).click();
		secretQuestionLoginPage.enterSecretQuestion(password);
		termsAndConditionsPage.acceptTermsIfPresent();
		selectFacilityIfNeeded();
		location = (StringUtils.isBlank(location) ? getDefaultLocationName() : location);
		driver.findElement(By.xpath("//*[contains(text(), '" + location + "')]")).click();
	}

	// some servers show a facility-selection step (a "visit-location-select" list) that must be
	// clicked before the location-selection section becomes visible; no-op unless overridden
	protected void selectFacilityIfNeeded() {
	}

	public abstract String getLocale();

	public abstract String getDefaultLocationName();

	public void logIn(String user, String password) {
		logIn(user, password, null);
	}

	public void logInAsAdmin() {
		this.logIn("admin", new SmokeTestProperties().getAdminUserPassword());
	}

    public void logInAsAdmin(String location) {
        this.logIn("admin", new SmokeTestProperties().getAdminUserPassword(), location);
    }

	public void logInAsPhysicianUser() throws Exception {
		User clinical = UserDatabaseHandler.insertNewPhysicianUser(getLocale());
		this.logIn(clinical.getUsername(), "Admin123");
	}

    public void logInAsPhysicianUser(String location) throws Exception {
        User clinical = UserDatabaseHandler.insertNewPhysicianUser(getLocale());
        this.logIn(clinical.getUsername(), "Admin123", location);
    }

	public void logInAsPharmacyManagerUser() throws Exception {
		User pharmacist = UserDatabaseHandler.insertNewPharmacyManagerUser(getLocale());
		this.logIn(pharmacist.getUsername(), "Admin123", "Klinik Ekstèn Famasi");
	}

    public void logInAsArchivistUser() throws Exception{
        User archivist = UserDatabaseHandler.insertNewArchivistUser(getLocale());
        this.logIn(archivist.getUsername(), "Admin123");
    }

}
