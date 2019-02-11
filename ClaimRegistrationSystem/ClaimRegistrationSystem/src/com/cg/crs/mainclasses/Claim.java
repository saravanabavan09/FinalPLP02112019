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

public class Claim {
	static Logger logger = Logger.getLogger(Claim.class);
	static long policyNumber = 0;

	public static void claimCreation(UserRole user) {
		PropertyConfigurator.configure("resources/log4j.properties");
		Scanner scanner = null;
		int choice = 0;
		boolean choiceFlag = false;
		CRSService service = new CRSServiceImpl();
		List<PolicyEntity> list = null;
		try {
			list = service.getPolicies(user);
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
				System.err.println("No Policies found in the database");
			}
		} catch (CRSException e1) {
			logger.error("Catch  displays the CRSException");
			System.err.println("Problem Occured while Viewing Policies");
		}

		do {
			scanner = new Scanner(System.in);
			logger.info("Seelcting the choice from the below option");
			System.out.println("1. Claim Creation");
			System.out.println("2. View the claim status");
			try {
				logger.info("Entering the choice");
				System.out.println("Enter choice");
				choice = scanner.nextInt();
				choiceFlag = true;

				switch (choice) {
				case 1:
					boolean doPolicyNumberFlag = false;
					boolean validatePolicyNumber;

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
						}
					} while (!doPolicyNumberFlag);

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
							System.err.println(
									"The reason should always starts with uppercase of minimum length 3 characters and maximum length of 30 characters");
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
							logger.debug("Entering the Insured street ");
							System.out.println("Enter the Insured street: ");
							accidentLocationStreet = scanner.nextLine();
							validateAccidentLocationStreet = service
									.CheckAccidentLocationStreet(accidentLocationStreet);
							if (validateAccidentLocationStreet == false) {
								logger.error("Entering the street name in lowercase");
								System.err.println(
										"The Street name should always starts with uppercase of minimum length 3 characters and maximum length of 30 characters");
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
								logger.debug("Entering the Insured city");
								System.out.println("Enter the Insured city : ");
								accidentCity = scanner.nextLine();
								validateAccidentCity = service.CheckAccidentCity(accidentCity);
								if (validateAccidentCity == false) {
									logger.error("Entering the city name in lowercase");
									System.err.println(
											"The City name should always starts with uppercase of minimum length 3 characters and maximum length of 30 characters");
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
									System.out.println("Enter the Insured state ");
									accidentState = scanner.nextLine();
									validateAccidentState = service.CheckAccidentState(accidentState);
									if (validateAccidentState == false) {
										logger.error("Entering the state name in lowercase");
										System.err.println(
												"The State should always starts with uppercase of minimum length 3 characters and maximum length of 30 characters");
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
										System.out.println("Enter the Insured postal code");
										try {
											accidentZip = scanner.nextLong();
											validateAccidentZip = service.CheckAccidentZip(accidentZip);
											if (validateAccidentZip == false) {

												logger.error("Entering the postal code more than 5 digits");
												System.err.println("The Postal code must be only 5 digits");
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
													System.exit(0);
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
					break;

				case 2:
					if (user.getRoleCode().equals("INSURED")) {
						ViewClaim.viewClaim(user);
					} else if (user.getRoleCode().equals("AGENT")) {
						AgentViewClaim.agentViewClaim(user);
					}
					break;
				default:
					choiceFlag = false;
					System.err.println("input should be in the range of (1-2)");
					System.err.println("Enter your input again");
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Enter the valid details");
				System.err.println("Enter you input again");

			} catch (CRSException e) {
				System.err.println("Problem Occured while checking policy is claim or not");
			}

		} while (!choiceFlag);
		scanner.close();

	}
}
