package DBControllers;

import main.JDBC;

import java.sql.Connection;

/**
 * Customers Database Accessor
 * @author William Nathan
 */
public class CustomersDB {
    private static final Connection db = JDBC.getConnection();
}
