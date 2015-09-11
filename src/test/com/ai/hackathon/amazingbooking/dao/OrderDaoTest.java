/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.ai.hackathon.amazingbooking.bean.OrderBean;
import com.ai.hackathon.amazingbooking.utils.OrderUtils;

/***************************************************************************
 *<PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.dao
 * 
 *  File Name       : OrderDaoTest.java
 * 
 *  Creation Date   : Sep 11, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : OrderDaoTest
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/

public class OrderDaoTest {
    
    private String fileName = "C:/Users/Administrator/git/AmazingBooking/Quick Booking-V1.1.xlsx";
    
    @Test
    public void testSave() throws IOException, SQLException {
        FileInputStream fis = new FileInputStream(fileName);
        OrderBean orderBean = OrderUtils.toOrderBean(fis);
        
        OrderDao orderDao = new OrderDao();
        orderBean = orderDao.save(orderBean);
        
        System.out.println(orderBean);
    }

}
