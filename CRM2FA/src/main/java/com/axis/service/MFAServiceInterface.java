package com.axis.service;

import java.util.List;

import com.axis.model.MFA_UserMaster;

public interface MFAServiceInterface {

	MFA_UserMaster insertMFAUser(MFA_UserMaster mfauser);
	List<MFA_UserMaster> getAllMFAUsers();
	//MFA_UserMaster getMFAUsersByChannelUserIDAndChannelId();
	//MFA_UserMaster getMFAUsersByChannelUserID(String channelUserID);
	//Optional<MFA_UserMaster> getMFAUsersByChannelUserID(MFAPrimaryKey mfarimaryKey);
	MFA_UserMaster findByMFAPrimaryKeychannel_UserId(String channel_UserId);
	
	
	List<MFA_UserMaster> findByMFAPrimaryKeychannel_Id(int channel_Id);
	String getMFAUserStatus(String channel_UserId);
	
	MFA_UserMaster updateMFA_UserMaster(MFA_UserMaster mfaUser);
	
	
}
