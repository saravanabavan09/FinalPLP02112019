package com.cg.crs.model;

public class ClaimDetailsEntity {
	private String claimQuesId;
	public ClaimDetailsEntity(String claimQuesId) {
		super();
		this.claimQuesId = claimQuesId;
	}
	

	public String getClaimQuesId() {
		return claimQuesId;
	}

	public void setClaimQuesId(String claimQuesId) {
		this.claimQuesId = claimQuesId;
	}


	private Integer claimQuesSeq;
	private String busSeqId;
	private String claimQuesDesc;
	private String claimQuesAns1;
	private Integer claimQuesAns1Weightage;
	private String claimQuesAns2;
	private Integer claimQuesAns2Weightage;
	private String claimQuesAns3;
	private Integer claimQuesAns3Weightage;
	private String claimQuesAns4;
	private Integer claimQuesAns4Weightage;
	
	public ClaimDetailsEntity() {
		// TODO Auto-generated constructor stub
	}

	public ClaimDetailsEntity(Integer claimQuesSeq, String busSeqId, String claimQuesDesc, String claimQuesAns1,
			Integer claimQuesAns1Weightage, String claimQuesAns2, Integer claimQuesAns2Weightage, String claimQuesAns3,
			Integer claimQuesAns3Weightage, String claimQuesAns4, Integer claimQuesAns4Weightage) {
		super();
		this.claimQuesSeq = claimQuesSeq;
		this.busSeqId = busSeqId;
		this.claimQuesDesc = claimQuesDesc;
		this.claimQuesAns1 = claimQuesAns1;
		this.claimQuesAns1Weightage = claimQuesAns1Weightage;
		this.claimQuesAns2 = claimQuesAns2;
		this.claimQuesAns2Weightage = claimQuesAns2Weightage;
		this.claimQuesAns3 = claimQuesAns3;
		this.claimQuesAns3Weightage = claimQuesAns3Weightage;
		this.claimQuesAns4 = claimQuesAns4;
		this.claimQuesAns4Weightage = claimQuesAns4Weightage;
	}

	public Integer getClaimQuesSeq() {
		return claimQuesSeq;
	}

	public void setClaimQuesSeq(Integer claimQuesSeq) {
		this.claimQuesSeq = claimQuesSeq;
	}

	public String getBusSeqId() {
		return busSeqId;
	}

	public void setBusSeqId(String busSeqId) {
		this.busSeqId = busSeqId;
	}

	public String getClaimQuesDesc() {
		return claimQuesDesc;
	}

	public void setClaimQuesDesc(String claimQuesDesc) {
		this.claimQuesDesc = claimQuesDesc;
	}

	public String getClaimQuesAns1() {
		return claimQuesAns1;
	}

	public void setClaimQuesAns1(String claimQuesAns1) {
		this.claimQuesAns1 = claimQuesAns1;
	}

	public Integer getClaimQuesAns1Weightage() {
		return claimQuesAns1Weightage;
	}

	public void setClaimQuesAns1Weightage(Integer claimQuesAns1Weightage) {
		this.claimQuesAns1Weightage = claimQuesAns1Weightage;
	}

	public String getClaimQuesAns2() {
		return claimQuesAns2;
	}

	public void setClaimQuesAns2(String claimQuesAns2) {
		this.claimQuesAns2 = claimQuesAns2;
	}

	public Integer getClaimQuesAns2Weightage() {
		return claimQuesAns2Weightage;
	}

	public void setClaimQuesAns2Weightage(Integer claimQuesAns2Weightage) {
		this.claimQuesAns2Weightage = claimQuesAns2Weightage;
	}

	public String getClaimQuesAns3() {
		return claimQuesAns3;
	}

	public void setClaimQuesAns3(String claimQuesAns3) {
		this.claimQuesAns3 = claimQuesAns3;
	}

	public Integer getClaimQuesAns3Weightage() {
		return claimQuesAns3Weightage;
	}

	public void setClaimQuesAns3Weightage(Integer claimQuesAns3Weightage) {
		this.claimQuesAns3Weightage = claimQuesAns3Weightage;
	}

	public String getClaimQuesAns4() {
		return claimQuesAns4;
	}

	public void setClaimQuesAns4(String claimQuesAns4) {
		this.claimQuesAns4 = claimQuesAns4;
	}

	public Integer getClaimQuesAns4Weightage() {
		return claimQuesAns4Weightage;
	}

	public void setClaimQuesAns4Weightage(Integer claimQuesAns4Weightage) {
		this.claimQuesAns4Weightage = claimQuesAns4Weightage;
	} 
	

}
