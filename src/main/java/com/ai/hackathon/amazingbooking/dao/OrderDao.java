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
import com.ai.commons.StringUtils;
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

			saveProduct(conn, orderBean);
			
			saveSupplier(conn, orderBean);
			
            saveOrderManDay(conn, orderBean);
            
            saveOrderCharge(conn, orderBean);			

            conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.setAutoCommit(autoCommit);

			DBUtil.close(conn);
		}

		return orderBean;
	}

	private void saveOrderGeneralInfo(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;
		
		int i = 1;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_GENERAL_INFO));
			pstat.setString(i++, "20"); // STATUS
			pstat.setString(i++, orderBean.getOrderNo()); // ORDER_NUMBER
			pstat.setString(i++, orderBean.getClientRefNb()); // CLIENT_REF_NB
			pstat.setString(i++, StringUtils.EMPTY); // ORDER_PLACER
			pstat.setDate(i++, new Date(orderBean.getCreateTime().getTime())); // BOOKING_DATE
			pstat.setString(i++, StringUtils.EMPTY); // SIC
			pstat.setString(i++, StringUtils.EMPTY); // SERVICE_TYPE
			pstat.setString(i++, StringUtils.EMPTY); // PROD_CATEGORY_AI
			pstat.setString(i++, StringUtils.EMPTY); // PROD_FAMILY_AI
			pstat.setString(i++, StringUtils.EMPTY); // PROD_CATEGORY_CLIENT
			pstat.setString(i++, StringUtils.EMPTY); // PROD_FAMILY_CLIENT
			pstat.setString(i++, StringUtils.EMPTY); // TOOLS
			pstat.setString(i++, StringUtils.EMPTY); // PROJECT_LEADER
			pstat.setString(i++, StringUtils.EMPTY); // PROTO_SUPERVISOR
			pstat.setString(i++, StringUtils.EMPTY); // PROTO_SUPERVISOR_2
			pstat.setString(i++, StringUtils.EMPTY); // REPORT_APPROVER
			pstat.setString(i++, StringUtils.EMPTY); // ITL_MANAGER
			pstat.setString(i++, StringUtils.EMPTY); // DEDICATED_INSPECTOR
			pstat.setString(i++, StringUtils.EMPTY); // BOOK_FROM_PRE_ORDER
			pstat.setString(i++, StringUtils.EMPTY); // INSPECTION_RATING
			pstat.setString(i++, StringUtils.EMPTY); // RATING_COMMENTS
			pstat.setString(i++, StringUtils.EMPTY); // IC_NEEDED
			pstat.setString(i++, StringUtils.EMPTY); // LC_NUMBER
			pstat.setString(i++, StringUtils.EMPTY); // LC_APP_NAME
			pstat.setString(i++, StringUtils.EMPTY); // LC_APP_ADDRESS
			pstat.setString(i++, StringUtils.EMPTY); // LC_BENE_NAME
			pstat.setString(i++, StringUtils.EMPTY); // LC_BENE_ADDRESS
			pstat.setDate(i++, new Date(orderBean.getExpectedInspDate().getTime())); // EXPECTED_INSP_DATE
			pstat.setNull(i++,  java.sql.Types.DATE); // ACTUAL_INSP_DATE
			pstat.setDate(i++, new Date(orderBean.getExpectedShipDate().getTime())); // EXPECTED_SHIP_DATE
			pstat.setString(i++, StringUtils.EMPTY); // COPY_ALL_MAIL_TO
			pstat.setString(i++, StringUtils.EMPTY); // COPY_REPORT_MAIL_TO
			pstat.setString(i++, StringUtils.EMPTY); // CONTAINER20
			pstat.setString(i++, StringUtils.EMPTY); // CONTAINER40
			pstat.setString(i++, StringUtils.EMPTY); // CONTAINER40HQ
			pstat.setDate(i++, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(i++, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(i++, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(i++, StringUtils.EMPTY); // CALLED_INSPECTOR
			pstat.setString(i++, StringUtils.EMPTY); // INSEPCTOR_CALLED_BY
			pstat.setString(i++, StringUtils.EMPTY); // INSEPCTOR_CALL_RESULT
			pstat.setString(i++, StringUtils.EMPTY); // INSEPCTOR_CALL_COMMENTS
			pstat.setString(i++, StringUtils.EMPTY); // CALLED_FACTORY
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_CALLED_BY
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_CALL_RESULT
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_CALL_COMMENTS
			pstat.setString(i++, orderBean.getCustId()); // CUST_ID

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	private void saveOrderExtFields(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;
		
		int i = 1;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_EXT_FIELDS));
			pstat.setString(i++, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(i++, StringUtils.EMPTY); // PRODUCT_ID_LIST
			pstat.setString(i++, StringUtils.EMPTY); // IS_RE_INSPECTION
			pstat.setString(i++, StringUtils.EMPTY); // ORIGINAL_NB_REINSP
			pstat.setString(i++, StringUtils.EMPTY); // IS_SPLIT_FROM
			pstat.setString(i++, StringUtils.EMPTY); // ORIGINAL_NB_SPLIT
			pstat.setString(i++, StringUtils.EMPTY); // IS_SPLIT_TO
			pstat.setString(i++, StringUtils.EMPTY); // NEW_NB_SPLIT
			pstat.setNull(i++,  java.sql.Types.DATE); // FREEZE_DATE
			pstat.setString(i++, StringUtils.EMPTY); // POSTPONE_4PM
			pstat.setString(i++, StringUtils.EMPTY); // IS_NEW_SUPPLIER
			pstat.setString(i++, StringUtils.EMPTY); // IS_NEW_FIRST
			pstat.setString(i++, StringUtils.EMPTY); // IS_INSP_DEDICATED
			pstat.setString(i++, StringUtils.EMPTY); // AI_CANCELSON_REASON
			pstat.setString(i++, StringUtils.EMPTY); // SAMPLE_SIZE_TOTAL
			pstat.setString(i++, StringUtils.EMPTY); // IS_ASK_MORE_INFO
			pstat.setString(i++, StringUtils.EMPTY); // ORIGINAL_SIC
			pstat.setString(i++, StringUtils.EMPTY); // APPROVE_REFERENCES
			pstat.setString(i++, StringUtils.EMPTY); // ASK_NUMBER_OF_REFERENCES
			pstat.setDate(i++, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(i++, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(i++, StringUtils.EMPTY); // CANCELLED_BY
			pstat.setNull(i++,  java.sql.Types.DATE); // CANCELLATION_DATE
			pstat.setString(i++, StringUtils.EMPTY); // RFQ_NUMBER
			pstat.setString(i++, StringUtils.EMPTY); // IRP_STATUS
			pstat.setString(i++, StringUtils.EMPTY); // CFM_BY_PHONE_BY
			pstat.setString(i++, StringUtils.EMPTY); // CFM_BY_PHONE_PHONENB
			pstat.setNull(i++,  java.sql.Types.DATE); // LAST_FREEZE_TIME
			pstat.setString(i++, StringUtils.EMPTY); // SHIPPED_SIGN
			pstat.setString(i++, StringUtils.EMPTY); // INSP_DATE_CONFIRMED_BY
			pstat.setNull(i++,  java.sql.Types.NUMERIC); // NB_OF_UPDATE_INSP_DATE

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	private void saveOrderFactory(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;
		
		int i = 1;

		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_ORDER_FACTORY));
			pstat.setString(i++, orderBean.getOrderId()); // ORDER_ID
			pstat.setString(i++, orderBean.getVendorName()); // FACTORY_NAME
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_NAME_CN
			pstat.setString(i++, orderBean.getSupplierMgrName()); // FACTORY_MGR_NAME
			pstat.setString(i++, orderBean.getSupplierMgrMobile()); // FACTORY_MGR_MOBILE
			pstat.setString(i++, orderBean.getSupplierMgrNumber()); // FACTORY_MGR_NUMBER
			pstat.setString(i++, orderBean.getSupplierMgrEmail()); // FACTORY_MGR_EMAIL
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_OTHER_NAME
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_OTHER_MOBILE
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_OTHER_NUMBER
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_OTHER_EMAIL
			pstat.setString(i++, orderBean.getSupplierAddress()); // FACTORY_ADDRESS
			pstat.setString(i++, orderBean.getSupplierCity()); // FACTORY_CITY
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_PROVINCE
			pstat.setString(i++, orderBean.getSupplierCountry()); // FACTORY_COUNTRY
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_CONTINENT
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_POSTCODE
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_PRODUCTLINES
			pstat.setString(i++, StringUtils.EMPTY); // DEPARTURE_CITY
			pstat.setNull(i++,  java.sql.Types.DATE); // ARRIVAL_TIME
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_AI_OFFICE
			pstat.setString(i++, StringUtils.EMPTY); // NB_OF_WORKERS
			pstat.setString(i++, StringUtils.EMPTY); // COMMENTS
			pstat.setString(i++, StringUtils.EMPTY); // COORDINATION_REMARK
			pstat.setString(i++, StringUtils.EMPTY); // IS_MUTI_LOCATION
			pstat.setDate(i++, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(i++, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(i++, StringUtils.EMPTY); // FACTORY_ID

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	/**
	 * @param conn
	 * @param orderBean
	 * @throws SQLException
	 */
	private void saveProduct(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;
		orderBean.setProdId(IDGenerator.uuid());
		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_PRODUCT));
			pstat.setString(1, orderBean.getProdId());
			pstat.setString(2, orderBean.getOrderId());
			pstat.setString(3, orderBean.getProdType());
			pstat.setString(4, orderBean.getProdName());
			pstat.setString(5, orderBean.getProdReference());
			pstat.setString(6, orderBean.getProdQuantity());
			pstat.setString(7, orderBean.getPoNumber());
			pstat.setDate(8, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(9, orderBean.getUpdateTime()); // UPDATE_TIME

			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}

	/**
	 * @param conn
	 * @param orderBean
	 * @throws SQLException
	 */
	private void saveSupplier(Connection conn, OrderBean orderBean)
			throws SQLException {
		PreparedStatement pstat = null;
		orderBean.setProdId(IDGenerator.uuid());
		try {
			pstat = conn.prepareStatement(SqlConfigLoader
					.findSql(Consts.INSERT_SUPPLIER));
			pstat.setString(1, orderBean.getOrderId());
			pstat.setString(2, orderBean.getSupplierMgrName());
			pstat.setString(3, orderBean.getSupplierMgrMobile());
			pstat.setString(4, orderBean.getSupplierMgrNumber());
			pstat.setString(5, orderBean.getSupplierMgrEmail());
			pstat.setString(6, orderBean.getSupplierAddress());
			pstat.setString(7, orderBean.getSupplierCity());
			pstat.setDate(8, orderBean.getCreateTime()); // CREATE_TIME
			pstat.setDate(9, orderBean.getUpdateTime()); // UPDATE_TIME
			pstat.setString(10, "A906287D40D96691482572E50047E3E2");
			pstat.execute();
		} finally {
			DBUtil.close(pstat);
		}
	}
	
    private void saveOrderManDay(Connection conn, OrderBean orderBean)
            throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement(SqlConfigLoader
                    .findSql(Consts.INSERT_ORDER_MAN_DAY));
            pstat.setString(1, orderBean.getOrderId()); // ORDER_ID
            pstat.setString(2, StringUtils.EMPTY); // CLIENT_MD
            pstat.setString(3, StringUtils.EMPTY); // SUPERVISOR_MD
            pstat.setString(4, StringUtils.EMPTY); // REAL_MD
            pstat.setString(5, StringUtils.EMPTY); // MD_STATUS
            pstat.setDate(6, orderBean.getCreateTime()); // CREATE_TIME
            pstat.setDate(7, orderBean.getUpdateTime()); // UPDATE_TIME
            pstat.setString(8, StringUtils.EMPTY); // MORE_ITEMS
            pstat.setString(9, StringUtils.EMPTY); // MDP_LOG_TOTAL
            
            pstat.execute();
        } finally {
            DBUtil.close(pstat);
        }
    }
    
    private void saveOrderCharge(Connection conn, OrderBean orderBean)
            throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement(SqlConfigLoader
                    .findSql(Consts.INSERT_ORDER_CHARGE));
            pstat.setString(1, orderBean.getOrderId()); // ORDER_ID
            pstat.setString(2, StringUtils.EMPTY); // MD_RATE
            pstat.setString(3, StringUtils.EMPTY); // NB_OF_EXTRA_REPORT
            pstat.setString(4, StringUtils.EMPTY); // EXTRA_REPORT_RATE
            pstat.setString(5, StringUtils.EMPTY); // EXPRESS_BOOKING_RATE
            pstat.setString(6, StringUtils.EMPTY); // EXPRESS_BOOKING_FEE
            pstat.setString(7, StringUtils.EMPTY); // SAMPLE_RATE
            pstat.setString(8, StringUtils.EMPTY); // CLIENT_TYPE
            pstat.setString(9, StringUtils.EMPTY); // INSPECTION_CHARGE
            pstat.setString(10, StringUtils.EMPTY); // EXTRA_REPORT_CHARGE
            pstat.setString(11, StringUtils.EMPTY); // SAMPLE_CHARGE
            pstat.setString(12, StringUtils.EMPTY); // OFFLINE_CHARGE
            pstat.setString(13, StringUtils.EMPTY); // TOTAL_CHARGE
            pstat.setDate(14, orderBean.getCreateTime()); // CREATE_TIME
            pstat.setDate(15, orderBean.getUpdateTime()); // UPDATE_TIME
            
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
