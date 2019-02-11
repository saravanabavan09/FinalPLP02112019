package com.cg.crs.service;

import java.util.List;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.AgentClaim;
import com.cg.crs.model.ClaimCreationEntity;
import com.cg.crs.model.ClaimDetailsEntity;
import com.cg.crs.model.PolicyEntity;
import com.cg.crs.model.Report;
import com.cg.crs.model.UserRole;

public interface CRSService {

	boolean profileCreation(UserRole userProfile) throws CRSException;

	boolean validateFields(UserRole user) throws CRSException;

	List<Report> reportGeneration(String userName) throws CRSException;

	List<Report> detailedView(Report report) throws CRSException;

	Boolean userExists(String userName) throws CRSException;

	boolean CheckClaimReason(String claimReason);

	boolean CheckAccidentLocationStreet(String accidentLocationStreet);

	boolean CheckAccidentCity(String accidentCity);

	boolean CheckAccidentState(String accidentState);

	boolean CheckAccidentZip(long accidentZip);

	boolean CheckPolicyNumber(long policyNumber);

	List<AgentClaim> claimData(String userName) throws CRSException;

	List<PolicyEntity> getPolicies(UserRole user) throws CRSException;

	boolean checkAlreadyClaimed(long policyNumber) throws CRSException;

	int insertClaimDetails(ClaimCreationEntity claimCreation) throws CRSException;

	List<ClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException;

	int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException;

	long getId(long policyNumber) throws CRSException;

	List<ClaimCreationEntity> viewClaimStatus(long claimId) throws CRSException;

	List<PolicyEntity> getAllPolicies() throws CRSException;

	List<ClaimCreationEntity> viewAllClaim() throws CRSException;;

}
