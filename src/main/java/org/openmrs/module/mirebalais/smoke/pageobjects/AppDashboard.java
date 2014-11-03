/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.mirebalais.smoke.pageobjects;

import org.openmrs.module.mirebalais.apploader.CustomAppLoaderConstants;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigInteger;

import static org.apache.commons.lang.StringUtils.replaceChars;

public class AppDashboard extends AbstractPageObject {

    public static final By SEARCH_FIELD = By.id("patient-search");
    public static final By SEARCH_RESULTS_TABLE = By.id("patient-search-results-table");

    public static final String LEGACY = "legacy-admin-app";
    public static final String APP_LINK_SUFFIX = "-appLink-app";

    public AppDashboard(WebDriver driver) {
        super(driver);
    }

    public void openActiveVisitsApp() {
        openApp(replaceChars(CustomAppLoaderConstants.ACTIVE_VISITS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openAppointmentSchedulingApp() {
        openApp(replaceChars(CustomAppLoaderConstants.APPOINTMENT_SCHEDULING_HOME_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openMyAccountApp() {
        openApp(replaceChars(CustomAppLoaderConstants.MY_ACCOUNT_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openInPatientApp() {
        openApp(replaceChars(CustomAppLoaderConstants.INPATIENTS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public void openArchivesRoomApp() {
        openApp(replaceChars(CustomAppLoaderConstants.ARCHIVES_ROOM_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public void openPatientRegistrationApp() {
        openApp(replaceChars(CustomAppLoaderConstants.LEGACY_PATIENT_REGISTRATION_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openStartHospitalVisitApp() {
        openApp(replaceChars(CustomAppLoaderConstants.LEGACY_PATIENT_REGISTRATION_ED_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openSysAdminApp() {
        openApp(replaceChars(CustomAppLoaderConstants.SYSTEM_ADMINISTRATION_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openCaptureVitalsApp() {
        openApp(replaceChars(CustomAppLoaderConstants.VITALS_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openReportApp() {
    	 openApp(replaceChars(CustomAppLoaderConstants.REPORTS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openAwaitingAdmissionApp() {
        openApp(replaceChars(CustomAppLoaderConstants.AWAITING_ADMISSION_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public void startClinicVisit() {
		openApp(replaceChars(CustomAppLoaderConstants.CHECK_IN_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openMasterPatientIndexApp() {
    	openApp(replaceChars(CustomAppLoaderConstants.LEGACY_MPI_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openCheckinApp() {
        openApp(replaceChars(CustomAppLoaderConstants.CHECK_IN_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public boolean isPatientRegistrationAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.LEGACY_PATIENT_REGISTRATION_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public boolean isArchivesRoomAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.ARCHIVES_ROOM_APP, ".", "-") + APP_LINK_SUFFIX);
    }

	public boolean isSystemAdministrationAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.SYSTEM_ADMINISTRATION_APP, ".", "-") + APP_LINK_SUFFIX);
    }

    public boolean isActiveVisitsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.ACTIVE_VISITS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public boolean isCaptureVitalsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.VITALS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public boolean isReportsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.REPORTS_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public Boolean isStartHospitalVisitAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.LEGACY_PATIENT_REGISTRATION_ED_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public Boolean isStartClinicVisitAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.CHECK_IN_APP, ".", "-") + APP_LINK_SUFFIX);
	}

	public Boolean isEditPatientAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.LEGACY_PATIENT_LOOKUP_APP, ".", "-") + APP_LINK_SUFFIX);
	}

    public Boolean isLegacyAppPresented() {
        return isAppButtonPresent(LEGACY);
    }

    public void findPatientByIdentifier(String identifier) {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        searchField.sendKeys(identifier);
        searchField.sendKeys(Keys.RETURN);
    }

    public void findPatientByName(Patient patient) {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        searchField.sendKeys(patient.getName()); // search on patient name

        // patient should be in results list
        WebElement searchResults = driver.findElement(SEARCH_RESULTS_TABLE);
        searchResults.findElement(By.xpath("//*[contains(text(), '" + patient.getIdentifier() + "')]")).click();

    }

    public void goToPatientPage(BigInteger patientId) {
        driver.get(properties.getWebAppUrl() + "/coreapps/patientdashboard/patientDashboard.page?patientId=" + patientId);
    }

	private void clickAppButton(String appId) {
        driver.findElement(By.id(appId)).click();
	}

    private boolean isAppButtonPresent(String appId) {
        try {
            return driver.findElement(By.id(appId)) != null;
        } catch (Exception ex) {
            return false;
        }
    }

    private void openApp(String appIdentifier) {
        driver.get(properties.getWebAppUrl());
        clickAppButton(appIdentifier);
    }

}
