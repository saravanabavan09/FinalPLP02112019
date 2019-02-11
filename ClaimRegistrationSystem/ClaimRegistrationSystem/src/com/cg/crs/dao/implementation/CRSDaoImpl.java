package com.cg.crs.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.crs.dao.CRSDao;
import com.cg.crs.exception.CRSException;
import com.cg.crs.model.AgentClaim;
import com.cg.crs.model.ClaimCreationEntity;
import com.cg.crs.model.ClaimDetailsEntity;
import com.cg.crs.model.PolicyEntity;
import com.cg.crs.model.Report;
import com.cg.crs.model.UserRole;
import com.cg.crs.query.QueryMapper;
import com.cg.crs.utility.JDBCUtility;

public class CRSDaoImpl implements CRSDao {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	static Logger logger = Logger.getLogger(CRSDaoImpl.class);
	/***
	 * method	 :validateFields
	 * argument  :It takes user role from MainUI
	 * returnType:boolean
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public boolean validateFields(UserRole user) throws CRSException {
		logger.debug("Validating for username and password");
		List<UserRole> list = new ArrayList<>();

		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		boolean flag = false;
		try {
			logger.info("Creating Query for checkUser in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.checkUser);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				logger.debug("Check for the username and password is present");
				String roleCode = resultSet.getString(1);
				logger.info("Getting rolecode from the database");
				user.setRoleCode(roleCode);
				logger.info("Setting the value for the Rolecode");
				list.add(user);
			}

			if (!list.isEmpty()) {

				flag = true;
			}

		} catch (SQLException e) {
			logger.error("Problem in occured in the checkUser Query");
			throw new CRSException("Not valid");

		}
		try {
			resultSet.close();
			logger.info("Closing the resultSet");
		} catch (SQLException e) {
			logger.error("Problem occured in closing ResultSet");
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
			logger.info("Closing the statement");
		} catch (SQLException e) {
			logger.error("Problem occured in statement closing");
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
			logger.info("Closing the connection");
		} catch (SQLException e) {
			logger.error(" problem in Connection closing ");
			throw new CRSException("Connection is not closed");

		}
		return flag;

	}
	
	/***
	 * method	 :profileCreation
	 * argument  :It takes userProfile object from UserProfileCreation
	 * returnType:boolean
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public boolean profileCreation(UserRole userProfile) throws CRSException {
		logger.info("Creation of new profile with username, password and rolecode");

		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		boolean flag = false;
		int value = 0;
		String userName = "";
		try {
			logger.info("Creating Query for userProfileCheck in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userProfile.getUserName());
			logger.debug("Check for the username is already present");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				logger.debug("Checking for the username by loop ");
				userName = resultSet.getString(1);
			}
			if (!userProfile.getUserName().equals(userName)) {
				logger.info("Creating Query for userProfileCreation in QueryMapper");
				statement = connection.prepareStatement(QueryMapper.userProfileCreation);
				statement.setString(1, userProfile.getUserName());
				statement.setString(2, userProfile.getPassword());
				statement.setString(3, userProfile.getRoleCode());
				logger.info("Inserting all datas to the database");
				value = statement.executeUpdate();
			}
			if (value != 0) {
				flag = true;
			}
		} catch (SQLException e) {
			logger.error("Error occured in the QueryMapper");
			throw new CRSException("Not valid");

		}
		try {
			resultSet.close();
			logger.info("Closing the resultSet");
		} catch (SQLException e) {
			logger.error("Problem occured in closing ResultSet");
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
			logger.info("Closing the statement");
		} catch (SQLException e) {
			logger.error("Problem occured in statement closing");
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
			logger.info("Closing the connection");
		} catch (SQLException e) {
			logger.error(" problem in Connection closing ");
			throw new CRSException("Connection is not closed");

		}
		return flag;

	}

	/***
	 * method	 :reportGeneration
	 * argument  :It takes userName String from ReportGeneration
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public List<Report> reportGeneration(String userName) throws CRSException {
		logger.info(" ReportGeneration for the username ");

		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		/*String username = "";*/
		Report report = new Report();
		/*UserRole user = new UserRole();*/
		List<Report> list = new ArrayList<>();
		try {
			/*logger.info("Creating Query for userProfileCheck in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				logger.debug("Checking for the username in the database ");
				username = resultSet.getString(1);
				user.setUserName(username);
			}
			*/
				logger.info("Creating Query for reportGeneration in QueryMapper");
				statement = connection.prepareStatement(QueryMapper.reportGeneration);
				statement.setString(1, userName);
				logger.info("Getting the report the given username");
				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					Long policyNumber = resultSet.getLong(3);
					Long claimNumber = resultSet.getLong(1);
					String claimType = resultSet.getString(2);

					report.setPolicyNumber(policyNumber);
					report.setClaimNumber(claimNumber);
					report.setClaimType(claimType);

					list.add(report);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException for viewing the report");
			throw new CRSException("Not valid");
		}
		try {
			resultSet.close();
			logger.info("Closing the resultSet");
		} catch (SQLException e) {
			logger.error("Problem occured in closing ResultSet");
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
			logger.info("Closing the statement");
		} catch (SQLException e) {
			logger.error("Problem occured in statement closing");
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
			logger.info("Closing the connection");
		} catch (SQLException e) {
			logger.error(" problem in Connection closing ");
			throw new CRSException("Connection is not closed");

		}

		return list;
	}

	/***
	 * method	 :detailedView
	 * argument  :It takes report object from ReportGeneration
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public List<Report> detailedView(Report report) throws CRSException {
		logger.info("Detailed View of the Customer status");
		List<Report> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for detailedview in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.detailedView);
			statement.setLong(1, report.getPolicyNumber());
			logger.info("Detailed view of the claim status by the policy Number");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				logger.debug("Checking for the policyNumber and getting the status of the customer claims ");
				String claimReason = resultSet.getString(1);
				String accidentStreet = resultSet.getString(2);
				String accidentCity = resultSet.getString(3);
				String accidentState = resultSet.getString(4);
				Integer accidentZip = resultSet.getInt(5);
				String claimType = resultSet.getString(6);
				String claimQuesDesc1 = resultSet.getString(7);
				String claimQuesAns1 = resultSet.getString(8);
				Report report1 = new Report();
				report1.setClaimReason(claimReason);
				report1.setAccidentStreet(accidentStreet);
				report1.setAccidentCity(accidentCity);
				report1.setAccidentState(accidentState);
				report1.setAccidentZip(accidentZip);
				report1.setClaimType(claimType);
				report1.setClaimQuesDesc1(claimQuesDesc1);
				report1.setClaimQuesAns1(claimQuesAns1);

				list.add(report1);
			}

		} catch (SQLException e) {
			logger.info("SQLException for viewing the claim status");
			throw new CRSException("Not retreived");
		}

		try {
			resultSet.close();
			logger.info("Closing the resultSet");
		} catch (SQLException e) {
			logger.error("Problem occured in closing ResultSet");
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
			logger.info("Closing the statement");
		} catch (SQLException e) {
			logger.error("Problem occured in statement closing");
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
			logger.info("Closing the connection");
		} catch (SQLException e) {
			logger.error(" problem in Connection closing ");
			throw new CRSException("Connection is not closed");

		}

		return list;

	}

	/***
	 * method	 :userExists
	 * argument  :It takes username string from ReportGeneration
	 * returnType:boolean
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public Boolean userExists(String userName) throws CRSException {
		logger.debug("checking for the username present in the database ");
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		boolean flag = false;
		String username = "";
		UserRole user = new UserRole();
		try {
			logger.info("Creating Query for userProfileCheck in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				logger.debug("Checking for the username in the database");
				username = resultSet.getString(1);
				user.setUserName(username);
			}
			if (username.equals(userName)) {
				flag = true;
			}
		} catch (SQLException e) {
			logger.error("Entered wrong usename");
			throw new CRSException("Invalid");
		}

		try {
			resultSet.close();
			logger.info("Closing the resultSet");
		} catch (SQLException e) {
			logger.error("Problem occured in closing ResultSet");
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
			logger.info("Closing the statement");
		} catch (SQLException e) {
			logger.error("Problem occured in statement closing");
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
			logger.info("Closing the connection");
		} catch (SQLException e) {
			logger.error(" problem in Connection closing ");
			throw new CRSException("Connection is not closed");

		}

		return flag;
	}

	/***
	 * method	 :claimData
	 * argument  :It takes username string from Claim
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public List<AgentClaim> claimData(String userName) throws CRSException {
		logger.info("Displaying the claimdatas for the Insurance Claim under the Agent");
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		List<AgentClaim> list = new ArrayList<>();
		try {
			logger.info("Creating Query for viewClaimHistory in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.viewClaimHistory);
			statement.setString(1, userName);
			logger.info("connection created");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				logger.info("Getting the claim status of the customers under the Agent");
				Long agentClaimNumber = resultSet.getLong(1);
				String agentClaimReason = resultSet.getString(2);
				String agentAccidentLocationStreet = resultSet.getString(3);
				String agentAccidentCity = resultSet.getString(4);
				String agentAccidentState = resultSet.getString(5);
				Long agentAccidentZip = resultSet.getLong(6);
				String agentClaimType = resultSet.getString(7);
				Long agentPolicyNumber = resultSet.getLong(8);
				AgentClaim presentation = new AgentClaim();
				presentation.setAgentClaimNumber(agentClaimNumber);
				presentation.setAgentClaimReason(agentClaimReason);
				presentation.setAgentAccidentLocationStreet(agentAccidentLocationStreet);
				presentation.setAgentAccidentCity(agentAccidentCity);
				presentation.setAgentAccidentState(agentAccidentState);
				presentation.setAgentAccidentZip(agentAccidentZip);
				presentation.setAgentClaimType(agentClaimType);
				presentation.setAgentPolicyNumber(agentPolicyNumber);
				list.add(presentation);

			}
			logger.info("Claim History viewed successfully");
		} catch (SQLException e) {
			logger.error("SQLException is occured by the wrong Query");
			throw new CRSException("statement problem occured while creating statement object");

		}

		return list;
	}

	/***
	 * method	 :getPolicies
	 * argument  :It takes user object from AgentViewClaim
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public List<PolicyEntity> getPolicies(UserRole user) throws CRSException {
		logger.info("Getting the list of policies and account Number in the policy table");
		List<PolicyEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for getPolicies in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getPolicies);
			statement.setString(1, user.getUserName());
			logger.info("Getting all the policy ,policy Premium and the account Number in the policy Table");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long policyNumber = resultSet.getLong(1);
				double policyPremium = resultSet.getDouble(2);
				long accountNumber = resultSet.getLong(3);

				PolicyEntity policy = new PolicyEntity();
				policy.setPolicyNumber(policyNumber);
				policy.setPolicyPremium(policyPremium);
				policy.setAccountNumber(accountNumber);
				list.add(policy);

			}
		} catch (SQLException e) {
			logger.error("Error occured for the SQLException is occured for viewing the policy");
			throw new CRSException("problem occured while creating the getPolicies statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				e.printStackTrace();
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}
		return list;
	}

	/***
	 * method	 :checkAlreadyClaimed
	 * argument  :It takes policyNumber from Claim
	 * returnType:boolean
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */
	@Override
	public boolean checkAlreadyClaimed(long policyNumber) throws CRSException {
		logger.info("Checking for the claim is made for the policynumber ");
		boolean flag = true;
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for checkClaimed in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.checkClaimed);

			statement.setLong(1, policyNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				logger.debug("Entered into the loop to check wheather the claim is made for the policyNumber");
				long policyNo = resultSet.getLong(8);
				if (policyNo == policyNumber) {
					flag = false;
				}

			}
		} catch (SQLException e) {
			logger.error("Problem occured in the SQl statements");
			throw new CRSException("problem occured while creating the getQuestions statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}

		return flag;
	}
	
	/***
	 * method	 :insertClaimDetails
	 * argument  :It takes claimCreation object from ClaimCreationEntity
	 * returnType:int
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public int insertClaimDetails(ClaimCreationEntity claimCreation) throws CRSException {
		logger.info("Adding the Customer claim details for the claim");
		connection = JDBCUtility.getConnection();
		logger.info("connection object created");
		int generatedId = 0;
		try {
			logger.info("Creating Query for insertClaimDetails in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.insertClaimDetails);

			statement.setString(1, claimCreation.getClaimReason());
			statement.setString(2, claimCreation.getAccidentLocationStreet());
			statement.setString(3, claimCreation.getAccidentCity());
			statement.setString(4, claimCreation.getAccidentState());
			statement.setLong(5, claimCreation.getAccidentZip());
			statement.setString(6, claimCreation.getClaimType());
			statement.setLong(7, claimCreation.getPolicyNumber());
			generatedId = statement.executeUpdate();
			logger.info("execute update called");
		} catch (SQLException e) {
			logger.error("Problem occured in the SQl query for the claim creation");
			throw new CRSException("problem occured while creating the insertClaimDetails statement object");
		} finally {
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}
		return generatedId;
	}
	
	/***
	 * method	 :getQuestions
	 * argument  :It takes policyNumber from ClaimQuestions
	 * returnType:List
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public List<ClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException {
		logger.info("Getting questions,options and answer weightage for the bussiness segment");
		List<ClaimDetailsEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for getClaimQuestionId in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getClaimQuestionId);
			statement.setLong(1, policyNumber);
			logger.info("Getting the Questions for the policy Number ");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String claimQuesId = resultSet.getString(1);
				int claimQuesSeq = resultSet.getInt(2);
				String busSeqId = resultSet.getString(3);
				String claimQuesDesc = resultSet.getString(4);
				String claimQuesAns1 = resultSet.getString(5);
				int claimQues1Weightage = resultSet.getInt(6);
				String claimQuesAns2 = resultSet.getString(7);
				int claimQues2Weightage = resultSet.getInt(8);
				String claimQuesAns3 = resultSet.getString(9);
				int claimQues3Weightage = resultSet.getInt(10);
				String claimQuesAns4 = resultSet.getString(11);
				int claimQues4Weightage = resultSet.getInt(12);
				ClaimDetailsEntity claimDetailsEntity = new ClaimDetailsEntity();
				claimDetailsEntity.setClaimQuesId(claimQuesId);
				claimDetailsEntity.setClaimQuesSeq(claimQuesSeq);
				claimDetailsEntity.setBusSeqId(busSeqId);
				claimDetailsEntity.setClaimQuesDesc(claimQuesDesc);
				claimDetailsEntity.setClaimQuesAns1(claimQuesAns1);
				claimDetailsEntity.setClaimQuesAns1Weightage(claimQues1Weightage);
				claimDetailsEntity.setClaimQuesAns2(claimQuesAns2);
				claimDetailsEntity.setClaimQuesAns2Weightage(claimQues2Weightage);
				claimDetailsEntity.setClaimQuesAns3(claimQuesAns3);
				claimDetailsEntity.setClaimQuesAns3Weightage(claimQues3Weightage);
				claimDetailsEntity.setClaimQuesAns4(claimQuesAns4);
				claimDetailsEntity.setClaimQuesAns4Weightage(claimQues4Weightage);
				list.add(claimDetailsEntity);
			}

		} catch (SQLException e) {
			logger.error("Problem occured in SQl query");
			throw new CRSException("problem occured while creating the getQuestions statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;
	}
	
	/***
	 * method	 :insertQuestions
	 * argument  :It takes policyNumber,claimQuesId,claimAns from ClaimQuestions
	 * returnType:int
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException {
		logger.info("Recording the questions and answer for the applied bussiness segment ");
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		int rows = 0;
		try {
			logger.info("Creating Query for insertQuestion in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.insertQuestion);
			statement.setLong(1, policyNumber);
			statement.setString(2, claimQuesId);
			statement.setString(3, claimAns);
			logger.info("Storing the Claim Answer for the claim ");
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Problem occured in Sql query");
			throw new CRSException("problem occured while creating the insertQuestions statement object");
		} finally {
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}

		return rows;
	}
	
	/***
	 * method	 :getId
	 * argument  :It takes policyNumber from Claim
	 * returnType:long
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public long getId(long policyNumber) throws CRSException {
		logger.info("Getting the Id for the policynumber");
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		long generatedClaimId = 0;
		try {
			logger.info("Creating Query for getClaimId in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getClaimId);
			statement.setLong(1, policyNumber);
			resultSet = statement.executeQuery();
			resultSet.next();
			logger.info("Getting the Id for the Customer Claim");
			generatedClaimId = resultSet.getLong(1);

		} catch (SQLException e) {
			logger.error("Problem occured in Sql query");
			throw new CRSException("Problem occured while creating statement object");
		} finally {
			logger.info("IN Finally BLOCK");
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				logger.error(e.getMessage());
				throw new CRSException("Problem in closing statement");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				logger.error(e.getMessage());
				throw new CRSException("Problem in closing statement");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new CRSException("Connection is not closed");
			}
		}

		return generatedClaimId;

	}
	
	/***
	 * method	 :viewClaimStatus
	 * argument  :It takes claimId from ViewClaim
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public List<ClaimCreationEntity> viewClaimStatus(long claimId) throws CRSException {
		logger.info("Displaying the status of the claim for the customer");
		List<ClaimCreationEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for getClaimStatus in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getClaimStatus);
			statement.setLong(1, claimId);
			logger.info("Viewing the claim status of the custpmer by the Given Id");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Long claimNumber = resultSet.getLong(1);
				String claimReason = resultSet.getString(2);
				String accidentLocationStreet = resultSet.getString(3);
				String accidentCity = resultSet.getString(4);
				String accidentState = resultSet.getString(5);
				Long accidentZip = resultSet.getLong(6);
				String claimType = resultSet.getString(7);
				Long policyNumber = resultSet.getLong(8);

				ClaimCreationEntity entity = new ClaimCreationEntity();

				entity.setClaimNumber(claimNumber);
				entity.setClaimReason(claimReason);
				entity.setAccidentLocationStreet(accidentLocationStreet);
				entity.setAccidentCity(accidentCity);
				entity.setAccidentState(accidentState);
				entity.setAccidentZip(accidentZip);
				entity.setClaimType(claimType);
				entity.setPolicyNumber(policyNumber);
				list.add(entity);

			}

		} catch (SQLException e) {
			logger.error("Problem occured in Sql query");
			throw new CRSException("problem occured while creating the viewClaimStatus statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;

	}
	
	/***
	 * method	 :getAllPolicies
	 * argument  :No arguments
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */


	@Override
	public List<PolicyEntity> getAllPolicies() throws CRSException {
		logger.info("Displaying all the policies ");
		List<PolicyEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for getAllPolicies in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getAllPolicies);
			logger.info("Displaying details present in the policy table");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long policyNumber = resultSet.getLong(1);
				double policyPremium = resultSet.getDouble(2);
				long accountNumber = resultSet.getLong(3);

				PolicyEntity policy = new PolicyEntity();
				policy.setPolicyNumber(policyNumber);
				policy.setPolicyPremium(policyPremium);
				policy.setAccountNumber(accountNumber);
				list.add(policy);

			}

		} catch (SQLException e) {
			logger.error("Problem occured in Sql query");
			throw new CRSException("problem occured while creating the getPolicies statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				e.printStackTrace();
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;
	}
	
	/***
	 * method	 :viewAllClaim
	 * argument  :No arguments
	 * returnType:list
	 * Author    :Capgemini
	 * Date      :11-02-2019
	 */

	@Override
	public List<ClaimCreationEntity> viewAllClaim() throws CRSException {
		logger.info("Displaying the claimed customer details");
		List<ClaimCreationEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		logger.info("JDBC connection created");
		try {
			logger.info("Creating Query for getAllClaim in QueryMapper");
			statement = connection.prepareStatement(QueryMapper.getAllClaim);
			logger.info("Displaying all the customer claim status ");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Long claimNumber = resultSet.getLong(1);
				String claimReason = resultSet.getString(2);
				String accidentLocationStreet = resultSet.getString(3);
				String accidentCity = resultSet.getString(4);
				String accidentState = resultSet.getString(5);
				Long accidentZip = resultSet.getLong(6);
				String claimType = resultSet.getString(7);
				Long policyNumber = resultSet.getLong(8);

				ClaimCreationEntity entity = new ClaimCreationEntity();

				entity.setClaimNumber(claimNumber);
				entity.setClaimReason(claimReason);
				entity.setAccidentLocationStreet(accidentLocationStreet);
				entity.setAccidentCity(accidentCity);
				entity.setAccidentState(accidentState);
				entity.setAccidentZip(accidentZip);
				entity.setClaimType(claimType);
				entity.setPolicyNumber(policyNumber);
				list.add(entity);

			}

		} catch (SQLException e) {
			logger.error("Problem occured in Sql query");
			throw new CRSException("problem occured while creating the getPolicies statement object");
		} finally {
			try {
				resultSet.close();
				logger.info("Closing the resultSet");
			} catch (SQLException e) {
				logger.error("Problem occured in closing ResultSet");
				e.printStackTrace();
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
				logger.info("Closing the statement");
			} catch (SQLException e) {
				logger.error("Problem occured in statement closing");
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
				logger.info("Closing the connection");
			} catch (SQLException e) {
				logger.error(" problem in Connection closing ");
				throw new CRSException("Connection Not Closed");
			}
		}
		return list;
	}

}