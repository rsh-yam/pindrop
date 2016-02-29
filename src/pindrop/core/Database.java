package pindrop.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	private static final String userName = "root";
	private static final String password = "root";
	private static final String serverName = "localhost";
	private static final int portNumber = 3306;
	private static final String dbName = "pindrop";

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName,
				connectionProps);

		return conn;
	}

}