/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

import com.ai.commons.DateUtils;
import com.ai.commons.IDGenerator;
import com.ai.commons.SqlConfigLoader;
import com.ai.hackathon.amazingbooking.bean.OrderBean;
import com.ai.hackathon.amazingbooking.consts.Consts;
import com.ai.hackathon.amazingbooking.utils.DBUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.dao
 * 
 *  File Name       : OrderDao.java
 * 
 *  Creation Date   : Sep 10, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : Order Dao
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class OrderDao {

	public OrderBean save(OrderBean orderBean) throws SQLException {
		orderBean.setOrderId(IDGenerator.uuid());
		orderBean.setOrderNo(generateOrderNo());
		// Login: mabur
		orderBean.setCustId("75EAA8F9E7834D39C125757A002E21AD");

		Timestamp ts = DateUtils.currentSQLTimestamp();
		Date date = new Date(ts.getTime());
		orderBean.setCreateTime(date);
		orderBean.setUpdateTime(date);

		Connection conn = DBUtil.getConnection();

		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);

		try {
			saveOrderGeneralInfo(conn, orderBean);

			saveOrderExtFields(conn, orderBean);

			saveOrderFactory(conn, orderBean);
		} catch (SQLException e) {
			conn.rollback();
		} finally {
			conn.commit();

			conn.setAutoCommit(autoCommit);

			DBUtil.close(conn);
		}

		return orderBean;
	}

	private void saveOrderGeneralInfo(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_GENERAL_INFO));
			pstat.setString(1, null); // STATUS
			pstat.setString(2, orderBean.getOrderNo()); // ORDER_NUMBER
			pstat.setString(3, orderBean.getClientRefNb()); // CLIENT_REF_NB
			pstat.setString(4, null); // ORDER_PLACER
			pstat.setDate(5, null); // BOOKING_DATE
			pstat.setString(6, null); // SIC
			pstat.setString(7, null); // SERVICE_TYPE
			pstat.setString(8, null); // PROD_CATEGORY_AI
			pstat.setString(9, null); // PROD_FAMILY_AI
			pstat.setString(10, null); // PROD_CATEGORY_CLIENT
			pstat.setString(11, null); // PROD_FAMILY_CLIENT
			pstat.setString(12, null); // TOOLS
			pstat.setString(13, null); // PROJECT_LEADER
			pstat.setString(14, null); // PROTO_SUPERVISOR
			pstat.setString(15, null); // PROTO_SUPERVISOR_2
			pstat.setString(16, null); // REPORT_APPROVER
			pstat.setString(17, null); // ITL_MANAGER
			pstat.setString(18, null); // DEDICATED_INSPECTOR
			pstat.setString(19, null); // BOOK_FROM_PRE_ORDER
			pstat.setString(20, null); // INSPECTION_RATING
			pstat.setString(21, null); // RATING_COMMENTS
			pstat.setString(22, null); // IC_NEEDED
			pstat.setString(23, null); // LC_NUMBER
			pstat.setString(24, null); // LC_APP_NAME
			pstat.setString(25, null); // LC_APP_ADDRESS
			pstat.setString(26, null); // LC_BENE_NAME
			pstat.setString(27, null); // LC_BENE_ADDRESS
			pstat.setDate(28, new Date(orderBean.getExpectedInspDate()
					.getTime())); // EXPECTED_INSP_DATE
			pstat.setDate(29, null); // ACTUAL_INSP_DATE
			pstat.setDate(29, new Date(orderBean.getExpectedShipDate()
					.getTime())); // EXPECTED_SHIP_DATE
			pstat.setString(30, null); // COPY_ALL_MAIL_TO
			pstat.setString(31, null); // COPY_REPORT_MAIL_TO
			pstat.setString(32, null); // CONTAINER20
			pstat.setString(33, null); // CONTAINER40
			pstat.setString(34, null); // CONTAINER40HQ
			pstat.setDate(35, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(36, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(37, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(38, null); // CALLED_INSPECTOR
			pstat.setString(39, null); // INSEPCTOR_CALLED_BY
			pstat.setString(40, null); // INSEPCTOR_CALL_RESULT
			pstat.setString(41, null); // INSEPCTOR_CALL_COMMENTS
			pstat.setString(42, null); // CALLED_FACTORY
			pstat.setString(43, null); // FACTORY_CALLED_BY
			pstat.setString(44, null); // FACTORY_CALL_RESULT
			pstat.setString(45, null); // FACTORY_CALL_COMMENTS
			pstat.setString(46, null); // CUST_ID

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	private void saveOrderExtFields(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_EXT_FIELDS));
			pstat.setString(1, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(2, null); // PRODUCT_ID_LIST
			pstat.setString(3, null); // IS_RE_INSPECTION
			pstat.setString(4, null); // ORIGINAL_NB_REINSP
			pstat.setString(5, null); // IS_SPLIT_FROM
			pstat.setString(6, null); // ORIGINAL_NB_SPLIT
			pstat.setString(7, null); // IS_SPLIT_TO
			pstat.setString(8, null); // NEW_NB_SPLIT
			pstat.setDate(9, null); // FREEZE_DATE
			pstat.setString(10, null); // POSTPONE_4PM
			pstat.setString(11, null); // IS_NEW_SUPPLIER
			pstat.setString(12, null); // IS_NEW_FIRST
			pstat.setString(13, null); // IS_INSP_DEDICATED
			pstat.setString(14, null); // AI_CANCELSON_REASON
			pstat.setString(15, null); // SAMPLE_SIZE_TOTAL
			pstat.setString(16, null); // IS_ASK_MORE_INFO
			pstat.setString(17, null); // ORIGINAL_SIC
			pstat.setString(18, null); // APPROVE_REFERENCES
			pstat.setString(19, null); // ASK_NUMBER_OF_REFERENCES
			pstat.setDate(20, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(21, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(22, null); // CANCELLED_BY
			pstat.setDate(23, null); // CANCELLATION_DATE
			pstat.setString(24, null); // RFQ_NUMBER
			pstat.setString(25, null); // IRP_STATUS
			pstat.setString(26, null); // CFM_BY_PHONE_BY
			pstat.setString(27, null); // CFM_BY_PHONE_PHONENB
			pstat.setDate(28, null); // LAST_FREEZE_TIME
			pstat.setString(29, null); // SHIPPED_SIGN
			pstat.setString(30, null); // INSP_DATE_CONFIRMED_BY
			pstat.setDate(31, null); // NB_OF_UPDATE_INSP_DATE

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	private void saveOrderFactory(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_FACTORY));
			pstat.setString(1, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(2, orderBean.getVendorName()); // FACTORY_NAME
			pstat.setString(3, null); // FACTORY_NAME_CN
			pstat.setString(4, orderBean.getSupplierMgrName()); // FACTORY_MGR_NAME
			pstat.setString(5, orderBean.getSupplierMgrMobile()); // FACTORY_MGR_MOBILE
			pstat.setString(6, orderBean.getSupplierMgrNumber()); // FACTORY_MGR_NUMBER
			pstat.setString(7, orderBean.getSupplierMgrEmail()); // FACTORY_MGR_EMAIL
			pstat.setString(8, null); // FACTORY_OTHER_NAME
			pstat.setString(9, null); // FACTORY_OTHER_MOBILE
			pstat.setString(10, null); // FACTORY_OTHER_NUMBER
			pstat.setString(11, null); // FACTORY_OTHER_EMAIL
			pstat.setString(12, orderBean.getSupplierAddress()); // FACTORY_ADDRESS
			pstat.setString(13, orderBean.getSupplierCity()); // FACTORY_CITY
			pstat.setString(14, null); // FACTORY_PROVINCE
			pstat.setString(15, orderBean.getSupplierCountry()); // FACTORY_COUNTRY
			pstat.setString(16, null); // FACTORY_CONTINENT
			pstat.setString(17, null); // FACTORY_POSTCODE
			pstat.setString(18, null); // FACTORY_PRODUCTLINES
			pstat.setString(19, null); // DEPARTURE_CITY
			pstat.setDate(20, null); // ARRIVAL_TIME
			pstat.setString(21, null); // FACTORY_AI_OFFICE
			pstat.setString(22, null); // NB_OF_WORKERS
			pstat.setString(23, null); // COMMENTS
			pstat.setString(24, null); // COORDINATION_REMARK
			pstat.setString(25, null); // IS_MUTI_LOCATION
			pstat.setDate(25, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(26, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(27, null); // FACTORY_ID

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	private String generateOrderNo() {
		String orderNo = "M-cn5-";

		Random random = new Random();
		int number = random.nextInt(9999999);

		return orderNo + number;
	}

}
