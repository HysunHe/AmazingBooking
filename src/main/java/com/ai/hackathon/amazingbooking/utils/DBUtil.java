package com.ai.hackathon.amazingbooking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtil {
	private static final String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String dbUrl = "jdbc:oracle:thin:@192.168.0.6:1521:aidev";
	private static final String dbUser = "aidatauser";
	private static final String dbPass = "tiger";

	static {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("!!! JDBC driver not found!");
			System.exit(1);
		}
	}

	private DBUtil() {
	}

	/**
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			System.out.println("connection:" + conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param stat
	 */
	public static void close(Statement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
