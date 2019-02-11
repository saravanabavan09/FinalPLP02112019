package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;

public class UserCreation {
	static Logger logger = Logger.getLogger(UserCreation.class);

	public static void profileCreation(UserRole user) throws CRSException, InputMismatchException {
		CRSService crsService = new CRSServiceImpl();
		Scanner scanner = null;
		UserRole userProfile = new UserRole();
		boolean flag2 = false;
		do {
			scanner = new Scanner(System.in);
			logger.info("Creating New username and New password");
			System.out.println("Enter New Username:");
			String userName = scanner.nextLine();
			System.out.println("Enter New Password:");
			String password = scanner.nextLine();
			logger.info("Getting the role from the User ");
			System.out.println("Enter new role code from the following list");
			System.out.println("1.Insured\n2.Agent\n3.Admin");
			boolean flag = false;
			do {
				scanner = new Scanner(System.in);
				int roleCode = 0;
				try {
					roleCode = scanner.nextInt();
					if (roleCode < 4 && roleCode > 0) {
						flag = true;
					} else {
						flag = false;
						logger.error("Entering the input which is not found");
						System.err.println("Enter input from 1 to 4");
					}

				} catch (InputMismatchException e) {
					logger.error("Entering the invalid input");
					System.err.println("Enter valid digits");
					flag = false;
				}

				switch (roleCode) {
				case 1:
					logger.info("Displaying the option as insured");
					userProfile.setRoleCode("INSURED");
					flag = true;
					break;
				case 2:
					logger.info("Displaying the option as agent");
					userProfile.setRoleCode("AGENT");
					flag = true;
					break;
				case 3:
					logger.info("Displayng the option as admin");
					userProfile.setRoleCode("ADMIN");
					flag = true;
					break;
				default:
					flag = false;
					break;
				}

			} while (!flag);
			userProfile.setUserName(userName);
			userProfile.setPassword(password);

			try {
				if (crsService.profileCreation(userProfile)) {
					flag2 = true;
					logger.info("Dispalying the message that the profile is created successfully");
					System.out.println("Profile Created Successfully");
				} else {
					flag2 = false;
					logger.info("Displaying the message that the profile is existing already");
					System.out.println("User already existed");
				}
			} catch (CRSException e) {
				logger.error("Entering the message");
				System.err.println("" + e.getMessage());

			}
		} while (!flag2);

		scanner.close();
	}

}
