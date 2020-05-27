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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReportsHomePage extends AbstractPageObject {

	public static final String BASIC_STATISTICS_REPORT = "reportingui-reports-homepagelink-app";
    public static final String NONCODED_DIAGNOSES_REPORT = "mirebalaisreports-nonCodedDiagnosesReport-link";

    public ReportsHomePage(WebDriver driver) {
        super(driver);
    }

    public void openBasicStatisticsReport() {
        clickOn(By.id(BASIC_STATISTICS_REPORT));
	}

    public void openNonCodedDiagnosesReport(String diagnosis) {
        clickOn(By.id(NONCODED_DIAGNOSES_REPORT));
        setTextToField("nonCodedField-display", diagnosis);  // hits RETURN
    }
}
