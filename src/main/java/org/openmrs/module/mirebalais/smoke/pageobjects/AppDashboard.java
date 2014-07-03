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

import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigInteger;

public class AppDashboard extends AbstractPageObject {

	public static final String ACTIVE_VISITS = "org-openmrs-module-mirebalais-activeVisitsHomepageLink-app";
    public static final String ARCHIVES_ROOM = "paperrecord-archivesRoom-app";
    public static final String EDIT_PATIENT = "patientRegistration-lookup-app";
    public static final String PATIENT_REGISTRATION = "patientRegistration-registration-app";
    public static final String START_HOSPITAL_VISIT = "patientRegistration-emergencyCheckin-app";
    public static final String START_CLINIC_VISIT = "mirebalais-liveCheckin-app";
    public static final String SYSTEM_ADMINISTRATION = "emr-systemAdministration-app";
    public static final String CAPTURE_VITALS = "mirebalais-outpatientVitals-app";
    public static final String REPORTS = "reportingui-reports-homepagelink-app";
    public static final String LEGACY = "legacy-admin-app";
    public static final String MASTER_PATIENT_INDEX = "mirebalais-mpi-app";
    public static final String IN_PATIENT = "mirebalaisreports-inpatients-app";
    public static final String MY_ACCOUNT = "emr-myAccount-app";
    public static final String MANAGE_APPOINTMENTS = "appointmentschedulingui-schedulingAppointmentAppLink-app";
    public static final String MANAGE_APPOINTMENT_TYPES = "appointmentschedulingui-manageAppointmentTypes-app";
    public static final String DAILY_APPOINTMENTS = "appointmentschedulingui-scheduledAppointments-homepageLink-app";
    public static final String AWAITING_ADMISSION = "org-openmrs-module-mirebalais-awaitingAdmissionHomepageLink-app";

    public static final By SEARCH_FIELD = By.id("patient-search");
    public static final By SEARCH_RESULTS_TABLE = By.id("patient-search-results-table");


    public AppDashboard(WebDriver driver) {
        super(driver);
    }

    public void openActiveVisitsApp() {
        openApp(ACTIVE_VISITS);
	}

    public void openManageAppointmentsApp() {
        openApp(MANAGE_APPOINTMENTS);
    }

    public void openMyAccountApp() {
        openApp(MY_ACCOUNT);
	}

    public void openInPatientApp() {
        openApp(IN_PATIENT);
	}

	public void openArchivesRoomApp() {
        openApp(ARCHIVES_ROOM);
	}

	public void openPatientRegistrationApp() {
        openApp(PATIENT_REGISTRATION);
	}

    public void openStartHospitalVisitApp() {
        openApp(START_HOSPITAL_VISIT);
    }

    public void openSysAdminApp() {
        openApp(SYSTEM_ADMINISTRATION);
	}

    public void openCaptureVitalsApp() {
        openApp(CAPTURE_VITALS);
    }

    public void openReportApp() {
    	 openApp(REPORTS);
	}

    public void openManageAppointmentTypesApp() {
        openApp(MANAGE_APPOINTMENT_TYPES);
    }

    public void openAwaitingAdmissionApp() {
        openApp(AWAITING_ADMISSION);
    }

    public void startClinicVisit() {
		openApp(START_CLINIC_VISIT);
	}

    public void openMasterPatientIndexApp() {
    	openApp(MASTER_PATIENT_INDEX);
	}

    public void openCheckinApp() {
        openApp("mirebalais-liveCheckin-app");
    }

    public void openDailyAppointments() {
        openApp(DAILY_APPOINTMENTS);
    }

    public boolean isPatientRegistrationAppPresented() {
		return isAppButtonPresent(PATIENT_REGISTRATION);
	}

    public boolean isArchivesRoomAppPresented() {
        return isAppButtonPresent(ARCHIVES_ROOM);
    }

	public boolean isSystemAdministrationAppPresented() {
        return isAppButtonPresent(SYSTEM_ADMINISTRATION);
    }

    public boolean isActiveVisitsAppPresented() {
		return isAppButtonPresent(ACTIVE_VISITS);
	}

	public boolean isCaptureVitalsAppPresented() {
		return isAppButtonPresent(CAPTURE_VITALS);
	}

	public boolean isReportsAppPresented() {
		return isAppButtonPresent(REPORTS);
	}

	public Boolean isStartHospitalVisitAppPresented() {
		return isAppButtonPresent(START_HOSPITAL_VISIT);
	}

	public Boolean isStartClinicVisitAppPresented() {
		return isAppButtonPresent(START_CLINIC_VISIT);
	}

	public Boolean isEditPatientAppPresented() {
		return isAppButtonPresent(EDIT_PATIENT);
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
