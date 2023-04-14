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

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class ActiveVisitsTest extends DbTest {

	@Test
	public void shouldShowActiveVisitAfterStartVisit() throws Exception {

		login();

		appDashboard.openActiveVisitsApp();
		assertFalse(driver.findElement(By.id("activeVisitsTable")).getText().contains(adultTestPatient.getIdentifier()));

		appDashboard.goToClinicianFacingDashboard(adultTestPatient.getId());
		clinicianDashboard.startVisit();

		appDashboard.openActiveVisitsApp();
		Thread.sleep(2000); // hack delay
		String contentText = driver.findElement(By.id("activeVisitsTable")).getText();
		assertThat(contentText, containsString(adultTestPatient.getNameLastNameFirst()));
		assertThat(contentText, containsString(adultTestPatient.getIdentifier()));
	}
}
