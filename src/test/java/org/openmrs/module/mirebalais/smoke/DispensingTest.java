package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.DispenseMedicationForm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.DrugFrequency.THREE_TIMES_A_DAY;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.DurationUnit.DAYS;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.TypeOfPrescrition.DISCHARGE;


public class DispensingTest extends DbTest {
	
	private String paracetamol = "Paracetamol, 500mg, tablet";
    private String prescriptionLocation = "Sal Fanm";
	
	@Test
	public void pharmacistCanDispenseMedicationForAnExistingActiveVisit() throws Exception {
		AppDashboard appDashboard = new AppDashboard(driver);
		PatientDashboard patientDashboard = new PatientDashboard(driver);
		
		Patient patient = PatientDatabaseHandler.insertNewTestPatient();
		
		logInAsClinicalUser();
		appDashboard.goToPatientPage(patient.getId());
		patientDashboard.startVisit();
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(false));
		logout();
		
		logInAsPharmacistUser();
		appDashboard.goToPatientPage(patient.getId());
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(true));
		DispenseMedicationForm dispensingForm = patientDashboard.goToDispenseMedicationForm();
        dispensingForm.fillDispensingInformation(DISCHARGE, prescriptionLocation);
		dispensingForm.fillFirstMedication(paracetamol, THREE_TIMES_A_DAY , "5", "Milligramme (mg)", "7", DAYS, "20");
		dispensingForm.submit();


		patientDashboard.clickFirstEncounterDetails();

        assertThat("Medication was dispensed.", patientDashboard.countEncountersOfType("Medikaman Ki Distribye"), is(1));

        PatientDashboard.MedicationDispensed medicationDispensed = patientDashboard.firstMedication();

        assertThat("Medication name is right.", medicationDispensed.getName(), is(("Paracetamol, 500mg, tablet")));
        assertThat("Medication frequency is right.", medicationDispensed.getFrequency(), is("TID"));
        assertThat("Medication dose is right.", medicationDispensed.getDose(), is("5"));
        assertThat("Medication dose unit is right.", medicationDispensed.getDoseUnit(), is("mg"));
        assertThat("Medication duration is right.", medicationDispensed.getDuration(), is("7"));
        assertThat("Medication duration unit is right.", medicationDispensed.getDurationUnit(), is("Jou"));
        assertThat("Medication amount is right.", medicationDispensed.getAmount(), is("20"));

        assertThat("Type of precription is right", medicationDispensed.getTypeOfPrescription(), is("Egzeyat"));
        assertThat("Prescription location is right",  medicationDispensed.getDischargeLocation(), is(prescriptionLocation));



    }
	
}
