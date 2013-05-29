package org.openmrs.module.mirebalais.smoke.dataModel;

public class Patient {

	private String name;
	private String identifier;
	
	public Patient(String identifier, String name) {
		this.name = name;
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

}
