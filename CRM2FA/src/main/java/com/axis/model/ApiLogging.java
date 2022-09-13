package com.axis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ApiLogging")
public class ApiLogging {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "serviceName")
	private String serviceName;

	@Column(name = "channelName")
	private String channelName;
	
	@Column(name = "url")
	private String url;

	@Column(name = "request")
	private String request;
	
	@Column(name = "response")
	private String response;
	
	@Column(name = "ipAddress")
	private String ipAddress;

	@Column(name = "startTime", updatable = false, nullable = false)
	private Date startTime;

	@Column(name = "endTime", updatable = false, nullable = false)
	private Date endTime;

	@Column(name = "timeTaken")
	private long timeTake;

	@Column(name = "createdOn", updatable = false, nullable = false)
	private Date createdOn;
}
