package org.openmrs.module.pihemr.smoke;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openmrs.module.pihemr.smoke.dataModel.Patient;
import org.openmrs.module.pihemr.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.pihemr.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.pihemr.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.TimeoutException;

public abstract class DbTest extends BasicSmokeTest {

    Patient adultTestPatient;

    Patient anotherAdultTestPatient;

    Patient newbornTestPatient;

    @Before
    public void setupTestData() throws Exception {
        log("Setting up test data");
        adultTestPatient = PatientDatabaseHandler.insertAdultTestPatient();
        anotherAdultTestPatient = PatientDatabaseHandler.insertAdultTestPatient();
        newbornTestPatient = PatientDatabaseHandler.insertNewbornTestPatient();
    }


    @After
	public void deleteTestData() throws Exception {
        log("Deleting test data");
		try {
			PatientDatabaseHandler.deleteAllTestPatients();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("tear down failed", e);
		}
	}

	// overrides "afterclass" defined in BaseSmokeTest
    @AfterClass
    public static void after() throws Exception {
        System.out.println("Deleting test users");
        try {
            UserDatabaseHandler.deleteAllTestUsers();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }

        if (header == null) {
            header = new HeaderPage(driver);
        }

        // back to home page
        header.home();

        // log out if necessary
        try {
            header.logOut();
        }
        catch (TimeoutException ex) {
            // do nothing, assume we are already logged out
        }

        if (createdOwnDriver) {
            driver.quit();
        }
    }

}
