package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.AgentClaim;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;
import com.cg.crs.ui.MainUI;

public class AgentViewClaim {
	static Logger logger = Logger.getLogger(AgentViewClaim.class);

	public static void agentViewClaim(UserRole user) throws CRSException {
		int value = 0;
		if (user.getRoleCode().equals("ADMIN")) {
			value = 1;

		}
		Scanner scanner = new Scanner(System.in);
		logger.info("Entering the username of the customer");
		System.out.println("Enter UserName");
		String userName = scanner.nextLine();
		CRSService service = new CRSServiceImpl();
		System.out.println(" ");
		logger.info("Viewing the particular details of the customer");
		System.out.println("=======================" + userName + "'s Claim Status=====================");
		List<AgentClaim> list;
		try {
			list = service.claimData(userName);
			logger.info("Dispalying the claim details of the customer");
			System.out.println("Claim Number\tClaim Reason\tStreet\tCity\tState\tZip\tClaim Type\tPolicyNumber");
			if (!list.isEmpty()) {

				for (AgentClaim entity : list) {
					logger.info("Gettting the details of the customer details");
					System.out.println(entity.getAgentClaimNumber() + "\t" + entity.getAgentClaimReason() + "\t"
							+ entity.getAgentAccidentLocationStreet() + "\t" + entity.getAgentAccidentCity() + "\t"
							+ entity.getAgentAccidentState() + "\t" + entity.getAgentAccidentZip() + "\t"
							+ entity.getAgentClaimType() + "\t" + entity.getAgentPolicyNumber());
				}
			} else {
				logger.error("No database for the particular customer found");
				System.err.println("No data found in database");
			}

		} catch (CRSException e) {
			logger.error("Getting the wrong input");
			System.err.println("Problem Occured while viewing customer claim status ");
		}
		boolean menuFlag = false;
		do {
			System.out.println(" ");
			logger.info("Entering the values either 0 or 1");
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
					logger.info("Displaying the thanking message");
					System.out.println("Thank You");
					System.exit(0);
				} else {
					menuFlag = false;
					logger.error("Entering the invalid input");
					System.err.println("Enter Valid Details");
				}
			} catch (InputMismatchException e) {
				logger.error("Entering the alphabets");
				System.err.println("Enter Only Integers");

			}
		} while (!menuFlag);

		scanner.close();
	}

}
