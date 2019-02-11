package com.cg.crs.mainclasses;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.crs.exception.CRSException;
import com.cg.crs.model.ClaimCreationEntity;
import com.cg.crs.model.PolicyEntity;
import com.cg.crs.model.UserRole;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.implementation.CRSServiceImpl;

public class AdminClaim {
	static Logger logger = Logger.getLogger(AdminClaim.class);
	static long policyNumber = 0;

	public static void claimCreation(UserRole user) {
		PropertyConfigurator.configure("resources/log4j.properties");
		Scanner scanner = null;
		CRSService service = new CRSServiceImpl();
		List<PolicyEntity> list = null;
		try {
			list = service.getAllPolicies();
			logger.info("Policy List is Displayed");
			System.out.println("===============================Policy List===============================");
			System.out.println(" ");
			if (!list.isEmpty()) {

				for (PolicyEntity entity : list) {
					logger.debug("Entered into the loop and Display the Details present in database");
					System.out.println("Policy Number   Premium Amount  Account Number");
					System.out.println(entity.getPolicyNumber() + "      " + entity.getPolicyPremium() + "           "
							+ entity.getAccountNumber());
					System.out.println(" ");

				}
			} else {
				logger.error("Display when the datas not present in database");
				System.err.println("No data found ");
			}

		} catch (CRSException e1) {
			logger.error("Catch  displays the CRSException");
			System.err.println("Problem Occured while Viewing Policies");
		}

		boolean doPolicyNumberFlag = false;
		boolean validatePolicyNumber = false;
		do {
			scanner = new Scanner(System.in);
			logger.info("Entering the policy number");
			System.out.println("Enter the policy number of your policy: ");

			try {
				policyNumber = scanner.nextLong();
				logger.info("Validating for the given policy number");
				validatePolicyNumber = service.CheckPolicyNumber(policyNumber);
				if (validatePolicyNumber == false) {
					logger.error("Entering the policy number out of the limit");
					System.err.println("Enter the policy number of 10 digits");
					doPolicyNumberFlag = false;
				} else {

					for (PolicyEntity entity : list) {
						Long policyNo = entity.getPolicyNumber();
						if (policyNo == policyNumber) {
							boolean checkAlreadyClaimed = service.checkAlreadyClaimed(policyNumber);
							if (checkAlreadyClaimed) {
								doPolicyNumberFlag = true;
							} else {
								logger.error("Entering the policy number which is already claimed");
								System.err.println("Already Claimed for the given Policy Number");
							}
						}
					}

					if (!doPolicyNumberFlag) {
						logger.error("Entering the policy which is not present in the above list");
						System.err.println("Enter the Valid Policy Number from the above shown list");
					}

				}
			} catch (InputMismatchException e) {
				logger.error("Entering input is not a digit");
				System.err.println("Enter only digits");
			} catch (CRSException e) {
				logger.error("Entered input is not valid");
				System.err.println("Problem Occured while checking policy is claim or not");

				e.printStackTrace();
			}
		} while (!doPolicyNumberFlag);

		if (validatePolicyNumber) {
			String claimReason;
			boolean doClaimReason = false;
			boolean validateClaimReason;
			do {
				scanner = new Scanner(System.in);
				logger.debug("Entering the reason for claiming");
				System.out.println("Enter the reason of the claim: ");
				claimReason = scanner.nextLine();
				validateClaimReason = service.CheckClaimReason(claimReason);
				if (validateClaimReason == false) {
					logger.error("Entering the reason in lowercase");
					System.err.println("The reason should always starts with uppercase");
					doClaimReason = false;
				} else {
					doClaimReason = true;
				}
			} while (doClaimReason == false);

			if (validateClaimReason) {
				String accidentLocationStreet;
				boolean validateAccidentLocationStreet;
				boolean doAccidentLocationStreet = false;
				do {
					scanner = new Scanner(System.in);
					logger.debug("Entering the  Insured street ");
					System.out.println("Enter the Insured street: ");
					accidentLocationStreet = scanner.nextLine();
					validateAccidentLocationStreet = service.CheckAccidentLocationStreet(accidentLocationStreet);
					if (validateAccidentLocationStreet == false) {
						logger.error("Entering the street name in lowercase");
						System.err.println("The Street name should always starts with uppercase");
						doAccidentLocationStreet = false;

					} else {
						doAccidentLocationStreet = true;
					}

				} while (doAccidentLocationStreet == false);

				if (validateAccidentLocationStreet) {
					String accidentCity;
					boolean validateAccidentCity;
					boolean doAccidentCity = false;
					do {
						scanner = new Scanner(System.in);
						logger.debug("Entering the Insured  city ");
						System.out.println("Enter the  Insured city: ");
						accidentCity = scanner.nextLine();
						validateAccidentCity = service.CheckAccidentCity(accidentCity);
						if (validateAccidentCity == false) {
							logger.error("Entering the city name in lowercase");
							System.err.println("The City name should always starts with uppercase");
							doAccidentCity = false;

						} else {
							doAccidentCity = true;
						}

					} while (doAccidentCity == false);
					if (validateAccidentCity) {
						String accidentState;
						boolean validateAccidentState;
						boolean doAccidentState = false;
						do {
							scanner = new Scanner(System.in);
							logger.debug("Entering the Insured state");
							System.out.println("Enter the Insured state: ");
							accidentState = scanner.nextLine();
							validateAccidentState = service.CheckAccidentState(accidentState);
							if (validateAccidentState == false) {
								logger.error("Entering the state name in lowercase");
								System.err.println("The State should always starts with uppercase");
								doAccidentState = false;

							} else {
								doAccidentState = true;
							}

						} while (doAccidentState == false);
						if (validateAccidentState) {
							long accidentZip = 0;
							boolean validateAccidentZip = false;
							boolean doAccidentZip = false;
							do {
								scanner = new Scanner(System.in);
								logger.debug("Entering the Insured postal code");
								System.out.println("Enter the Insured postal code : ");
								try {
									accidentZip = scanner.nextLong();
									validateAccidentZip = service.CheckAccidentZip(accidentZip);
									if (validateAccidentZip == false) {
										logger.error("Entering the postal code more than 5 digits");
										System.err.println("The Postal code must be only digits with 5 numbers");
										doAccidentZip = false;

									} else {
										doAccidentZip = true;
									}
								} catch (InputMismatchException e) {
									logger.error("Not entering the valid inputs");

									System.err.println("Enter Only Digits");

								}

							} while (doAccidentZip == false);

							if (validateAccidentZip) {
								boolean selectClaimTypeFlag = false;
								String claimType = " ";
								do {
									scanner = new Scanner(System.in);
									logger.info("Selecting the choice from the below options");
									System.out.println("Select the type which you want to claim: ");
									System.out.println("1. EarthQuake.");
									System.out.println("2. Flood.");
									System.out.println("3. Collision.");
									System.out.println("4. Fire.");
									System.out.println("5. Hurricane.");
									logger.info("Selecting the choice from the above options");
									try {

										int selectClaimType = scanner.nextInt();
										selectClaimTypeFlag = true;
										switch (selectClaimType) {
										case 1:
											logger.info("Selecting the reason as earthquake");
											claimType = "EarthQuake";
											break;
										case 2:
											logger.info("Selecting the reason as Flood");
											claimType = "Flood";
											break;
										case 3:
											logger.info("Selecting the reason as collision");
											claimType = "Collision";
											break;
										case 4:
											logger.info("Selecting the reason as Fire");
											claimType = "Fire";
											break;
										case 5:
											logger.info("Selecting the reason as Hurricane");
											claimType = "Hurricane";
											break;
										default:
											selectClaimTypeFlag = false;
											logger.error("Selecting the option more than 5");
											System.err.println("Select any options from 1 to 5");
											break;
										}
									} catch (InputMismatchException e) {
										logger.error("Entering the invalid input");
										System.err.println("Enter the valid details of integer type");
									}
								} while (!selectClaimTypeFlag);

								ClaimCreationEntity claimCreation = new ClaimCreationEntity();
								claimCreation.setClaimReason(claimReason);
								claimCreation.setAccidentLocationStreet(accidentLocationStreet);
								claimCreation.setAccidentCity(accidentCity);
								claimCreation.setAccidentState(accidentState);
								claimCreation.setAccidentZip(accidentZip);
								claimCreation.setClaimType(claimType);
								claimCreation.setPolicyNumber(policyNumber);

								try {
									service.insertClaimDetails(claimCreation);

									scanner.nextLine();
									boolean doYesNo = false;
									do {
										scanner = new Scanner(System.in);
										logger.debug("Entering the value either yes or no");
										System.out.println("Enter Yes or No to enter Claim Details Screen");
										String claimDetails = scanner.nextLine();
										if (claimDetails.equals("Yes") || claimDetails.equals("yes")
												|| claimDetails.equals("Y") || claimDetails.equals("y")) {
											doYesNo = true;
											ClaimQuestions.claimQuestion(user);
										} else if (claimDetails.equals("No") || claimDetails.equals("no")
												|| claimDetails.equals("n") || claimDetails.equals("N")) {
											logger.debug("Displaying the thanking message");
											System.out.println("Thank You");
										} else {
											logger.debug("Entering the value either yes or no or Y/N");
											System.out.println("Enter only Yes or No or Y/N");
										}
									} while (!doYesNo);

								} catch (CRSException e) {
									e.printStackTrace();
									logger.error("Entering the invalid input");
									System.err.println("Problem Occured while Claim Creation");

								}
							}
						}
					}
				}
			}
		}
		scanner.close();

	}
}
