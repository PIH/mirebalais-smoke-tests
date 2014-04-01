package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SysAdminPage extends AbstractPageObject {


    public static final String MANAGE_ACCOUNTS = "emr-account-manageAccounts-app";

    public static final String MERGE_PATIENT_RECORDS = "emr-mergePatients-app";

    public static final String MANAGE_APPOINTMENT_TYPES = "appointmentschedulingui-manageAppointmentTypes-systemAdminLink-app";

	public SysAdminPage(WebDriver driver) {
		super(driver);
	}

	public void openManageAccounts() {
		openApp(MANAGE_ACCOUNTS);
	}
	
	public void openManagePatientRecords() {
        openApp(MERGE_PATIENT_RECORDS);
	}

    public void openManageServiceTypes(){
        openApp(MANAGE_APPOINTMENT_TYPES);
    }

    public void openApp(String appId) {
        driver.findElement(By.id(appId)).click();
    }
	
}
