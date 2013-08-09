package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.junit.AfterClass;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.helper.UserDatabaseHandler;

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

    @AfterClass
    public static void deleteDataAfterClass() throws Exception {
        try {
            UserDatabaseHandler.deleteAllTestUsers();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("tear down failed", e);
        }
    }

}
