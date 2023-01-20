package DBControllers;

import main.JDBC;

import java.sql.Connection;

/**
 * Appointments Database Accessor
 * @author William Nathan
 */
public class AppointmentsDB {
    private static final Connection db = JDBC.getConnection();
}
