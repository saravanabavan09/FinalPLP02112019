package com.cg.crs.model;

public class AgentClaim {
	
	private String agentClaimReason;
	private Long agentClaimNumber;
    private String agentAccidentLocationStreet;
	private String agentAccidentCity;
	private String agentAccidentState;
	private Long agentAccidentZip;
	private String agentClaimType;
	private Long agentPolicyNumber;
	private Long customerAccountNumber;
	private String userName;
	public AgentClaim() {
		// TODO Auto-generated constructor stub
	}
	public String getAgentClaimReason() {
		return agentClaimReason;
	}
	public void setAgentClaimReason(String agentClaimReason) {
		this.agentClaimReason = agentClaimReason;
	}
	public String getAgentAccidentLocationStreet() {
		return agentAccidentLocationStreet;
	}
	public void setAgentAccidentLocationStreet(String agentAccidentLocationStreet) {
		this.agentAccidentLocationStreet = agentAccidentLocationStreet;
	}
	public String getAgentAccidentCity() {
		return agentAccidentCity;
	}
	public void setAgentAccidentCity(String agentAccidentCity) {
		this.agentAccidentCity = agentAccidentCity;
	}
	public String getAgentAccidentState() {
		return agentAccidentState;
	}
	public void setAgentAccidentState(String agentAccidentState) {
		this.agentAccidentState = agentAccidentState;
	}
	public Long getAgentAccidentZip() {
		return agentAccidentZip;
	}
	public void setAgentAccidentZip(Long agentAccidentZip) {
		this.agentAccidentZip = agentAccidentZip;
	}
	public String getAgentClaimType() {
		return agentClaimType;
	}
	public void setAgentClaimType(String agentClaimType) {
		this.agentClaimType = agentClaimType;
	}
	public Long getAgentPolicyNumber() {
		return agentPolicyNumber;
	}
	public void setAgentPolicyNumber(Long agentPolicyNumber) {
		this.agentPolicyNumber = agentPolicyNumber;
	}
	public Long getAgentClaimNumber() {
		return agentClaimNumber;
	}
	public void setAgentClaimNumber(Long agentClaimNumber) {
		this.agentClaimNumber = agentClaimNumber;
	}
	public AgentClaim(String agentClaimReason, String agentAccidentLocationStreet, String agentAccidentCity,
			String agentAccidentState, Long agentAccidentZip, String agentClaimType, Long agentPolicyNumber) {
		super();
		this.agentClaimReason = agentClaimReason;
		this.agentAccidentLocationStreet = agentAccidentLocationStreet;
		this.agentAccidentCity = agentAccidentCity;
		this.agentAccidentState = agentAccidentState;
		this.agentAccidentZip = agentAccidentZip;
		this.agentClaimType = agentClaimType;
		this.agentPolicyNumber = agentPolicyNumber;
	}
	@Override
	public String toString() {
		return "Presentation [agentClaimReason=" + agentClaimReason + ", agentAccidentLocationStreet="
				+ agentAccidentLocationStreet + ", agentAccidentCity=" + agentAccidentCity + ", agentAccidentState="
				+ agentAccidentState + ", agentAccidentZip=" + agentAccidentZip + ", agentClaimType=" + agentClaimType
				+ ", agentPolicyNumber=" + agentPolicyNumber + ", customerAccountNumber=" + customerAccountNumber
				+ ", userName=" + userName + "]";
	}
	public Long getCustomerAccountNumber() {
		return customerAccountNumber;
	}
	public void setCustomerAccountNumber(Long customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}