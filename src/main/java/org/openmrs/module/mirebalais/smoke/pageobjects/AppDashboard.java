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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigInteger;

import static org.apache.commons.lang.StringUtils.replaceChars;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AppDashboard extends AbstractPageObject {

    public static final By SEARCH_FIELD = By.id("patient-search");
    public static final By SEARCH_RESULTS_TABLE = By.id("patient-search-results-table");

    public static final String LEGACY = "legacy-admin-app";
    public static final String APP_LINK_SUFFIX = "-appLink-app";

    public AppDashboard(WebDriver driver) {
        super(driver);
    }

    public void goToAppDashboard() {
        clickOn(By.className("logo"));
    }

    public void openActiveVisitsApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.ACTIVE_VISITS_LIST, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openAppointmentSchedulingApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.APPOINTMENT_SCHEDULING_HOME, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openMyAccountApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.MY_ACCOUNT, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openInPatientApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.INPATIENTS, ".", "-") + APP_LINK_SUFFIX);
	}

	public void openArchivesRoomApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.ARCHIVES_ROOM, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openPatientRegistrationApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.PATIENT_REGISTRATION, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openSysAdminApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.SYSTEM_ADMINISTRATION, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openReportApp() {
    	 openApp(replaceChars(CustomAppLoaderConstants.Apps.REPORTS, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openAwaitingAdmissionApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.AWAITING_ADMISSION, ".", "-") + APP_LINK_SUFFIX);
    }

    public void startClinicVisit() {
		openApp(replaceChars(CustomAppLoaderConstants.Apps.CHECK_IN, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openMasterPatientIndexApp() {
    	openApp(replaceChars(CustomAppLoaderConstants.Apps.LEGACY_MPI, ".", "-") + APP_LINK_SUFFIX);
	}

    public void openCheckinApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.CHECK_IN, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openWaitingForConsultApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.WAITING_FOR_CONSULT, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openEDTriageApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.ED_TRIAGE, ".", "-") + APP_LINK_SUFFIX);
    }

    public void openEDTriageQueueApp() {
        openApp(replaceChars(CustomAppLoaderConstants.Apps.ED_TRIAGE_QUEUE, ".", "-") + APP_LINK_SUFFIX);
    }

    public boolean isPatientRegistrationAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.PATIENT_REGISTRATION, ".", "-") + APP_LINK_SUFFIX);
	}

    public boolean isArchivesRoomAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.ARCHIVES_ROOM, ".", "-") + APP_LINK_SUFFIX);
    }

	public boolean isSystemAdministrationAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.SYSTEM_ADMINISTRATION, ".", "-") + APP_LINK_SUFFIX);
    }

    public boolean isActiveVisitsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.ACTIVE_VISITS_LIST, ".", "-") + APP_LINK_SUFFIX);
	}

	public boolean isUhmCaptureVitalsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.UHM_VITALS, ".", "-") + APP_LINK_SUFFIX);
	}

	public boolean isReportsAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.REPORTS, ".", "-") + APP_LINK_SUFFIX);
	}

	public Boolean isStartClinicVisitAppPresented() {
		return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.CHECK_IN, ".", "-") + APP_LINK_SUFFIX);
	}

    public Boolean isAwaitingAdmissionAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.AWAITING_ADMISSION, ".", "-") + APP_LINK_SUFFIX);
    }

    public Boolean isInpatientsAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.INPATIENTS, ".", "-") + APP_LINK_SUFFIX);
    }

    public Boolean isSchedulingAppPresented() {
        return isAppButtonPresent(replaceChars(CustomAppLoaderConstants.Apps.APPOINTMENT_SCHEDULING_HOME, ".", "-") + APP_LINK_SUFFIX);
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
        searchField.sendKeys(Keys.RETURN);

        // patient should be in results list
        WebElement searchResults = driver.findElement(SEARCH_RESULTS_TABLE);
        searchResults.findElement(By.xpath("//*[contains(text(), '" + patient.getIdentifier() + "')]")).click();
    }


    public void findPatientByGivenAndFamilyName(String givenName, String familyName) {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        searchField.sendKeys(givenName + " " + familyName);
        searchField.sendKeys(Keys.RETURN);

        // patient should be in results list
        WebElement searchResults = driver.findElement(SEARCH_RESULTS_TABLE);
        searchResults.findElement(By.xpath("//*[contains(text(), '" + familyName + ", "
                + givenName + "')]")).click();
    }

    public void goToVisitNoteVisitList(BigInteger patientId) {
        driver.get(properties.getWebAppUrl() + "/pihcore/visit/visit.page?patient=" + patientId + "#/visitList");
        wait15seconds.until(visibilityOfElementLocated(By.id("visit-list")));
    }


    public void goToVisitNoteVisitListAndSelectFirstVisit(BigInteger patientId) {
        goToVisitNoteVisitList(patientId);

        // hack: if there is a stale element exception, just try to fetch again; see if this helps at all
        // http://docs.seleniumhq.org/exceptions/stale_element_reference.jsp
        try {
            driver.findElements(By.className("list-element")).get(0).click();
        }
        catch (StaleElementReferenceException e) {
            driver.findElements(By.className("list-element")).get(0).click();
        }
    }

    public void goToClinicianFacingDashboard(BigInteger patientId) {
        driver.get(properties.getWebAppUrl() + "/coreapps/clinicianfacing/patient.page?patientId=" + patientId);
    }

    public void openApp(String appIdentifier) {
        new HeaderPage(driver).home();
        clickAppButton(appIdentifier);
    }

  /*  public void goToPatientDashboard(BigInteger patientId) {
        driver.get(properties.getWebAppUrl() + "/coreapps/patientdashboard/patientDashboard.page?patientId=" + patientId);
    }*/

	private void clickAppButton(String appId) {
        wait15seconds.until(visibilityOfElementLocated(By.id(appId)));
        driver.findElement(By.id(appId)).click();
	}

    private boolean isAppButtonPresent(String appId) {
        try {
            return driver.findElement(By.id(appId)) != null;
        } catch (Exception ex) {
            return false;
        }
    }

}
