package com.axis.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.axis.model.ApiLogging;
import com.axis.repository.ApiLoggingRepository;


@Service
public class ApiLoggignService {

	@Autowired
	private ApiLoggingRepository apiLoggingRepository;

	@Autowired
	private Environment environment;

	
	@Async
	public void logExternalApi(String serviceName, String url, String request, String response, long startTime, long endTime,String ipAddress, String channelName) {
		String logApi = environment.getProperty("logApi");
		if (StringUtils.equalsIgnoreCase("True", logApi)) {
			ApiLogging apiLogging = createApiLoggingModel(serviceName, url, request, response, startTime, endTime, ipAddress, channelName);
			apiLoggingRepository.save(apiLogging);
		}
	}

	@Async
	public void logApplicationApi(String serviceName, String url, String request,String response,long startTime, long endTime,
			String ipAddress,String channelName) {
		String logApi = environment.getProperty("logApi");
		if (StringUtils.equalsIgnoreCase("True", logApi)) {
			ApiLogging apiLogging = createApiLoggingModel(serviceName, url, request,response, startTime, endTime, ipAddress,channelName);
			apiLoggingRepository.save(apiLogging);
		}
	}

	private ApiLogging createApiLoggingModel(String serviceName, String url, String request,String response,long startTime,
			long endTime, String ipAddress,String channelName) {
		ApiLogging externalApiLogging = ApiLogging.builder()//
				.serviceName(serviceName)//
				.channelName(channelName)
				.url(url)//
				.request(request)//
				.response(response)
				.ipAddress(ipAddress)
				.startTime(new Date(startTime))//
				.endTime(new Date(endTime))//
				.timeTake(endTime - startTime)//
				.createdOn(new Date())//
				.build();
		return externalApiLogging;
	}

	

}
