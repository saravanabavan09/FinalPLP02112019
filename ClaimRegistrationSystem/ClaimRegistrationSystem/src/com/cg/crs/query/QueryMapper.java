package com.cg.crs.query;

public interface QueryMapper {
	String checkUser="select role_code from User_role where username=? and password=?";
    String userProfileCheck="select username from user_role where username=?";
	String userProfileCreation = "insert into user_role values(?,?,?)";
	/*String reportGeneration="select policy_number,claim_number,claim_type from claim where policy_number=(select policy_number from policy where account_number=(select account_number from accounts where userName=?))";*/
	String reportGeneration="select c.claim_number,c.claim_type,c.policy_number from claim c,policy p,accounts a,user_role u where u.username=a.username and a.account_number=p.account_number and p.policy_number=c.policy_number and u.username=?";
    String detailedView="SELECT c.claim_reason,c.accident_street,c.accident_city,c.accident_state,c.accident_zip,c.claim_type,c_q.claim_ques_desc,p_d.answer from claim c,policy_details p_d,claim_questions c_q where c_q.claim_ques_id=p_d.question_id and p_d.policy_number=c.policy_number and p_d.policy_number=?";
	String getAllPolicies = "SELECT * FROM POLICY";
	String getAllClaim = "select * from claim";
	public static final String getPolicies = "select p.policy_number,p.policy_premium,a.account_number from accounts a,user_role u,policy p where a.username=u.username and a.account_number=p.account_number and u.username=?";
	public static final String checkClaimed = "select * from claim where policy_number=?";
	public static final String insertClaimDetails = "insert into claim values(claim_seq.nextval,?,?,?,?,?,?,?)";
	public static final String getClaimQuestionId = "select * from claim_questions where bus_seg_id=(select bus_seg_id from policy where policy_number=?)";
	public static final String insertQuestion = "insert into policy_details values(?,?,?)";
	public static final String getClaimId = "select claim_number from claim where policy_number=?";
	public static final String getClaimStatus = "select * from claim where claim_number =?";
	public static final String viewClaimHistory = " select c.claim_number,c.claim_reason,c.accident_street,c.accident_city,c.accident_state,c.accident_zip,c.claim_type,c.policy_number from claim c,policy p,accounts a,user_role u where u.username=a.username and a.account_number=p.account_number and p.policy_number=c.policy_number and u.username=?";
}



