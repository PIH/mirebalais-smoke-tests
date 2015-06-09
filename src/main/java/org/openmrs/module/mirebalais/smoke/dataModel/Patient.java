package org.openmrs.module.mirebalais.smoke.dataModel;

import java.math.BigInteger;

public class Patient {

	private String given_name;
    private String family_name;
	private String identifier;
    private BigInteger id;
    private String uuid;
    private Integer identifier_id;
    private BigInteger person_name_id;
    private BigInteger person_address_id;
    private BigInteger patient_identifier_id;
    private BigInteger encounterId;
    private Integer encounterTypeId;

    public Patient(String identifier, String given_name, String family_name, BigInteger id, String uuid, Integer identifier_id,
                   BigInteger person_name_id, BigInteger person_address_id, BigInteger patient_identifier_id, BigInteger encounterId, Integer encounterTypeId) {

        this.given_name = given_name;
		this.family_name = family_name;
        this.identifier = identifier;
        this.id = id;
        this.uuid = uuid;
        this.identifier_id = identifier_id;
        this.person_name_id = person_name_id;
        this.person_address_id = person_address_id;
        this.patient_identifier_id = patient_identifier_id;
        this.encounterId = encounterId;
        this.encounterTypeId = encounterTypeId;
    }

	public String getName() {
		return given_name + " " + family_name;
	}

	public String getIdentifier() {
		return identifier;
	}

    public BigInteger getId() {
        return id;
    }

    public Integer getIdentifier_id() {
        return identifier_id;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public BigInteger getPerson_name_id() {
        return person_name_id;
    }

    public BigInteger getPerson_address_id() {
        return person_address_id;
    }

    public BigInteger getPatient_identifier_id() {
        return patient_identifier_id;
    }

    public String getUuid() {
        return uuid;
    }

    public BigInteger getEncounter_id() {
        return encounterId;
    }

    public Integer getEncounter_type_id() {
        return encounterTypeId;
    }
}
