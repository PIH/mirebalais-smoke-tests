package org.openmrs.module.mirebalais.smoke.helper;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.QueryDataSet;

import java.sql.SQLException;

import static org.dbunit.operation.DatabaseOperation.DELETE;

public class AppointmentTypeDatabaseHandler extends BaseDatabaseHandler {

    private static QueryDataSet appointmentTypeDataToDelete;

    private static void defineAppointmentTypeToDelete() throws AmbiguousTableNameException {
        appointmentTypeDataToDelete = new QueryDataSet(connection);
        appointmentTypeDataToDelete.addTable("appointmentscheduling_appointment_type", "select * from appointmentscheduling_appointment_type where name like 'TEST%'");
    }

    public static void deleteAppointmentTypes() throws DatabaseUnitException, SQLException {
        defineAppointmentTypeToDelete();
        if(appointmentTypeDataToDelete != null)
        DELETE.execute(connection,appointmentTypeDataToDelete);
    }
}
