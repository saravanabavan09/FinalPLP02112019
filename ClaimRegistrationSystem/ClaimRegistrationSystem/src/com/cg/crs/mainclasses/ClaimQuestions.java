package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.ClaimDetailsEntity;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;
import com.cg.crs.ui.MainUI;

public class ClaimQuestions {
static Logger logger = Logger.getLogger(ClaimQuestions.class);
	@SuppressWarnings("resource")
	public static void claimQuestion(UserRole user) {
		Scanner scanner = new Scanner(System.in);
		long policyNumber;
		List<ClaimDetailsEntity> list;
		int value=0;
		if (user.getRoleCode().equals("ADMIN")) {
			policyNumber = AdminClaim.policyNumber;
			value=1;

		} else {
			policyNumber = Claim.policyNumber;
		}
		System.out.println(" ");
		logger.info("Entering the claim questions");
		System.out.println("===============================Claim Questions===============================");
		CRSService service = new CRSServiceImpl();

		try {
			list = service.getQuestions(policyNumber);
			String claimAns = null;
			String claimQuesId = null;
			@SuppressWarnings("unused")
			int claimAnsWeightage = 0;
			for (ClaimDetailsEntity entity : list) {
				boolean questionFlag = false;
				do {
					scanner = new Scanner(System.in);
					logger.info("Displaying claim questions from database");
					System.out.println(" ");
					
					System.out.println("Question: " + entity.getClaimQuesDesc());
					System.out.println("Select any one of the 4 options");
					System.out.println("1." + entity.getClaimQuesAns1());
					System.out.println("2." + entity.getClaimQuesAns2());
					System.out.println("3." + entity.getClaimQuesAns3());
					System.out.println("4." + entity.getClaimQuesAns4());
					logger.info("Selecting the option from the given questions");
					claimQuesId = entity.getClaimQuesId();
					try {
						int choice = scanner.nextInt();
						questionFlag = true;

						switch (choice) {
						case 1:
							claimAns = entity.getClaimQuesAns1();
							claimAnsWeightage = entity.getClaimQuesAns1Weightage();
							break;
						case 2:
							claimAns = entity.getClaimQuesAns2();
							claimAnsWeightage = entity.getClaimQuesAns2Weightage();
							break;
						case 3:
							claimAns = entity.getClaimQuesAns3();
							claimAnsWeightage = entity.getClaimQuesAns3Weightage();
							break;
						case 4:
							claimAns = entity.getClaimQuesAns4();
							claimAnsWeightage = entity.getClaimQuesAns4Weightage();
							break;

						default:
							questionFlag = false;
							logger.error("Selecting the options which is out of the given choices");
							System.err.println("Select any options from 1 to 4");
							break;
						}
					} catch (InputMismatchException e) {
						logger.error("Entering the invalid input");
						System.err.println("Enter Only Digits");

					}

				} while (!questionFlag);
				logger.info("Displaying the weightage of the claim ");
				System.out.println("You have selected " + claimAns);
				service.insertQuestions(policyNumber, claimQuesId, claimAns);

			}
			System.out.println(" ");
			logger.info("Displaying the message for successfull selection");
			System.out.println("* * * * * Congratulations. You Have Successfully Applied for Claim * * * * *");

			long claimId = service.getId(policyNumber);
			logger.info("Displaying the claim Id");
			System.out.println("Your Claim Id:" + claimId);

			boolean menuFlag = false;
			do {
				System.out.println(" ");
				logger.debug("Entering the input after the claim Id");
				System.out.println(
						"Press 1 to go back to main menu (or) Press 2 to go back to previous menu (or) Press 0 to Exit ");
				try {
					int menu = scanner.nextInt();
					if (menu == 1) {
						menuFlag = true;
						String[] args = null;
						MainUI.main(args);
					} else if (menu == 2) {
						menuFlag = true;
						if (value == 1) {
							Admin.adminClient(user);
						} else {
							Claim.claimCreation(user);
						}
					} else if (menu == 0) {
						menuFlag = true;
						logger.info("Entering the thanking message");
						System.out.println("Thank You");
						System.exit(0);
					} else {
						menuFlag = false;
						logger.error("Entering the wrong values");
						System.err.println("Enter Valid Details");
					}
				} catch (InputMismatchException e) {
					logger.error("Enter the wrong inputs");
					System.err.println("Enter Only Integers");

				}
			} while (!menuFlag);

		} catch (CRSException e) {
			e.printStackTrace();
			logger.error("Entering the invalid inputs");
			System.err.println("Problem Occured while fetching/asking Questions");
		}

	}

}
