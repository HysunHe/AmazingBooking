/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import com.ai.hackathon.amazingbooking.bean.OrderBean;

/***************************************************************************
 *<PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.utils
 * 
 *  File Name       : OrderUtilsTest.java
 * 
 *  Creation Date   : Sep 10, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : Order Utils Test
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/
public class OrderUtilsTest {
    
    private String fileName = "C:/Users/Administrator/git/AmazingBooking/Quick Booking-V1.1.xlsx";

    @Test
    public void testToOrderBean() throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(fileName);
        OrderBean orderBean = OrderUtils.toOrderBean(fis);
        System.out.println(orderBean);
    }

}
