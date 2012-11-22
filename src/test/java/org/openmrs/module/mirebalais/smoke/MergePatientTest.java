package org.openmrs.module.mirebalais.smoke;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dao.PatientDao;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.MergeFlow;
import org.openmrs.module.mirebalais.smoke.pageobjects.SysAdminPage;

public class MergePatientTest extends BasicMirebalaisSmokeTest {

	private static LoginPage loginPage;
	private AppDashboard appDashboard;
	private SysAdminPage sysAdminPage;
	private MergeFlow mergeFlow;
	private PatientDao patientDao;

	private String patientOneId;
	private String patientTwoId;
	
	@Override
    public void specificSetUp() {
		loginPage = new LoginPage(driver);
		appDashboard = new AppDashboard(driver);
		sysAdminPage = new SysAdminPage(driver);
		mergeFlow = new MergeFlow(driver);
		patientDao = new PatientDao();

		loginPage.logIn("admin", "Admin123");
		patientOneId = "TT2346";
		patientTwoId = "TT6432";
		
		patientDao.insertPatient("João da Silva", patientOneId);
		patientDao.insertPatient("João da Silva", patientTwoId);
	}
	
	@Test
	public void mergePatientsById() {
		appDashboard.openSysAdminApp();
		sysAdminPage.openManagePatientRecords();
		
		mergeFlow.setPatientsToMerge(patientOneId, patientTwoId);
		Set<Integer> patientIds = patientDao.getPatientId(patientOneId);
		assertTrue(patientIds.contains(patientTwoId));
	}

	@After
	public void tearDown() {
		patientDao.deletePatient(patientOneId);
		// precisa deletar o segundo? patientDao.deletePatient(patientTwoId);
	}

}
