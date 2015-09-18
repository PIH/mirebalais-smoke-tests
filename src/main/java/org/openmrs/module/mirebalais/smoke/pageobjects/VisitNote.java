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
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

// this has been migrated from the old patient dashboard page object
// TODO make sure everything is migrated, go through and rip out anything no longer used/working
// TODO consolidate/clean up constants vs hard-coded in text
public class VisitNote extends AbstractPageObject {
	
	public static final String CHECKIN_CREOLE_NAME = "Tcheke";
	
	public static final String CONSULTATION_CREOLE_NAME = "Konsiltasyon";
	
	public static final String VITALS_CREOLE_NAME = "Siy Vito";
	
	public static final String RADIOLOGY_CREOLE_NAME = "Preskripsyon Radyoloji";

    public static final String ADMISSION_CREOLE_NAME = "Admisyon";

    public static final String TRANSFER_CREOLE_NAME= "Transfè";

    public static final String DISCHARGE_CREOLE_NAME= "Soti Nan Swen Entèn";

	public static final String ACTIVE_VISIT_CREOLE_MESSAGE = "aktif";

    private final By home = By.className("logo");
	
	private final By medications = By.className("medication");

    private final By encounterDetails = By.className("encounter-summary-long");
	
	private ConsultNoteForm consultNoteForm;
	
	private EmergencyDepartmentNoteForm eDNoteForm;
	
	private RetroConsultNoteForm retroConsultNoteForm;

    private AdmissionNoteForm admissionNoteForm;
	
	private XRayForm xRayForm;
	
	private HashMap<String, By> formList;

    private By editEncounter = By.className("edit-encounter");

	private By deleteEncounter = By.className("delete-encounter");
	
	private By actions = By.cssSelector(".actions");
	
	private By checkIn = By.cssSelector("a i.icon-check-in");

    private By enterDeathCertificateLink = By.id("pih.haiti.deathCertificate");
	
	private By confirmStartVisit = By.cssSelector("#quick-visit-creation-dialog .confirm");

    private By goToAnotherVisit = By.cssSelector("#choose-another-visit a");
	
	private By dispenseMedicationButton = By.id(CustomAppLoaderConstants.Extensions.DISPENSE_MEDICATION_VISIT_ACTION);

    private By encounterList = By.id("encountersList");
	
	private By firstEncounterDetails = By.className("expand-encounter");
	
	private ExpectedCondition<WebElement> detailsAjaxCallReturns = visibilityOfElementLocated(encounterDetails);

    private ExpectedCondition<WebElement> encounterListView = visibilityOfElementLocated(encounterList);
	
	public VisitNote(WebDriver driver) {
		super(driver);
		consultNoteForm = new ConsultNoteForm(driver);
		eDNoteForm = new EmergencyDepartmentNoteForm(driver);
		retroConsultNoteForm = new RetroConsultNoteForm(driver);
		xRayForm = new XRayForm(driver);
        admissionNoteForm = new AdmissionNoteForm(driver);
		createFormsMap();
	}
	
	public void orderXRay(String study1, String study2) throws Exception {
		openForm(formList.get("Order X-Ray"));
		xRayForm.fillForm(study1, study2);
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

    /**
     * Confirm that the patient dashboard is currently open for the specified patient
     * @param patient
     * @return
     */
    public boolean isOpenForPatient(Patient patient) {

        try {
            // just make sure that a few core page elements exist
            driver.findElement(By.id("visit-details"));
            driver.findElement(By.id("visits-list"));
            driver.findElement(By.className("patient-header"));

            // patient specific information
            if (!driver.findElement(By.className("patient-header")).getText().contains(patient.getIdentifier())) {
                System.out.println("Did not find identifier " + patient.getIdentifier() + " in patient header.");
                return false;
            }

            return true;
        }
        catch (NoSuchElementException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

    }

	public void startVisit() {
		hoverOn(actions);
		clickOn(checkIn);
		clickOn(confirmStartVisit);
		
		wait15seconds.until(visibilityOfElementLocated(By.cssSelector(".visit-actions.active-visit")));
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
	
	public void addEmergencyDepartmentNote(String primaryDiagnosis) throws Exception {
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

	public void openForm(By formIdentification) {
		clickOn(formIdentification);
	}
	
	public void viewConsultationDetails() {
		clickFirstEncounterDetails();
	}
	
	public void requestRecord() {
		hoverOn(By.cssSelector(".actions"));
		clickOn(By.cssSelector(".actions i.icon-folder-open"));
		clickOn(By.cssSelector("#request-paper-record-dialog .confirm"));
	}

	public String getDossierNumber() {
		List<WebElement> elements = driver.findElements(By.cssSelector(".identifiers span"));
		return elements.get(1).getText();
	}
	
	public boolean canRequestRecord() {
		hoverOn(By.cssSelector(".actions"));
		return driver.findElement(By.cssSelector(".actions i.icon-folder-open")).isDisplayed();
	}
	
	public Boolean startVisitButtonIsVisible() {
		try {
			driver.findElement(By.id("noVisitShowVisitCreationDialog"));
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public Boolean containsText(String text) {
		return driver.getPageSource().contains(text);
	}
	
	public List<WebElement> getVisits() {
		return driver.findElements(By.className("visit-list-item"));
	}

    public Integer countVisits() {

        clickOn(goToAnotherVisit);

        try {
            wait5seconds.until(presenceOfElementLocated(By.id("visit-list")));
        }
        catch (TimeoutException e) {
            return 0;
        }

        return getVisits().size();
    }

	public boolean isDead() {
		try {
			driver.findElement(By.className("death-message"));
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public String providerForFirstEncounter() {
		return driver.findElement(By.className("encounter-provider")).getText();
	}
	
	public String locationForFirstAdmission() {
		return driver.findElement(By.className("admission-location")).getText();
	}
	
	public boolean canDispenseMedication() {
		try {
			driver.findElement(dispenseMedicationButton);
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public DispenseMedicationForm goToDispenseMedicationForm() {
		driver.findElement(dispenseMedicationButton).click();
		return new DispenseMedicationForm(driver);
	}

    public DeathCertificateFormPage goToEnterDeathCertificateForm() {
        clickOn(enterDeathCertificateLink);
        return new DeathCertificateFormPage(driver);
    }
	
	public void clickFirstEncounterDetails() {
        clickOn(firstEncounterDetails);
        wait5seconds.until(visibilityOfElementLocated(encounterDetails));
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


	public MedicationDispensed firstMedication() {
		return new MedicationDispensed(driver.findElement(encounterDetails));
	}

    public class MedicationDispensed {

        private List<WebElement> medicationElements;

        public MedicationDispensed(WebElement encounterDetails) {
            this.medicationElements = encounterDetails.findElements(By.className("obs-value"));
		}

        public String getTypeOfPrescription(){
            return medicationElements.get(0).getText();
        }

        public String getDischargeLocation(){
            return medicationElements.get(1).getText();
        }

		public String getName() {
			return medicationElements.get(3).getText();
		}

        public String getDose() {
            return medicationElements.get(4).getText();
        }

        public String getDoseUnit() {
            return medicationElements.get(5).getText();
        }

        public String getFrequency() {
            return medicationElements.get(6).getText();
        }

        public String getDuration() {
            return medicationElements.get(7).getText();
        }

        public String getDurationUnit() {
            return medicationElements.get(8).getText();
        }

        public String getAmount() {
            return medicationElements.get(9).getText();
        }

		
	}

    public Checkin firstEncounterCheckIn() {
        WebElement firstCheckIn = driver.findElements(encounterDetails).get(0);
        return new Checkin(firstCheckIn);
    }

    public class Checkin {

        private WebElement checkInInformation;


        public Checkin(WebElement checkInInformation){
            this.checkInInformation = checkInInformation;
        }

        public String getCheckInInformation(){
            return checkInInformation.getText();
        }

        private By checkInformation() {
            return By.cssSelector("p span");
        }

    }

    public ConsultNoteForm getConsultNoteForm() {
        return consultNoteForm;
    }

    public EmergencyDepartmentNoteForm geteDNoteForm() {
        return eDNoteForm;
    }

    public RetroConsultNoteForm getRetroConsultNoteForm() {
        return retroConsultNoteForm;
    }

    public AdmissionNoteForm getAdmissionNoteForm() {
        return admissionNoteForm;
    }
}
