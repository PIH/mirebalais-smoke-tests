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
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.AdmissionNoteForm;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.ConsultNoteForm;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.DispenseMedicationForm;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.EmergencyDepartmentNoteForm;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.RetroConsultNoteForm;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.XRayForm;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class VisitNote extends AbstractPageObject {
	
	public static final String CHECKIN_CREOLE_NAME = "Tcheke";
	
	public static final String CONSULTATION_CREOLE_NAME = "Konsiltasyon";
	
	public static final String VITALS_CREOLE_NAME = "Siy Vito";
	
	public static final String RADIOLOGY_CREOLE_NAME = "Preskripsyon Radyoloji";

    public static final String ADMISSION_CREOLE_NAME = "Admisyon";

    public static final String TRANSFER_CREOLE_NAME= "Transfè";

    public static final String DISCHARGE_CREOLE_NAME= "Soti Nan Swen Entèn";

	public static final String ACTIVE_VISIT_CREOLE_MESSAGE = "aktif";

    private static final By home = By.className("logo");

    private static final By encounterDetails = By.className("encounter-summary-long");

    public static final By diagnosisDetails = By.className("diagnosisLongClass");

    private static final By editEncounter = By.className("edit-encounter");

    private static final By deleteEncounter = By.className("delete-encounter");

    private static final By goToAnotherVisit = By.cssSelector("#choose-another-visit a");

    private static final By firstEncounterDetails = By.className("expand-encounter");
    private static final By firstEncounterContractDetails = By.className("contract-encounter");


    private ConsultNoteForm consultNoteForm;
	
	private EmergencyDepartmentNoteForm eDNoteForm;
	
	private RetroConsultNoteForm retroConsultNoteForm;

    private AdmissionNoteForm admissionNoteForm;
	
	private XRayForm xRayForm;
	
	private HashMap<String, By> formList;

	
	public VisitNote(WebDriver driver) {
		super(driver);
		consultNoteForm = new ConsultNoteForm(driver);
		eDNoteForm = new EmergencyDepartmentNoteForm(driver);
		retroConsultNoteForm = new RetroConsultNoteForm(driver);
		xRayForm = new XRayForm(driver);
        admissionNoteForm = new AdmissionNoteForm(driver);
		createFormsMap();
	}
	
	public boolean verifyIfSuccessfulMessageIsDisplayed() {
		return (driver.findElement(By.className("icon-ok")) != null);
	}
	
	public boolean hasActiveVisit() {
        if (!driver.findElement(By.className("active")).isDisplayed()) {
            return false;
        }
        return driver.findElement(By.className("active")).getText().toLowerCase().contains(ACTIVE_VISIT_CREOLE_MESSAGE);
	}

    public void editFirstEncounter() {
        clickOn(editEncounter);
    }

	public void deleteFirstEncounter() {
		clickOn(deleteEncounter);
		clickOn(By.className("delete-encounter-button") );
		wait5seconds.until(invisibilityOfElementLocated(By.className("delete-encounter-dialog")));
	}
	
	public Integer countEncountersOfType(String encounterName) {

        try {
            wait5seconds.until(presenceOfElementLocated(By.className("encounter-name")));
        }
        catch (TimeoutException e) {
            // for the case when there are no encounters of *any* type
            return 0;
        }

		int count = 0;

        List<WebElement> encounters = driver.findElements(By.className("encounter-name"));

        if (!encounters.isEmpty()) {
            for (WebElement encounter : encounters) {
                if (encounter.getText().equals(encounterName))
                    count++;
            }
        }

		return count;
	}

	public void addConsultNoteWithDischarge(String primaryDiagnosis) throws Exception {
		openForm(formList.get("Consult Note"));
		consultNoteForm.fillFormWithDischarge(primaryDiagnosis);
	}
	
	public String addConsultNoteWithAdmissionToLocation(String primaryDiagnosis, int numbered) throws Exception {
		openForm(formList.get("Consult Note"));
		return consultNoteForm.fillFormWithAdmissionAndReturnLocation(primaryDiagnosis, numbered);
	}
	
	public String addConsultNoteWithTransferToLocation(String primaryDiagnosis, int numbered) throws Exception {
		openForm(formList.get("Consult Note"));
		return consultNoteForm.fillFormWithTransferAndReturnLocation(primaryDiagnosis, numbered);
	}
	
	public void addConsultNoteWithDeath(String primaryDiagnosis) throws Exception {
		openForm(formList.get("Consult Note"));
		consultNoteForm.fillFormWithDeath(primaryDiagnosis);
	}
	
	public void editExistingConsultNote(String primaryDiagnosis) throws Exception {
		editFirstEncounter();
		consultNoteForm.editPrimaryDiagnosis(primaryDiagnosis);
        consultNoteForm.confirmData();
	}
	
	public void addRetroConsultNoteWithDischarge(String primaryDiagnosis) throws Exception {
		openForm(formList.get("Consult Note"));
		retroConsultNoteForm.fillFormWithDischarge(primaryDiagnosis);
	}
	
	public String addRetroConsultNoteWithAdmissionToLocation(String primaryDiagnosis, int numbered) throws Exception {
		openForm(formList.get("Consult Note"));
		return retroConsultNoteForm.fillFormWithAdmissionAndReturnLocation(primaryDiagnosis, numbered);
	}
	
	public String addRetroConsultNoteWithTransferToLocation(String primaryDiagnosis, int numbered) throws Exception {
		openForm(formList.get("Consult Note"));
		return retroConsultNoteForm.fillFormWithTransferAndReturnLocation(primaryDiagnosis, numbered);
	}
	
	public void addEDNote(String primaryDiagnosis) throws Exception {
		openForm(formList.get("ED Note"));
		eDNoteForm.fillFormWithDischarge(primaryDiagnosis);
	}
	
	public void editExistingEDNote(String primaryDiagnosis) throws Exception {
		editFirstEncounter();
		eDNoteForm.editPrimaryDiagnosis(primaryDiagnosis);
        eDNoteForm.confirmData();
	}

    public void addAdmissionNote(String primaryDiagnosis) throws Exception {
        openForm(formList.get("Admission Note"));
        admissionNoteForm.fillFormWithDiagnosis(primaryDiagnosis);
    }

    public void addAdmissionNoteWithDefaultLocation(String primaryDiagnosis) throws Exception {
        openForm(formList.get("Admission Note"));
        admissionNoteForm.fillFormWithDiagnosisAndDefaultLocation(primaryDiagnosis);
    }

    public void addAdmissionNoteAsAdminUser(String primaryDiagnosis) throws Exception {
        openForm(formList.get("Admission Note"));
        admissionNoteForm.fillFormWithBasicEncounterInfoAndDiagnosis(primaryDiagnosis);
    }

    public void editExistingAdmissionNote(String primaryDiagnosis) throws Exception {
        editFirstEncounter();
        admissionNoteForm.editPrimaryDiagnosis(primaryDiagnosis);
        admissionNoteForm.confirmData();
    }

    public void editExistingAdmissionNote(String primaryDiagnosis, int location, int provider) throws Exception {
        editFirstEncounter();
        admissionNoteForm.selectLocation(location);
        admissionNoteForm.selectProvider(provider);
        admissionNoteForm.editPrimaryDiagnosis(primaryDiagnosis);
        admissionNoteForm.confirmData();
    }

    public void orderXRay(String study1, String study2) throws Exception {
        openForm(formList.get("Order X-Ray"));
        xRayForm.fillForm(study1, study2);
    }

	public void openForm(By formIdentification) {
		clickOn(formIdentification);
	}
	
	public void viewConsultationDetails() {
		clickFirstEncounterDetails();
	}
	
	public Boolean containsText(String text) {
		return driver.getPageSource().contains(text);
	}
	
	public List<WebElement> getVisits() {
		return driver.findElements(By.cssSelector("#visit-list-dropdown .list-element"));
	}

    public Integer countVisits() {

        clickOn(goToAnotherVisit);

        try {
            wait5seconds.until(presenceOfElementLocated(By.id("visit-list-dropdown")));
        }
        catch (TimeoutException e) {
            return 0;
        }

        return getVisits().size();
    }

	public String providerForFirstEncounter() {
		return driver.findElement(By.className("encounter-provider")).getText();
	}
	
	public String locationForFirstAdmission() {
		return driver.findElement(By.className("admission-location")).getText();
	}

	public void clickFirstEncounterDetails() {
        clickOn(firstEncounterDetails);
        wait15seconds.until(visibilityOfElementLocated(encounterDetails));
    }

    public void gotoAppDashboard() {
        driver.findElement(home).click();
    }

	private void createFormsMap() {
		formList = new HashMap<String, By>();
		formList.put("Consult Note", By.id(CustomAppLoaderConstants.Extensions.CONSULT_NOTE_VISIT_ACTION));
		formList.put("Surgical Note", By.id(CustomAppLoaderConstants.Extensions.SURGICAL_NOTE_VISIT_ACTION));
		formList.put("Order X-Ray", By.id(CustomAppLoaderConstants.Extensions.ORDER_XRAY_VISIT_ACTION));
		formList.put("ED Note", By.id(CustomAppLoaderConstants.Extensions.ED_CONSULT_NOTE_VISIT_ACTION));
        formList.put("Admission Note", By.id(CustomAppLoaderConstants.Extensions.ADMISSION_NOTE_VISIT_ACTION));
	}

    public ConsultNoteForm getConsultNoteForm() {
        return consultNoteForm;
    }

    public EmergencyDepartmentNoteForm getEDNoteForm() {
        return eDNoteForm;
    }

    public RetroConsultNoteForm getRetroConsultNoteForm() {
        return retroConsultNoteForm;
    }

    public AdmissionNoteForm getAdmissionNoteForm() {
        return admissionNoteForm;
    }


    // TODO move all this dispensing stuff somewhere else?
    public DispenseMedicationForm goToDispenseMedicationForm() {
        driver.findElement(By.id(CustomAppLoaderConstants.Extensions.DISPENSE_MEDICATION_VISIT_ACTION)).click();
        return new DispenseMedicationForm(driver);
    }

    public MedicationDispensed firstMedication() {
        wait15seconds.until(visibilityOfElementLocated(By.className("dispensing-form")));
        WebElement first = driver.findElement(By.className("dispensing-form")).findElements(By.className("medication")).get(0);
        WebElement dispensingInformation = driver.findElement(By.className("dispensing-form"));
        return new MedicationDispensed(dispensingInformation, first, 1);
    }

    public boolean canDispenseMedication() {
        try {
            driver.findElement(By.id(CustomAppLoaderConstants.Extensions.DISPENSE_MEDICATION_VISIT_ACTION));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public class MedicationDispensed {

        private WebElement dispensingInformation;

        private WebElement medication;

        private int order;

        public MedicationDispensed(WebElement dispensingInformation, WebElement medication, int order) {
            this.dispensingInformation = dispensingInformation;
            this.medication = medication;
            this.order = order;
        }

        public String getTypeOfPrescription(){
            return dispensingInformation.findElement(typeOfPrescription()).getText();
        }

        public String getDischargeLocation(){
            return dispensingInformation.findElement(dischargeLocation()).getText();
        }

        public String getName() {
            return medication.findElement(name()).getText();
        }

        public String getFrequency() {
            return medication.findElement(frequency()).getText();
        }

        public String getDose() {
            return medication.findElement(dose()).getText();
        }

        public String getDoseUnit() {
            return medication.findElement(doseUnit()).getText();
        }

        public String getDuration() {
            return medication.findElement(duration()).getText();
        }

        public String getDurationUnit() {
            return medication.findElement(durationUnit()).getText();
        }

        public String getAmount() {
            return medication.findElement(amount()).getText();
        }

        private By typeOfPrescription() {
            return By.cssSelector("[id='Timing of hospital prescription']");
        }

        private By dischargeLocation() {
            return By.cssSelector("[id='Discharge location']");
        }

        private By name() {
            return By.cssSelector("#name" + order);
        }

        private By frequency() {
            return By.cssSelector("#frequencyCoded" + order);
        }

        private By dose() {
            return By.cssSelector("#dose" + order);
        }

        private By doseUnit() {
            return By.cssSelector("#doseUnit" + order);
        }

        private By duration() {
            return By.cssSelector("#duration" + order);
        }

        private By durationUnit() {
            return By.cssSelector("#durationUnit" + order);
        }

        private By amount() {
            return By.cssSelector("#amount" + order);
        }

    }
}
