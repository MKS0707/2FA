package com.axis.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.axis.exception.AppException;
import com.axis.exception.ResourseNotFoundException;
import com.axis.model.MFAPrimaryKey;
import com.axis.model.MFA_UserMaster;
import com.axis.repository.MFAMasterRepository;
import com.axis.service.MFAServiceInterface;

@Service
public class MFAServiceImpl implements MFAServiceInterface{
	
	private MFAMasterRepository mfaUserRepository;
	
	
	public MFAServiceImpl(MFAMasterRepository mfaUserRepository) {
		super();
		this.mfaUserRepository = mfaUserRepository;
	}

	@Override
	public MFA_UserMaster insertMFAUser(MFA_UserMaster mfauser)
	{
		
		mfauser.setR_CRE_TIME(new Date());
		
		MFA_UserMaster returnObj= mfaUserRepository.save(mfauser);
		
		if(returnObj==null)
		{
			new AppException("Data save failed!!!");
		}
		
		return returnObj;
	}

	@Override
	public List<MFA_UserMaster> getAllMFAUsers() {
		
		return mfaUserRepository.findAll();
	}

	/*
	 * @Override public Optional<MFA_UserMaster>
	 * getMFAUsersByChannelUserID(MFAPrimaryKey mfarimaryKey) { return
	 * mfaUserRepository.findById(mfarimaryKey); }
	 */

	@Override
	public MFA_UserMaster findByMFAPrimaryKeychannel_UserId(String channelUserId) {
		// TODO Auto-generated method stub
		return mfaUserRepository.findBychannelUserId(channelUserId);
	}

	@Override
	public List<MFA_UserMaster> findByMFAPrimaryKeychannel_Id(int channelId) {
		// TODO Auto-generated method stub
		return mfaUserRepository.findBychannelID(channelId);
	}

	@Override
	public String getMFAUserStatus(String channel_UserId) {
		// TODO Auto-generated method stub
		return mfaUserRepository.getMFAUserStatus(channel_UserId);
	}

	@Override
	public MFA_UserMaster updateMFA_UserMaster(MFA_UserMaster mfauser) {
		
		MFA_UserMaster returnObj =null;
		MFA_UserMaster existingUser=mfaUserRepository.findBychannelUserId(mfauser.getChannelUserId());
		
				if(existingUser==null)
				{
					throw new ResourseNotFoundException("MFA_UserMaster","channelUserId",mfauser.getChannelUserId());
				}else
				{
					existingUser = mfauser;
					
					existingUser.setR_MOD_ID(mfauser.getChannelUserId());
					existingUser.setR_MOD_TIME(new Date());
					returnObj =	mfaUserRepository.save(existingUser);
				}
		return returnObj;
	}
}
