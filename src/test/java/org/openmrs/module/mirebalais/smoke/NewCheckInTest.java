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
import org.openmrs.module.mirebalais.smoke.pageobjects.NewCheckIn;

public class NewCheckInTest extends BasicMirebalaisSmokeTest {

	private NewCheckIn newCheckIn;
    
	@Before
	public void setUp() {
		initBasicPageObjects();
		newCheckIn = new NewCheckIn(driver);
	}
    
	@Test
	public void startAClinicVisitAndAddingASurgicalNote() throws Exception {
        loginPage.logInAsAdmin();
        createPatient();
        
        appDashboard.startClinicVisit();
        newCheckIn.checkInPatientFillingTheFormTwice(testPatient.getIdentifier());
        
        assertThat(newCheckIn.isPatientSearchDisplayed(), is(true));
        
        appDashboard.findPatientById(testPatient.getIdentifier());
        patientDashboard.addSurgicalNote();
	}

}
