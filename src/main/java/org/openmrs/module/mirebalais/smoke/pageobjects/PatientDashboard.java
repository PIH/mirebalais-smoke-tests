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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class PatientDashboard extends AbstractPageObject {
	
	public static final String CHECKIN_CREOLE_NAME = "Tcheke";
	
	public static final String CONSULTATION_CREOLE_NAME = "Konsiltasyon";
	
	public static final String VITALS_CREOLE_NAME = "Siy Vito";
	
	public static final String RADIOLOGY_CREOLE_NAME = "Preskripsyon Radyoloji";

    public static final String ADMISSION_CREOLE_NAME = "Admisyon";

    public static final String TRANSFER_CREOLE_NAME= "Transfè";

    public static final String DISCHARGE_CREOLE_NAME= "Soti Nan Swen Entèn";

	public static final String ACTIVE_VISIT_CREOLE_MESSAGE = "Vizit aktiv";

    private final By home = By.className("logo");
	
	private final By dispensingForm = By.cssSelector(".encounter-summary-container .dispensing-form");
	
	private final By medications = By.className("medication");

    private final By encounterDetails = By.cssSelector(".encounter-summary-container");
	
	private ConsultNoteForm consultNoteForm;
	
	private EmergencyDepartmentNoteForm eDNoteForm;
	
	private RetroConsultNoteForm retroConsultNoteForm;

    private AdmissionNoteForm admissionNoteForm;
	
	private XRayForm xRayForm;
	
	private HashMap<String, By> formList;

	private By deleteEncounter = By.cssSelector("i.deleteEncounterId");
	
	private By confirmDeleteEncounter = By.cssSelector("#delete-encounter-dialog .confirm");
	
	private By actions = By.cssSelector(".actions");
	
	private By checkIn = By.cssSelector("a i.icon-check-in");

    private By addRetroVisit = By.cssSelector("a i.icon-plus");

    private By retroStartDate = By.cssSelector("#retrospectiveVisitStartDate-display");

    private By retroStopDate = By.cssSelector("#retrospectiveVisitStopDate-display");
	
	private By confirmStartVisit = By.cssSelector("#quick-visit-creation-dialog .confirm");

    private By confirmRetroVisit = By.cssSelector("#retrospective-visit-creation-dialog .confirm");

	private By firstPencilIcon = By.cssSelector("#encountersList span i:nth-child(1)");
	
	private By dispenseMedicationButton = By.id(CustomAppLoaderConstants.Extensions.DISPENSE_MEDICATION_VISIT_ACTION);

    private By encounterList = By.id("encountersList");
	
	private By firstEncounterDetails = By.className("details-action");
	
	private ExpectedCondition<WebElement> detailsAjaxCallReturns = visibilityOfElementLocated(encounterDetails);

    private ExpectedCondition<WebElement> encounterListView = visibilityOfElementLocated(encounterList);
	
	public PatientDashboard(WebDriver driver) {
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
        return driver.findElement(By.cssSelector(".status-container")).getText().contains(ACTIVE_VISIT_CREOLE_MESSAGE);
	}

    public void editFirstEncounter() {
        clickOn(By.cssSelector(".editEncounter"));
    }

	public void deleteFirstEncounter() {
		clickOn(deleteEncounter);
		clickOn(confirmDeleteEncounter);
		
		wait5seconds.until(invisibilityOfElementLocated(By.id("delete-encounter-dialog")));
	}
	
	public Integer countEncountersOfType(String encounterName) {

        wait15seconds.until(presenceOfElementLocated(By.id("encountersList")));

		int count = 0;

        List<WebElement> encounters = driver.findElements(By.cssSelector("span.encounter-name"));

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

    public void addRetroVisit() {
        hoverOn(actions);
        clickOn(addRetroVisit);
        hitTabKey(retroStartDate);
        hitTabKey(retroStopDate);
        clickOn(confirmRetroVisit);
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
		openForm(By.cssSelector(".consult-encounter-template .editEncounter"));
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
		openForm(By.cssSelector(".consult-encounter-template .editEncounter"));
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
		clickOn(By.cssSelector(".consult-encounter-template .show-details"));
	}
	
	public void requestRecord() {
		hoverOn(By.cssSelector(".actions"));
		clickOn(By.cssSelector(".actions i.icon-folder-open"));
		clickOn(By.cssSelector("#request-paper-record-dialog .confirm"));
	}

    public void openRequestAppointmentForm() {
        hoverOn(By.cssSelector(".actions"));
        clickOn(By.cssSelector(".actions a[href*='requestAppointment'"));
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
		return driver.findElements(By.cssSelector(".menu-item.viewVisitDetails"));
	}

    public Integer countVisits() {

        try {
            wait5seconds.until(presenceOfElementLocated(By.cssSelector(".menu-item.viewVisitDetails")));
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
		return driver.findElement(By.className("encounter-details")).findElement(By.className("provider")).getText();
	}
	
	public String locationForFirstEncounter() {
		return driver.findElement(By.className("encounter-details")).findElement(By.className("location")).getText();
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
	
	public void clickFirstEncounterDetails() {
        WebDriverWait wait30seconds = new WebDriverWait(driver, 30);
        wait30seconds.until(encounterListView);
        WebElement encounterDetails = driver.findElement(firstEncounterDetails);
		encounterDetails.click();
		wait30seconds.until(detailsAjaxCallReturns);
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
        wait15seconds.until(visibilityOfElementLocated(dispensingForm));
		WebElement first = driver.findElement(dispensingForm).findElements(medications).get(0);
        WebElement dispensingInformation = driver.findElement(dispensingForm);
		return new MedicationDispensed(dispensingInformation, first, 1);
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
