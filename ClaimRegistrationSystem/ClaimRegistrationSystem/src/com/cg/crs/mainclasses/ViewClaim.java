package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.ClaimCreationEntity;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;
import com.cg.crs.ui.MainUI;

public class ViewClaim {
	static Logger logger=Logger.getLogger(ViewClaim.class);
	public static void viewClaim(UserRole user) throws CRSException {
		Scanner scanner = new Scanner(System.in);
		
		int value = 0;
		if (user.getRoleCode().equals("ADMIN")) {
			logger.debug("Entered into the block if the RoleCode equals to ADMIN ");
			value = 1;
		}
		logger.info("Getting the claim Id to Dispaly the Status of the claim");
		System.out.println("Enter Claim Id");
		long claimId = scanner.nextLong();
		CRSService service = new CRSServiceImpl();
		List<ClaimCreationEntity> list;
		try {
			list = service.viewClaimStatus(claimId);
			System.out.println("Your Claims");
			logger.info("Displaying the status of the claim");
			if (!list.isEmpty()) {

				for (ClaimCreationEntity entity : list) {
					logger.debug("Dispaly the Status of the Customer");
					System.out.println("\nClaim Number:    " + entity.getClaimNumber() + "\nClaim Reason:    "
							+ entity.getClaimReason() + "\nAccident Street: " + entity.getAccidentLocationStreet()
							+ "\nAccident City:   " + entity.getAccidentCity() + "\nAccident State   "
							+ entity.getAccidentState() + "\nAccident Zip     " + entity.getAccidentZip()
							+ "\nClaim Type       " + entity.getClaimType() + "\nPolicy Number       "
							+ entity.getPolicyNumber());
				}
			} else {
				logger.error(" No Datas found in the Database ");
				System.err.println("No data found in database");
			}

		} catch (CRSException e) {
			logger.error("Problem occured While displaying the status ");
			System.err.println("Problem Occured while Viewing Claim Status");
		}

		boolean menuFlag = false;
		do {
			System.out.println(" ");
			logger.info("Entering  the choice from the menu ");
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
					logger.info("when user entered the option 0");
					System.out.println("Thank You");
					System.exit(0);
				} else {
					menuFlag = false;
					logger.error("Entering input other the given option");
					System.err.println("Enter Valid Details");
				}
			} catch (InputMismatchException e) {
				logger.error("User entered the invalid input for the choice");
				System.err.println("Enter Only Integers");

			}
		} while (!menuFlag);

		scanner.close();
	}
}