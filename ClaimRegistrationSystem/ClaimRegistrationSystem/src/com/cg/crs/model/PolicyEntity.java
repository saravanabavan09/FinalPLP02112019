package com.cg.crs.model;

public class PolicyEntity {
	private Long policyNumber;
	private Double policyPremium;
	private Long accountNumber;
	private String busSeqId;
	public PolicyEntity(Long policyNumber, Double policyPremium, Long accountNumber, String busSeqId) {
		super();
		this.policyNumber = policyNumber;
		this.policyPremium = policyPremium;
		this.accountNumber = accountNumber;
		this.busSeqId = busSeqId;
	}
	public Long getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(Long policyNumber) {
		this.policyNumber = policyNumber;
	}
	public Double getPolicyPremium() {
		return policyPremium;
	}
	public void setPolicyPremium(Double policyPremium) {
		this.policyPremium = policyPremium;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBusSeqId() {
		return busSeqId;
	}
	public void setBusSeqId(String busSeqId) {
		this.busSeqId = busSeqId;
	}
	public PolicyEntity() {
		// TODO Auto-generated constructor stub
	}
		
	
}
