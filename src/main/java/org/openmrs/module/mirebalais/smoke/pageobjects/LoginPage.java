package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.smoke.dataModel.User;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openqa.selenium.WebDriver;

public abstract class LoginPage {
	
	protected WebDriver driver;
	
	public abstract void logIn(String user, String password, String location);

	public void logIn(String user, String password) {
		logIn(user, password, null);
	}

	public void logInAsAdmin() {
		this.logIn("admin", "Admin123");
	}

    public void logInAsAdmin(String location) {
        this.logIn("admin", "Admin123", location);
    }

	public void logInAsPhysicianUser() throws Exception {
		User clinical = UserDatabaseHandler.insertNewPhysicianUser();
		this.logIn(clinical.getUsername(), "Admin123");
	}

    public void logInAsPhysicianUser(String location) throws Exception {
        User clinical = UserDatabaseHandler.insertNewPhysicianUser();
        this.logIn(clinical.getUsername(), "Admin123", location);
    }
	
	public void logInAsPharmacyManagerUser() throws Exception {
		User pharmacist = UserDatabaseHandler.insertNewPharmacyManagerUser();
		this.logIn(pharmacist.getUsername(), "Admin123", "Klinik Exten Famasi");   // HACK: 17 = Klinik Exten Famasi
	}

    public void logInAsArchivistUser() throws Exception{
        User archivist = UserDatabaseHandler.insertNewArchivistUser();
        this.logIn(archivist.getUsername(), "Admin123");
    }

}
