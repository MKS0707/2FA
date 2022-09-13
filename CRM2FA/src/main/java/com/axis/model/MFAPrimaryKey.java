package com.axis.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MFAPrimaryKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MFAPrimaryKey() {}
	public MFAPrimaryKey(String channelUserId, int channelID) {
		super();
		this.channelUserId = channelUserId;
		this.channelID = channelID;
	}
	
	@Column(updatable = true,name="CHANNEL_USERID",nullable = false,columnDefinition = "varchar(64)")
	 private String channelUserId;
	

	@Column(updatable = true,name="CHANNEL_ID",nullable = false,columnDefinition = "BIGINT")
	private int  channelID;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MFAPrimaryKey other = (MFAPrimaryKey) obj;
		return Objects.equals(channelUserId, other.channelUserId) && channelID == other.channelID;
	}
	@Override
	public int hashCode() {
		return Objects.hash(channelUserId, channelID);
	}
}
