package com.axis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.model.MFAPrimaryKey;
import com.axis.model.MFA_UserMaster;
import com.axis.service.MFAServiceInterface;

@RestController
@RequestMapping("/api/MFAUser")
public class MFAUserController {

	private MFAServiceInterface mfaUserService;

	public MFAUserController(MFAServiceInterface mfaUserService) {
		super();
		this.mfaUserService = mfaUserService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> insertMFAUser(@RequestBody MFA_UserMaster mfaUser){

		MFA_UserMaster returnObj=mfaUserService.insertMFAUser(mfaUser);
		Map<String, String> map = new HashMap<>();

		if(returnObj==null) { map.put("message", "Data save Failed!"); }else
		{
			map.put("message", "MFA User saved successfully!");
		}
		return ResponseEntity.ok(map);
		// ResponseEntity<MFA_UserMaster>(returnObj,HttpStatus.CREATED);

	}

	/*
	 * @GetMapping public List<MFA_UserMaster> getAllMFAUsers() { return
	 * mfaUserService.getAllMFAUsers();
	 * 
	 * 
	 * }
	 */


	@GetMapping("/{id}")
	public MFA_UserMaster getMFAUsersByChannelUserID(@PathVariable("id") String channel_UserId)
	{
		//MFAMasterRepository repos = new MFAMasterRepository();

		return mfaUserService.findByMFAPrimaryKeychannel_UserId(channel_UserId);
	}

	@GetMapping("getid/{id}")
	public List<MFA_UserMaster> findByMFAPrimaryKeychannel_Id(@PathVariable("id") int channel_Id)
	{
		//MFAMasterRepository repos = new MFAMasterRepository();

		return mfaUserService.findByMFAPrimaryKeychannel_Id(channel_Id);
	}


	@GetMapping("getStatus/{id}")
	public ResponseEntity<Map<String, String>>  getMFAUserStatus(@PathVariable("id") String channel_UserId)
	{

		Map<String, String> map = new HashMap<>();

		String returnObj=mfaUserService.getMFAUserStatus(channel_UserId);

		if(returnObj!=null && !returnObj.isEmpty()) 
		{
			map.put("token_STATUS", returnObj);
		}
		else
		{
			map.put("message", "User not found.");
		}
		return ResponseEntity.ok(map);
	}

	
	
	@PutMapping("/update")
	public ResponseEntity<Map<String, String>> updateMFAUser(@RequestBody MFA_UserMaster mfaUser){

		MFA_UserMaster returnObj=mfaUserService.updateMFA_UserMaster(mfaUser);
		Map<String, String> map = new HashMap<>();

		if(returnObj==null) { map.put("message", "User updation Failed!"); }else
		{
			map.put("message", "MFA User update successfully!");
		}
		return ResponseEntity.ok(map);
		// ResponseEntity<MFA_UserMaster>(returnObj,HttpStatus.CREATED);

	}


}
