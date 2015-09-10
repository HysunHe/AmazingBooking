/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with AsiaInspection. This 
 * information shall not be distributed or copied without written 
 * permission from the AsiaInspection.
 *
 ***************************************************************************/

package com.ai.hackathon.amazingbooking.bean;

import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 *<PRE>
 *  Project Name    : amazing-booking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.bean
 * 
 *  File Name       : OrderBean.java
 * 
 *  Creation Date   : Sep 10, 2015
 * 
 *  Author          : Alva Xie
 * 
 *  Purpose         : Order Bean
 * 
 * 
 *  History         : TODO
 * 
 *</PRE>
 ***************************************************************************/

public class OrderBean implements Serializable {

    private static final long serialVersionUID = -111656868412691227L;
    
    private String clientRefNb;
    private Date expectedInspDate;
    private Date expectedShipDate;
    private String vendorName;
    private String supplierMgrName;
    private String supplierMgrEmail;
    private String supplierMgrNumber;
    private String supplierMgrMobile;
    private String supplierAddress;
    private String supplierCity;
    private String supplierCountry;
    private String prodName;
    private String prodType;
    private String prodQuantity;
    private String prodReference;
    private String poNumber;
    private String orderProdSample;
    private String refSampleComments;
    private String colorDes;
    private String dimensionDesc;
    private String logoDes;
    private String packingComments;
    private String shippingMarks;
    private String additionalComments;
    private String aqlCritical;
    private String aqlMajor;
    private String aqlMinor;
    private String collectLtSample;
    private String collectLtSampleComments;
    private String collectProdSample;
    private String collectProdSampleComments;
    
    public String getClientRefNb() {
        return clientRefNb;
    }
    
    public void setClientRefNb(String clientRefNb) {
        this.clientRefNb = clientRefNb;
    }
    
    public Date getExpectedInspDate() {
        return expectedInspDate;
    }
    
    public void setExpectedInspDate(Date expectedInspDate) {
        this.expectedInspDate = expectedInspDate;
    }
    
    public Date getExpectedShipDate() {
        return expectedShipDate;
    }
    
    public void setExpectedShipDate(Date expectedShipDate) {
        this.expectedShipDate = expectedShipDate;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getSupplierMgrName() {
        return supplierMgrName;
    }
    
    public void setSupplierMgrName(String supplierMgrName) {
        this.supplierMgrName = supplierMgrName;
    }
    
    public String getSupplierMgrEmail() {
        return supplierMgrEmail;
    }
    
    public void setSupplierMgrEmail(String supplierMgrEmail) {
        this.supplierMgrEmail = supplierMgrEmail;
    }
    
    public String getSupplierMgrNumber() {
        return supplierMgrNumber;
    }
    
    public void setSupplierMgrNumber(String supplierMgrNumber) {
        this.supplierMgrNumber = supplierMgrNumber;
    }
    
    public String getSupplierMgrMobile() {
        return supplierMgrMobile;
    }
    
    public void setSupplierMgrMobile(String supplierMgrMobile) {
        this.supplierMgrMobile = supplierMgrMobile;
    }
    
    public String getSupplierAddress() {
        return supplierAddress;
    }
    
    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }
    
    public String getSupplierCity() {
        return supplierCity;
    }
    
    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }
    
    public String getSupplierCountry() {
        return supplierCountry;
    }
    
    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }
    
    public String getProdName() {
        return prodName;
    }
    
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    
    public String getProdType() {
        return prodType;
    }
    
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    
    public String getProdQuantity() {
        return prodQuantity;
    }
    
    public void setProdQuantity(String prodQuantity) {
        this.prodQuantity = prodQuantity;
    }
    
    public String getProdReference() {
        return prodReference;
    }
    
    public void setProdReference(String prodReference) {
        this.prodReference = prodReference;
    }
    
    public String getPoNumber() {
        return poNumber;
    }
    
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
    
    public String getOrderProdSample() {
        return orderProdSample;
    }
    
    public void setOrderProdSample(String orderProdSample) {
        this.orderProdSample = orderProdSample;
    }
    
    public String getRefSampleComments() {
        return refSampleComments;
    }
    
    public void setRefSampleComments(String refSampleComments) {
        this.refSampleComments = refSampleComments;
    }
    
    public String getColorDes() {
        return colorDes;
    }
    
    public void setColorDes(String colorDes) {
        this.colorDes = colorDes;
    }
    
    public String getDimensionDesc() {
        return dimensionDesc;
    }
    
    public void setDimensionDesc(String dimensionDesc) {
        this.dimensionDesc = dimensionDesc;
    }
    
    public String getLogoDes() {
        return logoDes;
    }
    
    public void setLogoDes(String logoDes) {
        this.logoDes = logoDes;
    }
    
    public String getPackingComments() {
        return packingComments;
    }
    
    public void setPackingComments(String packingComments) {
        this.packingComments = packingComments;
    }
    
    public String getShippingMarks() {
        return shippingMarks;
    }
    
    public void setShippingMarks(String shippingMarks) {
        this.shippingMarks = shippingMarks;
    }
    
    public String getAdditionalComments() {
        return additionalComments;
    }
    
    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
    
    public String getAqlCritical() {
        return aqlCritical;
    }
    
    public void setAqlCritical(String aqlCritical) {
        this.aqlCritical = aqlCritical;
    }
    
    public String getAqlMajor() {
        return aqlMajor;
    }
    
    public void setAqlMajor(String aqlMajor) {
        this.aqlMajor = aqlMajor;
    }
    
    public String getAqlMinor() {
        return aqlMinor;
    }
    
    public void setAqlMinor(String aqlMinor) {
        this.aqlMinor = aqlMinor;
    }
    
    public String getCollectLtSample() {
        return collectLtSample;
    }
    
    public void setCollectLtSample(String collectLtSample) {
        this.collectLtSample = collectLtSample;
    }
    
    public String getCollectLtSampleComments() {
        return collectLtSampleComments;
    }
    
    public void setCollectLtSampleComments(String collectLtSampleComments) {
        this.collectLtSampleComments = collectLtSampleComments;
    }
    
    public String getCollectProdSample() {
        return collectProdSample;
    }
    
    public void setCollectProdSample(String collectProdSample) {
        this.collectProdSample = collectProdSample;
    }
    
    public String getCollectProdSampleComments() {
        return collectProdSampleComments;
    }
    
    public void setCollectProdSampleComments(String collectProdSampleComments) {
        this.collectProdSampleComments = collectProdSampleComments;
    }
    
    @Override
    public String toString() {
        return "OrderBean [clientRefNb=" + clientRefNb + ", expectedInspDate="
                + expectedInspDate + ", expectedShipDate=" + expectedShipDate
                + ", vendorName=" + vendorName + ", supplierMgrName="
                + supplierMgrName + ", supplierMgrEmail=" + supplierMgrEmail
                + ", supplierMgrNumber=" + supplierMgrNumber
                + ", supplierMgrMobile=" + supplierMgrMobile
                + ", supplierAddress=" + supplierAddress + ", supplierCity="
                + supplierCity + ", supplierCountry=" + supplierCountry
                + ", prodName=" + prodName + ", prodType=" + prodType
                + ", prodQuantity=" + prodQuantity + ", prodReference="
                + prodReference + ", poNumber=" + poNumber
                + ", orderProdSample=" + orderProdSample
                + ", refSampleComments=" + refSampleComments + ", colorDes="
                + colorDes + ", dimensionDesc=" + dimensionDesc + ", logoDes="
                + logoDes + ", packingComments=" + packingComments
                + ", shippingMarks=" + shippingMarks + ", additionalComments="
                + additionalComments + ", aqlCritical=" + aqlCritical
                + ", aqlMajor=" + aqlMajor + ", aqlMinor=" + aqlMinor
                + ", collectLtSample=" + collectLtSample
                + ", collectLtSampleComments=" + collectLtSampleComments
                + ", collectProdSample=" + collectProdSample
                + ", collectProdSampleComments=" + collectProdSampleComments
                + "]";
    }

}
