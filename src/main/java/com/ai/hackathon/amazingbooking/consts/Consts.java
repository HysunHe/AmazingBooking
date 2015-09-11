/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.consts;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/***************************************************************************
 * <PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.consts
 * 
 *  File Name       : Consts.java
 * 
 *  Creation Date   : Sep 10, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : Consts
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

public final class Consts {
	public static final String SUBJECT_KEYWORD = "QUICK BOOK ORDER THRU MAIL";
	
	public static final String INSERT_ORDER_GENERAL_INFO = "INSERT_ORDER_GENERAL_INFO";
	public static final String INSERT_ORDER_EXT_FIELDS = "INSERT_ORDER_EXT_FIELDS";
	public static final String INSERT_ORDER_FACTORY = "INSERT_ORDER_FACTORY";
	public static final String INSERT_PRODUCT = "INSERT_PRODUCT";
	public static final String INSERT_SUPPLIER = "INSERT_SUPPLIER";
    public static final String INSERT_ORDER_MAN_DAY = "INSERT_ORDER_MAN_DAY";
    public static final String INSERT_ORDER_CHARGE = "INSERT_ORDER_CHARGE";	
    public static final String INSERT_PAYMNET = "INSERT_PAYMNET";	
    public static final String INSERT_PROD_EXT = "INSERT_PROD_EXT";	
    public static final String INSERT_PROD_SPEC = "INSERT_PROD_SPEC";	
	

	public enum OrderField {
		CLIENT_REF_NB("CLIENT_REF_NB"), EXPECTED_INSP_DATE("EXPECTED_INSP_DATE"), EXPECTED_SHIP_DATE(
				"EXPECTED_SHIP_DATE"), VENDOR_NAME("VENDOR_NAME"), SUPPLIER_MGR_NAME(
				"SUPPLIER_MGR_NAME"), SUPPLIER_MGR_EMAIL("SUPPLIER_MGR_EMAIL"), SUPPLIER_MGR_NUMBER(
				"SUPPLIER_MGR_NUMBER"), SUPPLIER_MGR_MOBILE(
				"SUPPLIER_MGR_MOBILE"), SUPPLIER_ADDRESS("SUPPLIER_ADDRESS"), SUPPLIER_CITY(
				"SUPPLIER_CITY"), SUPPLIER_COUNTRY("SUPPLIER_COUNTRY"), PROD_NAME(
				"PROD_NAME"), PROD_TYPE("PROD_TYPE"), PROD_QUANTITY(
				"PROD_QUANTITY"), PROD_REFERENCE("PROD_REFERENCE"), PO_NUMBER(
				"PO_NUMBER"), ORDER_PROD_SAMPLE("ORDER_PROD_SAMPLE"), REF_SAMPLE_COMMENTS(
				"REF_SAMPLE_COMMENTS"), COLOR_DES("COLOR_DES"), DIMENSION_DESC(
				"DIMENSION_DESC"), LOGO_DES("LOGO_DES"), PACKING_COMMENTS(
				"PACKING_COMMENTS"), SHIPPING_MARKS("SHIPPING_MARKS"), ADDITIONAL_COMMENTS(
				"ADDITIONAL_COMMENTS"), AQL_CRITICAL("AQL_CRITICAL"), AQL_MAJOR(
				"AQL_MAJOR"), AQL_MINOR("AQL_MINOR"), COLLECT_LT_SAMPLE(
				"COLLECT_LT_SAMPLE"), COLLECT_LT_SAMPLE_COMMENTS(
				"COLLECT_LT_SAMPLE_COMMENTS"), COLLECT_PROD_SAMPLE(
				"COLLECT_PROD_SAMPLE"), COLLECT_PROD_SAMPLE_COMMENTS(
				"COLLECT_PROD_SAMPLE_COMMENTS"),

		UNKNOWN("UNKNOWN");

		private String value;

		OrderField(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static OrderField getValueOf(String value) {
			for (OrderField orderField : OrderField.values()) {
				if (Objects.equal(orderField.getValue(), value)) {
					return orderField;
				}
			}

			return UNKNOWN;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(OrderField.class)
					.add("value", value).toString();
		}

	}

}
