package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.AfterClass;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.HeaderPage;
import org.openqa.selenium.TimeoutException;

public abstract class DbTest extends BasicMirebalaisSmokeTest {
	
	@After
	public void deleteData() throws Exception {
		try {
			PatientDatabaseHandler.deleteAllTestPatients();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("tear down failed", e);
		}
	}

	// overrides "after" defined in BaseMirebalaisSmokeTest
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

        // back to home page & then make sure we reset the lucene index
        header.home();
        adminPage.updateLuceneIndex();

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
