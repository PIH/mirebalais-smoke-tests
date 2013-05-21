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

package org.openmrs.module.mirebalais.smoke.pageobjects.forms;

import org.openmrs.module.mirebalais.smoke.pageobjects.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SurgicalPostOperativeNoteForm extends AbstractPageObject {

	private final static String REQUIRED_PROVIDER = "//*[@id='htmlform']/htmlform/fieldset[1]/p[2]/select";
	private final static String SYS_ADMIN = "Sys Admin";
	
	public SurgicalPostOperativeNoteForm(WebDriver driver) {
		super(driver);
	}

	public void fillBasicForm() throws Exception {
		clickOnOptionLookingForText(SYS_ADMIN, By.xpath(REQUIRED_PROVIDER));
		clickOnSubmit();
	}

	private void clickOnSubmit() {
		clickOn(By.cssSelector("input.submitButton"));
	}
	
}