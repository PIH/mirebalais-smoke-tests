package org.openmrs.module.mirebalais.smoke;

import org.junit.After;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;

public abstract class DbTest extends BasicMirebalaisSmokeTest {
	
	@After
	public void deleteData() throws Exception {
		try {
			PatientDatabaseHandler.deleteAllTestPatients();
			PatientDatabaseHandler.deleteTestUser();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("tear down failed", e);
		}
	}
}
