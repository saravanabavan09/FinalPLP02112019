package com.cg.crs.ui;

import java.util.Scanner;

import com.cg.crs.exception.CRSException;
import com.cg.crs.mainclasses.Admin;
import com.cg.crs.mainclasses.Claim;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;

public class MainUI {

	public static void main(String[] args) {
		Scanner scanner = null;
		CRSService crsService = new CRSServiceImpl();
		System.out
				.println("* * * * * * * * * * * * Insurance Claiming * * * * * * * * * * * * ");
		boolean loginFlag = false;
		do {
			scanner = new Scanner(System.in);
			System.out.println("Enter Username:");
			String userName = scanner.nextLine();
			System.out.println("Enter Password:");
			String password = scanner.nextLine();
			UserRole user = new UserRole();
			user.setUserName(userName);
			user.setPassword(password);
			boolean services;
			try {
				services = crsService.validateFields(user);
				if (services == true) {
					switch (user.getRoleCode()) {
					case "INSURED":
						System.out.println(" ");
						System.out
						.println("==============================Insured Portal==============================");
						System.out.println("\t\t\t      Welcome "+user.getUserName());
						Claim.claimCreation(user);
						loginFlag = true;
						break;
					case "AGENT":
						System.out.println(" ");
						System.out
						.println("==============================Agent Portal==============================");
						System.out.println("\t\t\t       Welcome "+user.getUserName());
						Claim.claimCreation(user);
						loginFlag = true;
						break;
					case "ADMIN":
						System.out.println(" ");
						System.out.println("==============================Admin Portal==============================");
						System.out.println("\t\t\t     Welcome "+user.getUserName());
					
						Admin.adminClient(user);
						loginFlag = true;
						break;
					default:
						loginFlag = false;
						break;
					}
				} else {
					System.out.println("Enter Valid Username/Password");
					loginFlag = false;
				}
			} catch (CRSException e) {
				loginFlag = false;
				System.err.println("Error Occured while Logging In");
			}

		} while (!loginFlag);
		scanner.close();
	}
}