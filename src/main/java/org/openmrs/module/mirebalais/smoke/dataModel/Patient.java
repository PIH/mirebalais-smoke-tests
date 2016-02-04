package org.openmrs.module.mirebalais.smoke.dataModel;

import java.math.BigInteger;

public class Patient {

	private String given_name;
    private String family_name;
	private String identifier;
    private BigInteger id;
    private Integer identifier_id;
    private BigInteger person_name_id;
    private BigInteger person_address_id;
    private BigInteger patient_identifier_id;
    private BigInteger encounter_id;
    private Integer encounter_type_id;
    private String person_uuid;
    private String person_name_uuid;
    private String person_address_uuid;
    private String patient_identifier_uuid;
    private String encounter_uuid;


    public Patient(String identifier, String given_name, String family_name, BigInteger id, Integer identifier_id,
                   BigInteger person_name_id, BigInteger person_address_id, BigInteger patient_identifier_id, BigInteger encounterId, Integer encounterTypeId,
                   String person_uuid, String person_name_uuid, String person_address_uuid, String patient_identifier_uuid, String encounter_uuid) {

        this.given_name = given_name;
		this.family_name = family_name;
        this.identifier = identifier;
        this.id = id;
        this.identifier_id = identifier_id;
        this.person_name_id = person_name_id;
        this.person_address_id = person_address_id;
        this.patient_identifier_id = patient_identifier_id;
        this.encounter_id = encounterId;
        this.encounter_type_id = encounterTypeId;
        this.person_uuid = person_uuid;
        this.person_name_uuid = person_name_uuid;
        this.person_address_uuid = person_address_uuid;
        this.patient_identifier_uuid = patient_identifier_uuid;
        this.encounter_uuid = encounter_uuid;
    }

	public String getName() {
		return given_name + " " + family_name;
	}

    public String getNameLastNameFirst() {
        return family_name + ", " + given_name;
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

    public BigInteger getEncounter_id() {
        return encounter_id;
    }

    public Integer getEncounter_type_id() {
        return encounter_type_id;
    }

    public String getPerson_uuid() {
        return person_uuid;
    }

    public String getPerson_name_uuid() {
        return person_name_uuid;
    }

    public String getPerson_address_uuid() {
        return person_address_uuid;
    }


    public String getPatient_identifier_uuid() {
        return patient_identifier_uuid;
    }

    public String getEncounter_uuid() {
        return encounter_uuid;
    }
}
