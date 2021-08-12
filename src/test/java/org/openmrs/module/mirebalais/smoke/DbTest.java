package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.TimeoutException;

public abstract class DbTest extends BasicSmokeTest {

    Patient adultTestPatient;

    Patient anotherAdultTestPatient;

    Patient newbornTestPatient;

    @Before
    public void setupTestData() throws Exception {
        adultTestPatient = PatientDatabaseHandler.insertAdultTestPatient();
        anotherAdultTestPatient = PatientDatabaseHandler.insertAdultTestPatient();
        newbornTestPatient = PatientDatabaseHandler.insertNewbornTestPatient();
        home();
        logInAsAdmin();
        adminPage.updateLuceneIndex();
        logout();
    }


    @After
	public void deleteTestData() throws Exception {

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
    public static void after() throws Exception{

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

        // hack to reset the lucene index at the end of tests... fail quietly
        try {
            header.logOut();
            logInAsAdmin();
            adminPage.updateLuceneIndex();
        }
        catch (Exception e) {

        }

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
