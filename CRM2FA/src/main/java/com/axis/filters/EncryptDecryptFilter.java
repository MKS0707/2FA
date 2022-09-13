package com.axis.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.axis.service.impl.ApiLoggignService;


public class EncryptDecryptFilter implements Filter 
{
	private static final Logger log = LoggerFactory.getLogger(EncryptDecryptFilter.class);

	@Autowired
	private ApiLoggignService apiLoggignService;
	
	public static final String ENCRYPTION_KEY = "encryptedData";

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long currentTimeMillis = System.currentTimeMillis();
		String decryptedData = null;
		String responseData = null;
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		String ipAddress = getClientIpAddr((HttpServletRequest) request);
		log.info("Axis 2FA Request URL: {}", url);
		try {
			InputRequestWrapper wrappedRequest = new InputRequestWrapper((HttpServletRequest) request);
			OutputResponseWrapper bufferedResponse = new OutputResponseWrapper((HttpServletResponse) response);
			String body = IOUtils.toString(wrappedRequest.getReader());
			if (!"".equals(body)) {
				JSONObject oldJsonObject = new JSONObject(body);
				String encryptedInputData = oldJsonObject.get("encryptedData").toString();
				decryptedData = EncryptUtil.decryptString(encryptedInputData, "");
				Object requestObj1 = (new JSONTokener(decryptedData)).nextValue();
				if (requestObj1 instanceof JSONObject) {
					JSONObject newResponseObject = new JSONObject(decryptedData);
					decryptedData = newResponseObject.toString();
				} else if (requestObj1 instanceof JSONArray) {
					JSONArray jsonArray = new JSONArray(decryptedData);
					decryptedData = jsonArray.toString();
				}
				log.info("Axis 2FA Decrypted Request :{}", decryptedData);
				wrappedRequest.resetInputStream(decryptedData.getBytes());
				chain.doFilter((ServletRequest) wrappedRequest, (ServletResponse) bufferedResponse);
				 responseData = bufferedResponse.getResponseData();
				if (!"".equals(responseData)) {
					log.info("Axis 2FA Decrypted Response: {}", responseData);
					Object obj = (new JSONTokener(responseData)).nextValue();
					if (obj instanceof JSONObject) {
						JSONObject oldResponseObject = new JSONObject(responseData);
						String encryptedData = EncryptUtil.encryptString(oldResponseObject.toString(), "");
						JSONObject newObj = new JSONObject();
						newObj.put("encryptedData", encryptedData);
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(newObj.toString().getBytes());
						servletOutputStream.flush();
						servletOutputStream.close();
					} else if (obj instanceof JSONArray) {
						JSONArray jsonArray = new JSONArray(responseData);
						JSONArray returnArray = new JSONArray();
						for (int i = 0; i <
								jsonArray.length(); i++) {
							JSONObject jsonObjectEncoded = new JSONObject();
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							for (String key:
								jsonObject.keySet()) jsonObjectEncoded.put(key,
										EncryptUtil.encryptString(jsonObject.get(key).toString(), ""));
							returnArray.put(jsonObjectEncoded);
						}
						String encryptedData = EncryptUtil.encryptString(responseData.toString(), "");
						JSONObject newObj = new JSONObject();
						newObj.put("encryptedData", encryptedData);
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(newObj.toString().getBytes());
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				} else {
					log.info("Axis 2FA Decrypted Response: {}", "null");
				}
			} else {
				chain.doFilter((ServletRequest) wrappedRequest, (ServletResponse) bufferedResponse);
				 responseData = bufferedResponse.getResponseData();
				if (!"".equals(responseData)) {
					log.info("Axis 2FA Decrypted Response: {}", responseData);
					Object obj = (new JSONTokener(responseData)).nextValue();
					if (obj instanceof JSONObject) {
						JSONObject oldResponseObject = new JSONObject(responseData);
						String encryptedData = EncryptUtil.encryptString(oldResponseObject.toString(), "");
						JSONObject newObj = new JSONObject();
						newObj.put("encryptedData", encryptedData);
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(newObj.toString().getBytes());
						servletOutputStream.flush();
						servletOutputStream.close();
					} else if (obj instanceof JSONArray) {
						JSONArray jsonArray = new JSONArray(responseData);
						JSONArray returnArray = new JSONArray();
						for (int i = 0; i <
								jsonArray.length(); i++) {
							JSONObject jsonObjectEncoded = new JSONObject();
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							for (String key:
								jsonObject.keySet()) jsonObjectEncoded.put(key,
										EncryptUtil.encryptString(jsonObject.get(key).toString(), ""));
							returnArray.put(jsonObjectEncoded);
						}
						log.info("Axis 2FA Decrypted Response: {}", responseData);
						String encryptedData = EncryptUtil.encryptString(responseData.toString(), "");
						JSONObject newObj = new JSONObject();
						newObj.put("encryptedData", encryptedData);
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(newObj.toString().getBytes());
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception occured:", e);
			throw e;
		} finally {
			try {
				String serviceName = url.substring(url.lastIndexOf("/") + 1);
				apiLoggignService.logApplicationApi(serviceName, url, decryptedData,responseData, currentTimeMillis,System.currentTimeMillis(),ipAddress,"");
			} catch (Exception e) {
				log.error("Exception occurred:{}", e.getMessage());
			}
		}
	}
	
	
	public static String getClientIpAddr(HttpServletRequest request)
	{  
		String ip = request.getHeader("X-Forwarded-For");  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_X_FORWARDED");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_FORWARDED");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("HTTP_VIA");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getHeader("REMOTE_ADDR");  
		}  
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
			ip = request.getRemoteAddr();  
		}  
		return ip;  
	}

}