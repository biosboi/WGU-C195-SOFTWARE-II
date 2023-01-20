package DBControllers;

import main.JDBC;

import java.sql.Connection;

/**
 * First Level Divisions Database Accessor
 * @author William Nathan
 */
public class FirstLevelDivisionsDB {
    private static final Connection db = JDBC.getConnection();
}
