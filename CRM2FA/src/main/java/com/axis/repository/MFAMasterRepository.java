package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.axis.model.MFAPrimaryKey;
import com.axis.model.MFA_UserMaster;

import java.util.*;

public interface MFAMasterRepository extends JpaRepository<MFA_UserMaster, MFAPrimaryKey> {
	
	//List<MFA_UserMaster> findBychannelUserId(String channelUserId); 
	List<MFA_UserMaster> findBychannelID(int channelId);
	
	
	MFA_UserMaster findBychannelUserId(String channel_UserId); 
	
	@Query("Select m.token_STATUS from MFA_UserMaster m where m.channelUserId=:c")
	String getMFAUserStatus(@Param("c")String channelUserId);
	
	
	
	
}
