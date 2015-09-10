/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ai.commons.StringUtils;
import com.ai.hackathon.amazingbooking.bean.OrderBean;
import com.ai.hackathon.amazingbooking.consts.Consts;
import com.ai.hackathon.amazingbooking.consts.Consts.OrderField;

/***************************************************************************
 *<PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.utils
 * 
 *  File Name       : ExcelUtils.java
 * 
 *  Creation Date   : Sep 10, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : Excel Utils
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/

public class OrderUtils {

    public static OrderBean toOrderBean(InputStream is) throws IOException,
            ParseException {
        OrderBean orderBean = new OrderBean();

        Workbook wb = null;

        try {
            wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 0, size = sheet.getLastRowNum(); i <= size; i++) {
                Row row = sheet.getRow(i);

                Cell cell = row.getCell(2); // the hidden cell
                String hiddenFieldValue = cell.getStringCellValue();

                OrderField orderField = Consts.OrderField
                        .getValueOf(hiddenFieldValue.trim());

                cell = row.getCell(1);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                    orderBean = setProperty(orderBean, orderField, cell);
                }
            }
        } finally {
            if (wb != null) {
                wb.close();
            }

            IOUtils.closeQuietly(is);
        }

        return orderBean;
    }
    
    private static OrderBean setProperty(OrderBean orderBean,
            OrderField orderField, Cell cell) throws ParseException {
        switch (orderField) {
        case CLIENT_REF_NB:
            orderBean.setClientRefNb(getCellValue(cell));
            break;

        case EXPECTED_INSP_DATE:
//            Date expectedInspDate = DateUtils.parseDate(value,
//                    new String[] { DateUtils.Format.SAMPLE_INSP_DATE_FORMAT
//                            .getValue() });
            orderBean.setExpectedInspDate(cell.getDateCellValue());
            break;

        case EXPECTED_SHIP_DATE:
//            Date expectedShipDate = DateUtils.parseDate(value,
//                    new String[] { DateUtils.Format.SAMPLE_INSP_DATE_FORMAT
//                            .getValue() });
            orderBean.setExpectedShipDate(cell.getDateCellValue());
            ;
            break;

        case VENDOR_NAME:
            orderBean.setVendorName(getCellValue(cell));
            break;

        case SUPPLIER_MGR_NAME:
            orderBean.setSupplierMgrName(getCellValue(cell));
            break;

        case SUPPLIER_MGR_EMAIL:
            orderBean.setSupplierMgrEmail(getCellValue(cell));
            break;

        case SUPPLIER_MGR_NUMBER:
            orderBean.setSupplierMgrNumber(getCellValue(cell));
            break;

        case SUPPLIER_MGR_MOBILE:
            orderBean.setSupplierMgrMobile(getCellValue(cell));
            break;

        case SUPPLIER_ADDRESS:
            orderBean.setSupplierAddress(getCellValue(cell));
            break;

        case SUPPLIER_CITY:
            orderBean.setSupplierCity(getCellValue(cell));
            break;

        case SUPPLIER_COUNTRY:
            orderBean.setSupplierCountry(getCellValue(cell));
            break;

        case PROD_NAME:
            orderBean.setProdName(getCellValue(cell));
            break;

        case PROD_TYPE:
            orderBean.setProdType(getCellValue(cell));
            break;

        case PROD_QUANTITY:
            orderBean.setProdQuantity(getCellValue(cell));
            break;

        case PROD_REFERENCE:
            orderBean.setProdReference(getCellValue(cell));
            break;

        case PO_NUMBER:
            orderBean.setPoNumber(getCellValue(cell));
            break;

        case ORDER_PROD_SAMPLE:
            orderBean.setOrderProdSample(getCellValue(cell));
            break;

        case REF_SAMPLE_COMMENTS:
            orderBean.setRefSampleComments(getCellValue(cell));
            break;

        case COLOR_DES:
            orderBean.setColorDes(getCellValue(cell));
            break;

        case DIMENSION_DESC:
            orderBean.setDimensionDesc(getCellValue(cell));
            break;

        case LOGO_DES:
            orderBean.setLogoDes(getCellValue(cell));
            break;

        case PACKING_COMMENTS:
            orderBean.setPackingComments(getCellValue(cell));
            break;

        case SHIPPING_MARKS:
            orderBean.setShippingMarks(getCellValue(cell));
            break;

        case ADDITIONAL_COMMENTS:
            orderBean.setAdditionalComments(getCellValue(cell));
            break;

        case AQL_CRITICAL:
            orderBean.setAqlCritical(getCellValue(cell));
            break;

        case AQL_MAJOR:
            orderBean.setAqlMajor(getCellValue(cell));
            break;

        case AQL_MINOR:
            orderBean.setAqlMinor(getCellValue(cell));
            break;

        case COLLECT_LT_SAMPLE:
            orderBean.setCollectLtSample(getCellValue(cell));
            break;

        case COLLECT_LT_SAMPLE_COMMENTS:
            orderBean.setCollectLtSampleComments(getCellValue(cell));
            break;

        case COLLECT_PROD_SAMPLE:
            orderBean.setCollectProdSample(getCellValue(cell));
            break;

        case COLLECT_PROD_SAMPLE_COMMENTS:
            orderBean.setCollectProdSampleComments(getCellValue(cell));
            break;

        default:
            break;
        }

        return orderBean;
    }
    
    private static String getCellValue(Cell cell) {
        String value = StringUtils.EMPTY;

        int type = cell.getCellType();
        switch (type) {
        case Cell.CELL_TYPE_STRING:
            value = cell.getStringCellValue();
            break;

        case Cell.CELL_TYPE_NUMERIC:
            value = String.valueOf(cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            value = String.valueOf(cell.getBooleanCellValue());
            break;
        default:
            break;
        }

        return value;
    }

    
}
