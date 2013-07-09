package org.openmrs.module.mirebalais.smoke.dataModel;

public class Patient {

	private String name;
	private String identifier;
    private Integer id;
    private Integer identifier_id;

    public Patient(String identifier, String name, Integer id, Integer identifier_id) {
		this.name = name;
		this.identifier = identifier;
        this.id = id;
        this.identifier_id = identifier_id;
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

    public Integer getIdentifier_id() {
        return identifier_id;
    }
}
