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
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.LoginPage;
import org.openmrs.module.mirebalais.smoke.pageobjects.NewCheckIn;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientRegistrationDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.Registration;

public class NewCheckInTest extends BasicMirebalaisSmokeTest {

	private Registration registration;
	private PatientDashboard patientDashboard;
	private PatientRegistrationDashboard patientRegistrationDashboard; 
	private NewCheckIn newCheckIn;
	
	private String patientIdentifier;
    
	@Before
	public void setUp() {
        loginPage = new LoginPage(driver);
        registration = new Registration(driver);
        patientRegistrationDashboard = new PatientRegistrationDashboard(driver);
        patientDashboard = new PatientDashboard(driver);
        appDashboard = new AppDashboard(driver);
        newCheckIn = new NewCheckIn(driver);
	}
    
	@Test
	public void startAClinicVisitAndAddingASurgicalNote() throws Exception {
        loginPage.logInAsAdmin();
        appDashboard.openPatientRegistrationApp();
        registration.goThruRegistrationProcessWithoutPrintingCard();
        patientIdentifier = patientRegistrationDashboard.getIdentifier();
        
        appDashboard.startClinicVisit();
        newCheckIn.checkInPatientFillingTheFormTwice(patientIdentifier);
        
        assertThat(newCheckIn.isPatientSearchDisplayed(), is(true));
        
        appDashboard.findPatientById(patientIdentifier);
        patientDashboard.addSurgicalNote();
	}

}
