package com.capg.uas.client;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;
import com.capg.uas.service.IMacService;
import com.capg.uas.service.MacServiceImpl;

public class MacClientScreen {
	static Logger logger=Logger.getRootLogger();
	static int loginAttempts = 0;
	private Scanner scan;
	private IMacService macService;
	String literal = "**********************************************************************";
	String end = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	
	/*******************************************************************************************************
	 - Function Name	:	start
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	starts the program by the menu to Log in or Quit
	 ********************************************************************************************************/
	public void start() {
		int choice = -1;

		macService = new MacServiceImpl();
		scan = new Scanner(System.in);
		while (choice != 2 && loginAttempts <= 3) {
			System.out.println("\n"+literal);
			System.out.print("1. Login\n2. Quit \n");
			System.out.println("\n"+literal);
			scan = new Scanner(System.in);
			try{
				choice = scan.nextInt();
			
		

			if (choice == 1) {
				System.out.print("Login ID : ");
				String userName = scan.next();
				System.out.print("Password : ");
				String password = scan.next();
				loginAttempts++;
				
				try {
					if (macService.validateMac(userName, password)) {
						System.out.println("\n"+literal);
						System.out.println("Logged in Successfully.....\n");
						
						while(true){
							
							try {
								displayMainMenu();
							} catch (UASException exceptionMsg) {
								System.err.println("please enter valid choice...");
							}
						
						}
						
					} else {
						System.err.println("Please Login Again");
					}
				} catch (UASException exceptionMsg) {
					System.err.println("Login again...");
				}

			} else if (choice == 2) {
				System.out.println("Thank you! Exiting the Application....");
				logger.info("Exiting the Application.....");
				System.exit(0);
			}
			}catch(InputMismatchException exceptionMsg){
				System.err.println("please enter valid choice.");
			}
			
		}
		scan.close();
		System.out.println("Program Terminated.");
		logger.info("Program Terminated");
	}

	
	
	/*******************************************************************************************************
	 - Function Name	:	displayMainMenu
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	display menu for Member of Admission Committee 
	 ********************************************************************************************************/
	public void displayMainMenu() throws UASException{
		int option = 0;
		System.out.println("\nEnter your choice : \n 1. View all scheduled programs\n 2. Update the status of the applicant before interview\n 3. Updating the status of the applicant after interview \n 4. Exit");
	    scan=new Scanner(System.in);
		try{
			option = scan.nextInt();
			switch (option) {
			case 1:
				viewPrograms();

				break;
			case 2:
				updateBeforeInterview();

				break;

			case 3:
				updateAfterInterview();
				break;

			case 4:
				System.out.println("Thank you! Exiting the Application....");
				logger.info("Exiting the Application.....");
				System.exit(0);

			default:
				System.err.println("Please enter a valid input");
			}
		}
		catch(InputMismatchException exceptionMsg){
			throw new UASException("Please enter valid choice...");
		}

		
	}

	
	/*******************************************************************************************************
	 - Function Name	:	viewPrograms
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	viewing program list from database
	 ********************************************************************************************************/
	public void viewPrograms() throws UASException {
		System.out.println("\n\nIt is in viewing mode.....\n");
		try {
			List<ProgramScheduled> programScheduled = macService.listPrograms();
			if (programScheduled != null) {
				System.out.println("Program Names:");
				System.out.println("\n"+literal);
				for (ProgramScheduled program : programScheduled) {
					System.out.println(String.format("\t%s", program.getProgName()));
				}
			} else {
				System.err.println("No Records Found!");
			}
		} catch (UASException exceptionMsg) {
			System.err.println(exceptionMsg.getMessage());
		}

		System.out.println("Showing all Applications.....");
		System.out.println("Enter the name of the program from the options shown above:");
		String pName = scan.next().toUpperCase();
		try {
			List<Applicant> appList = macService.findProgApplicant(pName);
			if (appList != null) {
				System.out.println("\n"+literal);
				for (Applicant applicant : appList) {
				
					System.out.println("\nApplicant's Id   :"+applicant.getAppId()+
										"\nApplicant's Name :"+applicant.getAppName()+
										"\nDOB              :"+applicant.getAppDOB()+
										"\nQualifications   :"+applicant.getQualification()+
										"\nMarks            :"+applicant.getMarks()+
										"\nGoals            :"+applicant.getGoals()+
										"\nEmail            :"+applicant.getEmailId()+
										"\nProgram Id       :"+applicant.getScheduleProgId()+
										"\nStatus           :"+applicant.getStatus()+
										"\nInterview date   :"+applicant.getDateOfInterview());
					System.out.println("\n"+end);
					
				}
				System.out.println("\n"+literal);
			} else {
				System.err.println("No Records Found!");
			
			}
		} catch (UASException exceptionMsg) {
			System.err.println(exceptionMsg.getMessage());
		}
		
		
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	updateBeforeInterview
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	update status before the interview process
	 ********************************************************************************************************/
	private void updateBeforeInterview() throws UASException {
		int choose = 0;
		while(true){
			
		try {
			List<ProgramScheduled> programScheduled = macService.listPrograms();			
			if (programScheduled != null) {
				System.out.println("Program Name");
				System.out.println("\n-----------------------");
				for (ProgramScheduled program : programScheduled) {
					System.out.println(String.format("\t%s", program.getProgName()));
				}
			} else {
				System.err.println("No Records Found!");
			}
		} catch (UASException exceptionMsg) {
			System.err.println(exceptionMsg.getMessage());
		}

		System.out.println("Showing all Applications");
		System.out.println("Enter name of program from the options shown above");
		String pName = scan.next().toUpperCase();
		try {
			List<Applicant> appList = macService
					.findAppliedApplicant(pName);
			if (appList != null) {
				System.out.println("\n"+literal);
				for (Applicant applicant : appList) {
					
					System.out.println("\nApplicant's Id   :"+applicant.getAppId()+
										"\nApplicant's Name :"+applicant.getAppName()+
										"\nDOB              :"+applicant.getAppDOB()+
										"\nQualifications   :"+applicant.getQualification()+
										"\nMarks            :"+applicant.getMarks()+
										"\nGoals            :"+applicant.getGoals()+
										"\nEmail            :"+applicant.getEmailId()+
										"\nProgram Id       :"+applicant.getScheduleProgId()+
										"\nStatus           :"+applicant.getStatus()+
										"\nInterview date   :"+applicant.getDateOfInterview());
					System.out.println("\n"+end);
					
				}
				System.out.println("\n"+literal);
			} else {
				System.err.println("No Records Found!");
				break;
			}
		} catch (UASException exceptionMsg) {
			System.err.println(exceptionMsg.getMessage());
		}

		
		System.out.println("Enter applicant's Id to update status before interview: ");
		int aId = scan.nextInt();
	
		System.out.println("Enter\n 1. Accept the Application\n 2. Reject the Application\n 3. Exit from this menu");

		int opt = scan.nextInt();
		while(true){
			
		if (opt == 2) {

			int isDone = macService.updateStatus(aId);
			System.out.println("Application with ID "
							+ isDone
							+ " is rejected for interview");
			logger.info("Application is rejected");
			System.out.println("Do you want to continue updating the status of other applicants?\nIf yes press 1\nIf no press 0");
			choose = scan.nextInt();
			if (choose==1)
				break;
			else
				displayMainMenu();
			
		} else if (opt == 1) {
			while(true){
			System.out.println("Enter Interview Date in 'DD/MM/YYYY' format: ");
			String doi;
			try{
			doi = scan.next();
		
			
			}
			catch(InputMismatchException exceptionMsg){
				logger.error("Please enter Date of Interview in mentioned format");
				throw new UASException("Please enter Date of Interview in above mentioned format");
			}
			SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
			myFormat.setLenient(false);
			boolean isDate = macService.isValidDoi(doi);
			if (isDate) {
				
				try {
					Date date = myFormat.parse(doi);
					Boolean isFuture=macService.isFutureDate(date);
					if(!isFuture){
						System.err.println("Enter a future date");
						break;
					}
					java.sql.Date doiSql = new java.sql.Date(date.getTime());

					int isDone = macService.assignInterview(aId, doiSql);
					System.out.println("Application with ID "+ isDone+ " is accepted and the Interview is scheduled on "+ doiSql);
					logger.info("Application is accepted");
					System.out.println("Do you want to continue updating the status of other applicants?\nIf yes press 1\nIf no press 0");
					choose = scan.nextInt();
					if (choose==1)
						break;
					else
						displayMainMenu();
				} catch (ParseException exceptionMsg) {
					logger.error("Please enter Date of Interview in mentioned format");
					throw new UASException("Please enter Date of Interview in above mentioned format");
				}
			} else {
				logger.error("Please enter Date of Interview in mentioned format");
				System.err.println("Please enter Date of Interview in above mentioned format");
			}

			}
			if(choose == 1) break;
		}
		else if(opt ==3)
			break;
			}
		
		}
	}

	
	/*******************************************************************************************************
	 - Function Name	:	updateAfterInterview
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	update status after the interview process
	 ********************************************************************************************************/
	public void updateAfterInterview() throws UASException {
		System.out.println("After Interview Process");
		while (true){
			int choose;
		List<Applicant> appList = macService
				.viewInterviewedCandidates();
		if (appList != null) {
			System.out.println("\n"+literal);
			for (Applicant applicant : appList) {
				
				System.out.println("\nApplicant's Id   :"+applicant.getAppId()+
									"\nApplicant's Name :"+applicant.getAppName()+
									"\nDOB              :"+applicant.getAppDOB()+
									"\nQualifications   :"+applicant.getQualification()+
									"\nMarks            :"+applicant.getMarks()+
									"\nGoals            :"+applicant.getGoals()+
									"\nEmail            :"+applicant.getEmailId()+
									"\nProgram Id       :"+applicant.getScheduleProgId()+
									"\nStatus           :"+applicant.getStatus()+
									"\nInterview date   :"+applicant.getDateOfInterview());
				System.out.println("\n"+end);
				
			}
			System.out.println("\n"+literal);
		} else {
			System.err.println("No Records Found!");
			
		}

		System.out
				.println("Enter applicant's Id to update status after interview:");
		
		int intrwId = scan.nextInt();
		System.out.println("Enter 1 to Confirm and 2 to Reject the Application");

		int value = scan.nextInt();
		while(true){

		if (value == 2) {

			int isDone = macService.updateStatus(intrwId);
			System.out.println("Application with ID "+ isDone+ " is rejected for admission");
			System.out.println("Do you want to continue updating the status of other applicants?\nIf yes press 1\nIf no press 0");
			choose = scan.nextInt();
			if (choose==1)
				break;
			else
				displayMainMenu();
		} else if (value == 1) {
			int isDone = macService.updateInterviewStatus(intrwId);
			System.out.println("Application with ID "+ isDone+ " is confirmed for admission");
			int isConfirmed = macService.addParticipant(intrwId);
			logger.info(isConfirmed+"Participant is added");
			System.out.println("Do you want to continue updating the status of other applicants?\nIf yes press 1\nIf no press 0");
			choose = scan.nextInt();
			if (choose==1)
				break;
			else
				displayMainMenu();
		}
	}
		}
	}
}