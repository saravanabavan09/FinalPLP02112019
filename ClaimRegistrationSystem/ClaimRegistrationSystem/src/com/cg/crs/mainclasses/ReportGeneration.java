package com.cg.crs.mainclasses;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.Report;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;
import com.cg.crs.ui.MainUI;

public class ReportGeneration {
	static Logger logger = Logger.getLogger(ReportGeneration.class);

	public static void reportGeneration(UserRole user) throws CRSException {

		Report report = new Report();
		CRSService crsService = new CRSServiceImpl();
		Scanner scanner = new Scanner(System.in);
		boolean flag = false;
		Integer decision = 0;
		boolean flag2 = false;
		List<Report> list = new ArrayList<>();
		do {
			scanner = new Scanner(System.in);
			logger.info("Entering the username");
			System.out.println("Enter username");
			String userName = scanner.nextLine();
			if (crsService.userExists(userName)) {
				flag = true;

				try {
					list = crsService.reportGeneration(userName);
					logger.info("Viewing the detail list");
					System.out.println("1.Policy Number\t2.Claim Number\t3.Claim Type\t4.User Name");
					for (Report report1 : list) {
						logger.info("Displaying the detail list");
						System.out.println(report1.getPolicyNumber() + "\t" + report1.getClaimNumber() + "\t"
								+ report1.getClaimType() + "\t" + userName);
						report.setPolicyNumber(report1.getPolicyNumber());
					}
					do {
						scanner = new Scanner(System.in);
						logger.debug("Checking that whether we want full details or not");
						System.out.println("Do you want detailed view");

						try {
							logger.debug("Selecting either yes or no option");
							System.out.println("1.Yes\n2.No");
							decision = scanner.nextInt();
							if (decision > 2) {
								flag = false;
								logger.error("Entering the values which are not present in the below list");
								System.err.println("Enter values from the below list ");
							} else {
								flag = true;
							}
						} catch (InputMismatchException e) {

							flag = false;
							logger.error("Entering digits are present in the below list");
							System.err.println("Enter digits from the below list");
						}

						switch (decision) {
						case 1:
							flag = true;
							logger.info("Entering the policy number");
							System.out.println("Enter Policy Number");
							do {
								scanner = new Scanner(System.in);

								try {
									Long policyNumber = scanner.nextLong();
									if (Pattern.matches("[0-9]{10}$", policyNumber.toString())) {
										for (Report report2 : list) {

											if (report2.getPolicyNumber().equals(policyNumber)) {
												flag2 = true;
												report.setPolicyNumber(policyNumber);
												break;
											} else {
												logger.error("Entered policy number is not valid");
												System.err.println(
														"Enter valid policy number which you are eligible for");
											}
										}
									} else {
										flag2 = false;
										logger.error("Entering the policy number which is more than 10 numbers");
										System.err.println("Enter 10 digit policy number");
									}

								} catch (InputMismatchException e) {
									logger.error("Entering the invalid input");
									System.err.println("Enter digits");
									flag2 = false;
								}
							} while (!flag2);

							list = crsService.detailedView(report);
							int count = 0;
							for (Report report3 : list) {
								count++;
								logger.info("Displaying the details");
								System.out.println("1.ClaimReason\t: " + report3.getClaimReason()
										+ "\n2.Accident Street: " + report3.getAccidentStreet()
										+ "\n3.Accident City\t: " + report3.getAccidentCity() + "\n4.Accident Zip\t: "
										+ report3.getAccidentZip() + "\n5.Accident State: " + report3.getAccidentState()
										+ "\n6.Claim Type\t: " + report3.getClaimType());
								if (count > 0) {
									break;
								}
							}
							logger.info("Displaying the questions for confirmation");
							System.out.println("7.Question\t\t\t\tAnswer\n");
							count = 0;
							for (Report report2 : list) {

								if (count == 0) {
									logger.info("Displaying the question1");
									System.out.println(
											report2.getClaimQuesDesc1() + "\t\t\t" + report2.getClaimQuesAns1());
								} else {
									logger.info("Displaying the answer for the question1");
									System.out.println(report2.getClaimQuesDesc1() + "\t" + report2.getClaimQuesAns1());
								}
								count++;
							}

							break;
						case 2:
							boolean menuFlag = false;
							do {
								System.out.println(" ");
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
										Admin.adminClient(user);
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

						default:
							break;
						}
					} while (!flag);

				} catch (CRSException e) {

					System.err.println(e.getMessage());
				}
			} else {
				flag = false;
				logger.error("Entered username is not valid");
				System.err.println("Enter Valid Username");
			}
		} while (!flag);
		scanner.close();
	}
}
