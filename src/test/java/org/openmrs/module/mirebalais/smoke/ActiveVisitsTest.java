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

package org.openmrs.module.mirebalais.smoke;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.CheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openqa.selenium.By;

public class ActiveVisitsTest extends BasicMirebalaisSmokeTest{

	private CheckIn checkIn;
	private String patientName;
	
	
	@Before
    public void setUp() {
		initBasicPageObjects();
		checkIn = new CheckIn(driver);
	}

	
	@Test
	public void checkInPatientDeletingEncounterMustKeepActiveVisit() throws Exception {
		loginPage.logInAsAdmin();
		appDashboard.openPatientRegistrationApp();
		registration.goThruRegistrationProcessWithoutPrintingCard(); 
		patientIdentifier = patientRegistrationDashboard.getIdentifier();
		patientName = patientRegistrationDashboard.getName();

		appDashboard.openActiveVisitsApp();
		assertFalse(driver.findElement(By.id("content")).getText().contains(patientIdentifier));

        appDashboard.openStartClinicVisitApp();
		checkIn.checkInPatient(patientIdentifier, patientName);
		
		appDashboard.findPatientById(patientIdentifier);
		assertTrue(patientDashboard.hasActiveVisit());
		
		try {
			patientDashboard.deleteEncounter(PatientDashboard.CHECKIN);
		} catch (Exception e) {
			fail();
		}
		
		appDashboard.findPatientById(patientIdentifier);
		
		assertThat(patientDashboard.countEncouters(PatientDashboard.CHECKIN), is(0));
		assertTrue(patientDashboard.hasActiveVisit());
	}

}