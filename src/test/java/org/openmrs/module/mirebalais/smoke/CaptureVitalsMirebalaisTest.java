package org.openmrs.module.mirebalais.smoke;

import org.openmrs.module.pihcore.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VitalsApp;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class CaptureVitalsMirebalaisTest extends CaptureVitalsTest {

	@Override
	protected String getVitalsAppIdentifier() {
		return replaceChars(CustomAppLoaderConstants.Apps.UHM_VITALS, ".", "-") + AppDashboard.APP_LINK_SUFFIX;
	}

	@Override
	protected void findPatient(String identifier) {
		vitals.enterPatientIdentifier(identifier);
		vitals.confirmPatient();
	}

	@Override
	protected boolean isVitalsHomePageDisplayed() {
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id(VitalsApp.SEARCH_PATIENT_FIELD_ID)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected Boolean getPaperRecordEnabled() { return true; }

}
