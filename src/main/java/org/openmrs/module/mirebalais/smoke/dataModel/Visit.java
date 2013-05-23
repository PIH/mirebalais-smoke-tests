package org.openmrs.module.mirebalais.smoke.dataModel;


public class Visit {

	private String patientId;
	private String name;
	private String firstAdmitted;
	private String currentWard;
	
	public Visit(String patientId, String name,	String firstAdmitted, String currentWard) {
		this.patientId = patientId;
		this.name = name;
		this.firstAdmitted = firstAdmitted;
		this.currentWard = currentWard;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getName() {
		return name;
	}

	public String getFirstAdmitted() {
		return firstAdmitted;
	}

	public String getCurrentWard() {
		return currentWard;
	}
	
	
}
