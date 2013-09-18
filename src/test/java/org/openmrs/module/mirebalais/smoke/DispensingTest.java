package org.openmrs.module.mirebalais.smoke;

import org.junit.Test;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;
import org.openmrs.module.mirebalais.smoke.helper.PatientDatabaseHandler;
import org.openmrs.module.mirebalais.smoke.pageobjects.AppDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.PatientDashboard;
import org.openmrs.module.mirebalais.smoke.pageobjects.forms.DispenseMedicationForm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.mirebalais.smoke.pageobjects.selects.DurationUnit.DAYS;

public class DispensingTest extends DbTest {
	
	private final String bronchitis = "J40";
	
	private String paracetamol = "Paracetamol (500mg tablet)";
	
	@Test
	public void pharmacistCanDispenseMedicationForAnExistingActiveVisit() throws Exception {
		Patient patient = PatientDatabaseHandler.insertNewTestPatient();
		
		logInAsClinicalUser();
		
		AppDashboard appDashboard = new AppDashboard(driver);
		
		appDashboard.goToPatientPage(patient.getId());
		
		PatientDashboard patientDashboard = new PatientDashboard(driver);
		
		patientDashboard.startVisit();
		
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(false));
		
		patientDashboard.addConsultNoteWithAdmissionToLocation(bronchitis, 2);
		
		logout();
		
		logInAsPharmacistUser();
		
		appDashboard.goToPatientPage(patient.getId());
		
		assertThat("Dispense medication.", patientDashboard.canDispenseMedication(), is(true));
		
		DispenseMedicationForm dispensingForm = patientDashboard.goToDispenseMedicationForm();
		
		dispensingForm.fillFirstMedication(paracetamol, "twice per day", "5", "mg", "7", DAYS, "20");
		dispensingForm.submit();
		patientDashboard.verifyIfSuccessfulMessageIsDisplayed();
		
		patientDashboard.clickFirstEncounterDetails();
		
		assertThat("Medication was dispensed.", patientDashboard.countEncountersOfType("Medication dispensed"), is(1));
		assertThat("Medication name is right.", patientDashboard.containsText("Paracetamol (500mg tablet) (PARACÉTAMOL)"),
		    is(true));
		assertThat("Medication frequency is right.", patientDashboard.containsText("twice per day"), is(true));
		assertThat("Medication dose is right.", patientDashboard.containsText("5.0"), is(true));
		assertThat("Medication dose unit is right.", patientDashboard.containsText("mg"), is(true));
		assertThat("Medication duration is right.", patientDashboard.containsText("7.0"), is(true));
		assertThat("Medication duration unit is right.", patientDashboard.containsText("ANNÉES"), is(true));
		assertThat("Medication amount is right.", patientDashboard.containsText("20.0"), is(true));
		
	}
	
}
