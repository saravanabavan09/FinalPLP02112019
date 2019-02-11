package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.UserRole;

public class Admin {
	static Logger logger = Logger.getLogger(Admin.class);
	public static void adminClient(UserRole user) throws CRSException, InputMismatchException {
		Scanner scanner = null;
		boolean adminFlag = false;
		logger.info("Displaying the Admin menu");
		System.out.println("===============================Admin Menu===============================");

		int choice = 0;
		do {
			scanner = new Scanner(System.in);
			
			System.out.println("1.New Profile Creation");
			System.out.println("2.Claim Creation");
			System.out.println("3.View Claim");
			System.out.println("4.Report Generation");
			System.out.println("Enter your choice:");
			logger.info("Choosing from the menu");
			try {
				choice = scanner.nextInt();
				adminFlag = true;
				switch (choice) {
				case 1:
					logger.info("New profile creation");
					UserProfileCreation.profileCreation(user);
					break;
				case 2:
					logger.info("customer claim creation");
					AdminClaim.claimCreation(user);
					break;
				case 3:
					logger.info("Viewing the claim");
					boolean viewFlag = false;
					int viewChoice = 0;
					do {
						logger.debug("Entered into the loop ");
						scanner = new Scanner(System.in);
						System.out.println("1.View All Claims");
						System.out.println("2.View Claims Based on Username");
						System.out.println("3.View Claims Based on Claim Id");
						System.out.println("Enter your choice:");
						try {
							viewChoice = scanner.nextInt();
							switch (viewChoice) {
							case 1:
								logger.info("Viewing the claim for the User");
								AdminViewClaim.viewClaim(user);
								viewFlag = true;
								break;
							case 2:
								logger.info("Viewing the claim for the customers under the Agent");
								AgentViewClaim.agentViewClaim(user);
								viewFlag = true;
								break;
							case 3:
								logger.info("Viewing the claim for all the customer");
								ViewClaim.viewClaim(user);
								viewFlag = true;
								break;
							default:
								logger.error("User Entered the Wrong choice");
								System.err.println("Enter Integer from 1 to 3");
								viewFlag = false;
								break;
							}
						} catch (InputMismatchException e) {
							logger.error("User Entered the Ivalid Inputs");
							System.err.println("Enter Only digits");
						}

					} while (!viewFlag);
					break;
				case 4:
					logger.info("The report is generating");
					ReportGeneration.reportGeneration(user);
					break;
				default:
					adminFlag = false;
					logger.error("Entering the wrong inputs for the given choice");
					System.err.println("Enter input from 1 to 4");
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Enter Only digits");
				logger.error("Entered the wrong input");
			}
		} while (!adminFlag);

		scanner.close();
	}

}
