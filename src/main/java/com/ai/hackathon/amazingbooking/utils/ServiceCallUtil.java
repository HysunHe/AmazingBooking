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
import java.util.HashMap;
import java.util.Map;

import com.ai.userservice.common.bean.GetRequest;
import com.ai.userservice.common.bean.ServiceCallResult;
import com.ai.userservice.common.util.HttpUtil;

/***************************************************************************
 * <PRE>
 *  Project Name    : AmazingBooking
 * 
 *  Package Name    : com.ai.hackathon.amazingbooking.utils
 * 
 *  File Name       : ServiceCallUtil.java
 * 
 *  Creation Date   : 2015年9月11日
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public final class ServiceCallUtil {
	/**
	 * @param serviceUrl
	 * @return
	 */
	public static String callGet(String url, Map<String, String> params) {
		String responseString = "";

		// Build the GetRequest
		GetRequest getReq = GetRequest.newInstance().setUrl(url)
				.setParams(params);
		try {
			ServiceCallResult result = HttpUtil.issueGetRequest(getReq);
			// 20X - success
			if (result.getStatusCode() == 200 || result.getStatusCode() == 202) {
				responseString = result.getResponseString();
			} else {
				System.err.println("ERROR: " + result + "; ACTION: " + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// throw new ReportServiceException(e);
			return null;
		}
		return responseString;
	}

	/**
	 * @param url
	 * @param data
	 * @return
	 */
	public static boolean callPost(String url, Object data) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		try {
			ServiceCallResult result = HttpUtil.issuePostRequest(url, null,
					data);
			if (200 == result.getStatusCode() || 202 == result.getStatusCode()) {
				return true;
			} else {
				System.err.println("ERROR: " + result + "; ACTION: " + url);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
