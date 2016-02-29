package pindrop.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Disk {

	public static int getFile() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = Database.getConnection();
			String createString = "SELECT max(id) as max FROM file";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(createString);
			if (rs == null || rs.isClosed())
				return 1;
			if (rs.next()) {
				return rs.getInt("max") + 1;
			}
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();

		}
		return -1;

	}

	public static boolean insert(long usable, long free, long total) {
		Connection conn = null;
		try {
			conn = Database.getConnection();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return false;
		}

		try {

			long time = System.currentTimeMillis();
			Timestamp timestamp = new Timestamp(time);
			int memory_id = getFile();
			if (memory_id == -1)
				return false;
			String query = " insert into file (id,time,usable,free,total)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, memory_id);
			preparedStmt.setTimestamp(2, timestamp);
			preparedStmt.setString(3, Long.toString(usable));
			preparedStmt.setString(4, Long.toString(free));
			preparedStmt.setString(5, Long.toString(total));
			preparedStmt.execute();
			conn.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;

	}

}
