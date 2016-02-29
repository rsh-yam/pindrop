package pindrop.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class CPU {

	public static int getCPU() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = Database.getConnection();
			String createString = "SELECT max(id) as max FROM cpu";
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

	public static boolean insert(double cpu) {
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
			int cpu_id = getCPU();
			if (cpu_id == -1)
				return false;
			String query = " insert into mem (id,time,cpu)" + " values (?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, cpu_id);
			preparedStmt.setTimestamp(2, timestamp);
			preparedStmt.setString(3, Double.toString(cpu));
			preparedStmt.execute();
			conn.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;

	}

	public static String get(long time) {

		Connection conn = null;
		try {
			conn = Database.getConnection();
		} catch (SQLException e) {
			System.err.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return null;
		}

		Statement stmt = null;
		ResultSet rs = null;
		try {
			String createString = "SELECT * FROM cpu WHERE time = '" + time + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(createString);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		try {
			if (rs == null || rs.isClosed())
				return null;

			StringBuffer sb = new StringBuffer();
			if (rs.next()) {
				String cpu = rs.getString("cpu");

				if (Double.parseDouble(cpu) > 0) {
					sb.append("<cpu>");
					sb.append(cpu);
					sb.append("</cpu>");
				}

				return sb.toString();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return null;

	}

}
