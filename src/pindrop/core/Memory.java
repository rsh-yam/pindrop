package pindrop.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Memory {

	public static int getMemory() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = Database.getConnection();
			String createString = "SELECT max(id) as max FROM mem";
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

	public static boolean insert(long totalMem, long freeMem, long totalSwap, long freeSwap) {
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
			int memory_id = getMemory();
			if (memory_id == -1)
				return false;
			String query = " insert into mem (id,time,totalmem,freemem,totalswap,freeswap)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, memory_id);
			preparedStmt.setTimestamp(2, timestamp);
			preparedStmt.setString(3, Long.toString(totalMem));
			preparedStmt.setString(4, Long.toString(freeMem));
			preparedStmt.setString(5, Long.toString(totalSwap));
			preparedStmt.setString(6, Long.toString(freeSwap));
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
			String createString = "SELECT * FROM memory WHERE time = '" + time + "'";
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
				String totalMem = rs.getString("totalmem");
				String freeMem = rs.getString("freemem");
				String totalSwap = rs.getString("totalswap");
				String freeSwap = rs.getString("freeswap");

				if (totalMem.equals("-1")) {
					sb.append("<totalMem>");
					sb.append(totalMem);
					sb.append("</totalMem>");
				}
				if (freeMem.equals("-1")) {
					sb.append("<freeMem>");
					sb.append(freeMem);
					sb.append("</freeMem>");
				}
				if (totalSwap.equals("-1")) {
					sb.append("<totalSwap>");
					sb.append(totalSwap);
					sb.append("</totalSwap>");
				}
				if (freeSwap.equals("-1")) {
					sb.append("<freeSwap>");
					sb.append(freeSwap);
					sb.append("</freeSwap>");
				}
				return sb.toString();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return null;

	}

}
