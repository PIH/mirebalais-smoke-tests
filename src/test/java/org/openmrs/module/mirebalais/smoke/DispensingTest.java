package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.ClinicianDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.VisitNote;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.DispenseMedicationForm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.DrugFrequency.THREE_TIMES_A_DAY;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.DurationUnit.DAYS;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.TypeOfPrescrition.DISCHARGE;


public class DispensingTest extends DbTest {
	
	private String paracetamol = "Paracetamol, 500mg, tablet";
    private String prescriptionLocation = "Sal Fanm";
    private String doseUnit = "Milligramme (mg)";

    @Test
	public void pharmacyManagerCanDispenseMedicationForAnExistingActiveVisit() throws Exception {
		AppDashboard appDashboard = new AppDashboard(driver);
		VisitNote patientDashboard = new VisitNote(driver);
        ClinicianDashboard clinicianDashboard = new ClinicianDashboard(driver);
		
		Patient patient = PatientDatabaseHandler.insertNewTestPatient();
		
		logInAsPhysicianUser();
		appDashboard.goToClinicianFacingDashboard(patient.getId());
		clinicianDashboard.startVisit();
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(false));
		logout();
		
		logInAsPharmacyManagerUser();
		appDashboard.goToVisitNote(patient.getId());
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(true));
		DispenseMedicationForm dispensingForm = patientDashboard.goToDispenseMedicationForm();
        dispensingForm.fillDispensingInformation(DISCHARGE, prescriptionLocation);
        dispensingForm.fillFirstMedication(paracetamol, THREE_TIMES_A_DAY , "5", doseUnit, "7", DAYS, "20");
		dispensingForm.submit();

		patientDashboard.clickFirstEncounterDetails();

        assertThat("Medication was dispensed.", patientDashboard.countEncountersOfType("Medikaman Ki Distribye"), is(1));

        VisitNote.MedicationDispensed medicationDispensed = patientDashboard.firstMedication();

        assertThat("Type of precription is right", medicationDispensed.getTypeOfPrescription(), is("Egzeyat"));

        // TODO fix and re-enable this after https://tickets.pih-emr.org/browse/UHM-2179
        //assertThat("Prescription location is right",  medicationDispensed.getDischargeLocation(), is(prescriptionLocation));

        // TODO fix and re-enable
        //assertThat("Medication name is right.", medicationDispensed.getName(), is(("Paracetamol, 500mg, tablet")));

        //assertThat("Medication dose is right.", medicationDispensed.getDose(), is("5"));
        assertThat("Medication dose is right.", medicationDispensed.getDose(), is("5.0"));

        //assertThat("Medication frequency is right.", medicationDispensed.getFrequency(), is("TID"));
        assertThat("Medication frequency is right.", medicationDispensed.getFrequency(), is("TDS"));

        //assertThat("Medication dose unit is right.", medicationDispensed.getDoseUnit(), is(doseUnit));
        assertThat("Medication dose unit is right.", medicationDispensed.getDoseUnit(), is("mg"));

        //assertThat("Medication duration is right.", medicationDispensed.getDuration(), is("7"));
        assertThat("Medication duration is right.", medicationDispensed.getDuration(), is("7.0"));

        assertThat("Medication duration unit is right.", medicationDispensed.getDurationUnit(), is("Jou"));

        //assertThat("Medication amount is right.", medicationDispensed.getAmount(), is("20"));
        assertThat("Medication amount is right.", medicationDispensed.getAmount(), is("20.0"));

    }
	
}
