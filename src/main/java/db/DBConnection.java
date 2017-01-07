package db;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private static String strError = "";
	private static Object poolLock = new Object();
	private static DataSource connectionPool;
	ResultSet rs = null;
	Statement stmt = null;

	private Connection conn = null;

	public Connection getConnection() {
		// System.out.println("-------");
		synchronized (poolLock) {
			if (connectionPool == null) {
				try {
					InitialContext ctx = new InitialContext();
					connectionPool = (DataSource) ctx
							.lookup("java:comp/env/jdbc/scoracle");
				} catch (Exception e) {
					strError = "DbPool[0]:" + e.getMessage();
					System.out.println(e.getMessage());
					return null;
				}
			}
		}// synchronized

		try {
			conn = connectionPool.getConnection();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			strError = "DbPool[1]:" + e.getMessage();
			// System.out.println( e.getMessage() );
			return null;
		}

		return conn;
	}// end getConnection()

	public String getError() {
		return strError;
	}

	public ResultSet executeQuery(String sql) {

		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			strError = "SQLExecute[0]:" + e.getMessage();
			System.out.println(e.getMessage());
			return null;
		}
		return rs;
	}

	public String executeInsert(String sql) {
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("conn.executeInsert:" + ex.getMessage());
			return ex.getMessage();
		}
		return null;
	}

	public void executeUpdate(String sql) {
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("conn.executeUpdate: " + ex.getMessage());

		}
	}

	public String executeDelete(String sql) {
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("conn.executeDelete: " + ex.getMessage());
			return ex.getMessage();
		}
		return null;
	}

	public void destroy() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			System.err.println("conn.executeDelete: " + ex.getMessage());
		}

	}
}
