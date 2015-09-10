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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ai.commons.SqlConfigLoader;
import com.ai.hackathon.amazingbooking.bean.OrderBean;
import com.ai.hackathon.amazingbooking.consts.Consts;
import com.ai.hackathon.amazingbooking.utils.DBUtil;

/***************************************************************************
 *<PRE>
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
 *</PRE>
 ***************************************************************************/
public class OrderDao {

    public String save(OrderBean orderBean) throws SQLException {
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
        
        return "M-cn5-1431456";
    }
    
    private void saveOrderGeneralInfo(Connection conn, OrderBean orderBean)
            throws SQLException {
        PreparedStatement pstat = null;

        try {
            pstat = conn.prepareStatement(SqlConfigLoader
                    .findSql(Consts.INSERT_ORDER_GENERAL_INFO));
            pstat.setString(1, "");
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
            pstat.setString(1, "");
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
            pstat.setString(1, "");
            pstat.execute();
        } finally {
            DBUtil.close(pstat);
        }
    }
    
    
}
