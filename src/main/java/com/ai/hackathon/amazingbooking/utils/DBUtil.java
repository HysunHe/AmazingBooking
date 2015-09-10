package com.ai.hackathon.amazingbooking.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class DBUtil {

	private static Log log = LogFactory.getLog(DBUtil.class.getName());

	private static ComboPooledDataSource dataSource;

	private static final String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String dbUrl = "jdbc:oracle:thin:@192.168.0.6:1521:aidev";
	private static final String dbUser = "aidatauser";
	private static final String dbPass = "tiger";

	private DBUtil() {
	}

	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);

			log.debug("connection:" + conn);
		} catch (Exception e) {
			log.error("Error:", e);
		}

		return conn;
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {

			dataSource = new ComboPooledDataSource();
			try {
				dataSource.setDriverClass(dbDriver);
			} catch (PropertyVetoException e) {
				log.error("Error: ", e);
			}
			dataSource.setJdbcUrl(dbUrl);
			dataSource.setUser(dbUser);
			dataSource.setPassword(dbPass);

			dataSource.setInitialPoolSize(1);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(500);
			dataSource.setAcquireIncrement(0);
			dataSource.setMaxIdleTime(300);

			// close Statement, PreparedStatement cache
			dataSource.setMaxStatements(0);
			dataSource.setMaxStatementsPerConnection(0);

			dataSource.setIdleConnectionTestPeriod(100);
		}

		return dataSource;
	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error("Error:", e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Error:", e);
			}
		}
	}

	public static void close(Statement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			log.error("Error:", e);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				log.error("Error:", e);
			}
		}
	}

}
