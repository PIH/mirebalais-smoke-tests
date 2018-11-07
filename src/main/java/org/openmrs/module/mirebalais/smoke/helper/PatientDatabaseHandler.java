package org.openmrs.module.mirebalais.smoke.helper;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.RowOutOfBoundsException;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.openmrs.module.mirebalais.smoke.dataModel.Patient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.dbunit.operation.DatabaseOperation.DELETE;
import static org.dbunit.operation.DatabaseOperation.INSERT;

public class PatientDatabaseHandler extends BaseDatabaseHandler {

    protected static Map<Patient, IDataSet> datasets = new HashMap<Patient, IDataSet>();

	private static List<Map<String, String>> patientTablesToDelete = new LinkedList<Map<String, String>>();

    static {
        try {
            initializePatientTablesToDelete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void addTestPatientForDelete(BigInteger patientId) throws IOException, DataSetException, SQLException {
		Patient patient = new Patient("123", null, null, patientId, -1, new BigInteger("-1"), new BigInteger("-1"),
                new BigInteger("-1"), new BigInteger("-1"), -1, null, null, null, null, null, null);
		
		datasets.put(patient, createDataset(patient));
	}

	public static Patient insertAdultTestPatient() throws Exception {
    	return insertTestPatient("1934-02-12");
	}

	public static Patient insertNewbornTestPatient() throws Exception {
    	return insertTestPatient(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}

	private static Patient insertTestPatient(String birthdate) throws Exception {
		try {
			Patient patient = new Patient(getNextValidPatientIdentifier(), "Crash Test", "Dummy",
			        getNextAutoIncrementFor("person"), getPatientIdentifierTypeId(),
			        getNextAutoIncrementFor("person_name"), getNextAutoIncrementFor("person_address"),
			        getNextAutoIncrementFor("patient_identifier"), getNextAutoIncrementFor("encounter"),
			        getEncounterTypeId(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
					UUID.randomUUID().toString(), UUID.randomUUID().toString(), birthdate);
			
			IDataSet dataset = createDataset(patient);
			datasets.put(patient, dataset);
			
			INSERT.execute(connection, dataset);
			return patient;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("unable to create patient in database", e);
		}
	}

	public static void deleteAllTestPatients() throws Exception {
		for (Patient patient : datasets.keySet()) {
			deleteTestPatient(patient);
		}
	}
	
	private static Integer getEncounterTypeId() throws SQLException, DataSetException {
		ITable patientRegistrationEncounterType = connection.createQueryTable("encounter_type",
		    "select * from encounter_type where uuid = '873f968a-73a8-4f9c-ac78-9f4778b751b6'"); // Patient Registration
		return (Integer) patientRegistrationEncounterType.getValue(0, "encounter_type_id");
	}
	
	private static BigInteger getTableId(String table, BigInteger patientId) throws DataSetException, SQLException {
		ITable queryTable = connection.createQueryTable(table, "select * from " + table + " where person_id = " + patientId);
		return queryTable.getRowCount() > 0 ? new BigInteger(queryTable.getValue(0, table + "_id").toString())
		        : new BigInteger("-1");
	}
	
	private static void deleteTestPatient(Patient patient) throws Exception {
		for (Map<String, String> tables : patientTablesToDelete) {
			QueryDataSet createdData = new QueryDataSet(connection);
			for (String table : tables.keySet()) {
				createdData.addTable(table, String.format(tables.get(table), patient.getId()));
			}
			DELETE.execute(connection, createdData);
		}
		
		DELETE.execute(connection, datasets.get(patient));

        // we stopped unlocking here because I don't think this was playing nicely with hibernate and we were getting duplicates
		//unlockPatientIdentifier(patient.getIdentifier());
	}
	
	private static void initializePatientTablesToDelete() {
		Map<String, String> firstToDelete = new LinkedHashMap<String, String>();
		firstToDelete
		        .put(
		            "obs",
		            "select * from obs where encounter_id in (select encounter_id from encounter where patient_id = %d) and obs_group_id is not null");
		firstToDelete.put("person_merge_log", "select * from person_merge_log where winner_person_id = %d");
        firstToDelete.put("paperrecord_paper_record_request",
                "select * from paperrecord_paper_record_request where paper_record in (select record_id from paperrecord_paper_record where patient_identifier in (select patient_identifier_id from patient_identifier where patient_id = %d))");
        firstToDelete.put("appointmentscheduling_appointment_request",
                "select * from appointmentscheduling_appointment_request where patient_id= %d");
        firstToDelete
                .put("name_phonetics",
                        "select * from name_phonetics where person_name_id in (select person_name_id from person_name where person_id = %d)");
        firstToDelete.put("encounter_diagnosis", "select * from encounter_diagnosis where patient_id = %d");
        patientTablesToDelete.add(firstToDelete);
		
		Map<String, String> secondToDelete = new LinkedHashMap<String, String>();
		secondToDelete.put("person_merge_log", "select * from person_merge_log where loser_person_id = %d");
		secondToDelete.put("person_attribute", "select * from person_attribute where person_id = %d");
		secondToDelete.put("patient_identifier", "select * from patient_identifier where patient_id = %d");
		    secondToDelete.put("paperrecord_paper_record",
                "select * from paperrecord_paper_record where patient_identifier in (select patient_identifier_id from patient_identifier where patient_id = %d)");
        secondToDelete.put("visit", "select * from visit where patient_id = %d");
        secondToDelete.put("visit", "select * from visit where patient_id = %d");
		secondToDelete.put("encounter", "select * from encounter where patient_id = %d");
		secondToDelete.put("orders", "select * from orders where patient_id = %d");
		secondToDelete.put("allergy", "select * from allergy where patient_id = %d");
		secondToDelete.put("test_order",
		    "select * from test_order where order_id in (select order_id from orders where patient_id = %d)");
		secondToDelete.put("emr_radiology_order",
		    "select * from emr_radiology_order where order_id in (select order_id from orders where patient_id = %d)");
		secondToDelete.put("obs",
		    "select * from obs where encounter_id in (select encounter_id from encounter where patient_id = %d)");
		secondToDelete
		        .put("encounter_provider",
		            "select * from encounter_provider where encounter_id in (select encounter_id from encounter where patient_id = %d)");
        secondToDelete.put("person_name", "select * from person_name where person_id = %d");
        secondToDelete.put("person_address", "select * from person_address where person_id = %d");
        secondToDelete.put("patient_identifier", "select * from patient_identifier where patient_id = %d");
		patientTablesToDelete.add(secondToDelete);
	}
	
	private static IDataSet createDataset(Patient patient) throws IOException, DataSetException {
		Handlebars handlebars = new Handlebars();
		Template template = handlebars.compile("datasets/patients_dataset.xml");
		
		return new FlatXmlDataSetBuilder().build(new InputStreamReader(IOUtils.toInputStream(template.apply(patient))));
	}
	
	private static String getNextValidPatientIdentifier() throws Exception {

        String identifier;

        // handles Haiti address generation form
		ITable patientIdentifier = connection.createQueryTable("idgen_pooled_identifier",
		    "select * from idgen_pooled_identifier where date_used is null limit 1");

        try {
            identifier = (String) patientIdentifier.getValue(0, "identifier");
            lockPatientIdentifier(identifier);
        }
        catch(RowOutOfBoundsException e) {
            // TODO improve this
            // hack to generate a random pseudo (won't be sequential) Pleebo identifier if Haiti generator fails
            identifier = "PL9" + StringUtils.leftPad(new Integer(new Random().nextInt(999999)).toString(), 6, '0');
        }

		return identifier;
	}
	
	private static void lockPatientIdentifier(String identifier) throws Exception {
		setDateUsedOfPatientIdentifierTo(identifier, "sysdate()");
	}
	
	private static void unlockPatientIdentifier(String identifier) throws Exception {
		setDateUsedOfPatientIdentifierTo(identifier, "null");
	}
	
	private static Integer getPatientIdentifierTypeId() throws Exception {

        // bit of a hack--search for ZL EMR ID type first, if this fails, go for Pleebo EMR ID, then Mexico
        ITable patientIdentifierType = connection.createQueryTable("identifier_source",
		    "select * from patient_identifier_type where uuid = 'a541af1e-105c-40bf-b345-ba1fd6a59b85'"); // ZL EMR ID

        try {
            return (Integer) patientIdentifierType.getValue(0, "patient_identifier_type_id");
        }
        catch (RowOutOfBoundsException e) {
        }

        patientIdentifierType = connection.createQueryTable("identifier_source",
                    "select * from patient_identifier_type where uuid = '0bc545e0-f401-11e4-b939-0800200c9a66'"); // Pleebo EMR ID

		try {
			return (Integer) patientIdentifierType.getValue(0, "patient_identifier_type_id");
		}
		catch (RowOutOfBoundsException e) {
		}

		patientIdentifierType = connection.createQueryTable("identifier_source",
				"select * from patient_identifier_type where uuid = '506add39-794f-11e8-9bcd-74e5f916c5ec'"); // Mexico EMR ID

        return (Integer) patientIdentifierType.getValue(0, "patient_identifier_type_id");

	}

	private static void setDateUsedOfPatientIdentifierTo(String identifier, String date) throws Exception {
		connection
		        .getConnection()
		        .createStatement()
		        .executeUpdate(
		            "update idgen_pooled_identifier set date_used = " + date + " where identifier = '" + identifier + "'");
	}
}
