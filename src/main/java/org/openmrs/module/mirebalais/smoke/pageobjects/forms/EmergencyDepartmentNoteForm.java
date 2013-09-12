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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EmergencyDepartmentNoteForm extends ConsultNoteForm {

	public EmergencyDepartmentNoteForm(WebDriver driver) {
		super(driver);
	}

	protected void fillFormWithBasicInfo(String primaryDiagnosis, String disposition) throws Exception {
		choosePrimaryDiagnosis(primaryDiagnosis);
		chooseDisposition(disposition);
		fillTraumaData();
		confirmData();
	}

	private void fillTraumaData() {
		clickOn(By.cssSelector("#trauma input:nth-child(1)"));
        List<WebElement> options = driver.findElements(By.cssSelector("#traumaType option"));
        chooseOption(By.cssSelector("#traumaType option"), options.get(1 + (int) (Math.random() * options.size() - 1)));
	}
}
