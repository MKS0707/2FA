package com.axis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MFAPrimaryKey.class)
public class MFA_UserMaster implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * @EmbeddedId MFAPrimaryKey mPK;
	 * 
	 * public MFAPrimaryKey getmPK() { return mPK; } public void
	 * setmPK(MFAPrimaryKey mPK) { this.mPK = mPK; }
	 */
	@Id
	@Column(updatable = false,name="CHANNEL_USERID",nullable = false,columnDefinition = "varchar(64)")
	 private String channelUserId;
	@Id
	@Column(updatable = false,name="CHANNEL_ID",nullable = false,columnDefinition = "BIGINT")
	private int  channelID;
	
	@Column(updatable = true,name="TFA_USER_ID",columnDefinition = "BIGINT")
	private int  tfa_USER_ID;
	@Column(updatable = true,name="CUST_ID",columnDefinition = "varchar(64)")
	private String cust_ID;
	
	@Column(updatable = true,name="BAY_USER_ID",columnDefinition = "varchar(64)")
	private Long bay_USER_ID;
	@Column(updatable = true,name="USER_TYPE",columnDefinition = "varchar(64)")
    private String user_TYPE;
	@Column(updatable = true,name="ENFORCED_2FA",columnDefinition = "varchar(64)")
    private String enforced_2FA;
	@Column(updatable = true,name="MOBILE_NUMBER",columnDefinition = "varchar(64)")
    private String mobile_NUMBER;
	@Column(updatable = true,name="EMAIL",columnDefinition = "varchar(100)")
    private String email;
    @Column(updatable = true,name="TOKEN_STATUS",columnDefinition = "varchar(200)")
    private String token_STATUS;
    @Column(updatable = true,name="ADDR_CUST_ID",columnDefinition = "varchar(32)")
    private String addr_CUST_ID;
    @Column(updatable = true,name="DEL_FLG",columnDefinition = "varchar(16)")
    private String del_FLG;
    @Column(updatable = true,name="R_CRE_ID",columnDefinition = "varchar(64)")
     private String r_CRE_ID;
    
    @Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date r_CRE_TIME;
    
    @Column(updatable = true,name="R_MOD_ID",columnDefinition = "varchar(64)")
    private String r_MOD_ID;
    
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date r_MOD_TIME;
	
    @Column(updatable = true,name="INCORRECT_ATTEMPTS",columnDefinition = "BIGINT")
    private int incorrect_ATTEMPTS;
    @Column(updatable = true,name="SMSOTP_ENABLED",columnDefinition = "varchar(64)")
    private String smsotp_ENABLED;
    @Column(updatable = true,name="FREE_FIELD_1",columnDefinition = "varchar(64)")
    private String free_FIELD_1;
    @Column(updatable = true,name="FREE_FIELD_2",columnDefinition = "varchar(64)")
    private String free_FIELD_2;
    @Column(updatable = true,name="FREE_FIELD_3",columnDefinition = "varchar(64)")
    private String free_FIELD_3;
    
    
    

}
