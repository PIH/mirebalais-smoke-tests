package org.openmrs.module.mirebalais.smoke.dataModel;

public class Patient {

	private String name;
	private String identifier;
    private Integer id;
	
	public Patient(String identifier, String name, Integer id) {
		this.name = name;
		this.identifier = identifier;
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

    public Integer getId() {
        return id;
    }
}
