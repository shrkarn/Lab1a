package com.capg.uas.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramOffered;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;
import com.capg.uas.service.AdminServiceImpl;
import com.capg.uas.service.IAdminService;
import com.capg.uas.service.IMacService;
import com.capg.uas.service.MacServiceImpl;


public class AdminClientScreen {

	
	static int loginAttempts = 0;
	private Scanner scan;
	private int choose;
	private IAdminService adminService;
	private IMacService macService;
	private SimpleDateFormat myFormat;
	static Logger logger=Logger.getRootLogger();
	String literal ="**********************************************************************";
	
	/*******************************************************************************************************
	 - Function Name	:	start()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	starts the Administrator client by logging admin and 
	 						using switch to display functionality of Client layer
	 ********************************************************************************************************/
	public void start() {
		int choice = -1;

		adminService = new AdminServiceImpl();
		scan = new Scanner(System.in);
		myFormat = new SimpleDateFormat("dd/MM/yyyy");
		myFormat.setLenient(false);
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
					if (adminService.validateAdmin(userName, password)) {
						System.out.println("\n"+literal);
						System.out.println("Logged in Successfully.....\n");
						
						while(true){
							
							try {
								displayMainMenu();
							} catch (UASException e) {
								System.err.println("please enter valid choice...");
							}
						
						}
						
					} else {
						System.out.println("Please Login Again");
					}
				} catch (UASException e) {
					System.err.println("Login again...");
				}

			} else if (choice == 2) {
				System.out.println("Thank you! Exiting the Application....");
				logger.info("Exiting the Application.....");
				System.exit(0);
			}
			}catch(InputMismatchException e){
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
	 - Description		:	display menu for Administrator
	 ********************************************************************************************************/
	public void displayMainMenu() throws UASException{
		int option ;
		
		System.out.println("\n"+literal);
		System.out.println(" Enter your Choice from below");
		System.out.println(" 1. Manage Programs Offered by the University");
		System.out.println(" 2. Manage Programs Scheduled");
		System.out.println(" 3. View Reports");
		System.out.println(" 4. Exit");
		System.out.println(literal+"\n");
		scan = new Scanner(System.in);
		try{
			option = scan.nextInt();
	
			switch (option) {
			case 1:
				managePrograms();
				break;
	
				
			case 2: 
				manageProgramSchedule();							
				break;
				
			case 3: 
				displayLists();
				break;
			
			case 4:
				System.out.println("Thank you! Exiting the Application....");
				logger.info("Exiting the Application.....");
				System.exit(0);
			
			default:
				defaultInput();
			}
		}
		catch(InputMismatchException e){
			throw new UASException("Please enter valid choice...");
		}
	}
	

	/*******************************************************************************************************
	 - Function Name	:	managePrograms()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	display functionality of AdminClient layer and 
	 						manages Programs Offered 
	 ********************************************************************************************************/
	public void managePrograms(){
		System.out.println("Program Management mode activated");
		while(true){
			scan = new Scanner(System.in);
			choose = 0;
			System.out.println("\n"+literal);
			System.out.println(" Enter your Choice from below");
			System.out.println(" 1. Add Program ");
			System.out.println(" 2. Update Program");
			System.out.println(" 3. Delete Program");
			System.out.println(" 4. Back to Administrator");
			System.out.println(" 5. Exit");
			System.out.println(literal+"\n");
			choose = scan.nextInt();
			
			
			
			switch(choose){
			case 1:				
				try {
					addPrograms();
				} catch (UASException e) {
					logger.error("error occured");
				}				
				break;
				
			case 2:				
				updatePrograms();				
				break;
				
			case 3:				
				deletePrograms();				
				break;
				
			case 4:
				try {
					displayMainMenu();
				} catch (UASException exception) {
					logger.error("Admin Main Menu not reached");
					System.err.println("Error Occurred. Sorry regarding Problem");
				}
				break;
				
			case 5:
				logger.info("Programs Offered by University managed. Exiting.... ");
				System.out.println("Programs offered by university have been managed");
				System.out.println("Thank you. \nPlease come again.");
				System.exit(0);
				break;
			
			default:
				defaultInput();
			}

		
		}
		
	}

	/*******************************************************************************************************
	 - Function Name	:	manageProgramSchedule()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	display functionality of AdminClient layer and 
	 						manages Programs Scheduled 
	 ********************************************************************************************************/
	public void manageProgramSchedule(){
		System.out.println("Program Schedule Management mode activated");
		while(true){
			scan = new Scanner(System.in);
			int choose;
			System.out.println("\n"+literal);
			System.out.println(" Enter your Choice from below");
			System.out.println(" 1. Add Program ");
			System.out.println(" 2. Delete Program");
			System.out.println(" 3. Back to Administrator");
			System.out.println(" 4. Exit");
			System.out.println(literal+"\n");
			choose = scan.nextInt();
			
			
			
			switch(choose){
			case 1:
				try {
					addProgramSchedule();
				} catch (UASException e) {
					System.err.println("Error Occurred. Please try again");
					logger.error("Program Schedule cannot be added as error occurs");
				}
				break;
				
			case 2:
				deleteProgramSchedule();
				break;
				
			case 3:
				try {
					displayMainMenu();
				} catch (UASException exception) {
					logger.error("Admin Main Menu not reached");
					System.err.println("Error Occurred. Sorry regarding Problem");
				}
				break;
				
			case 4:
				logger.info("Programs Schedule managed. Exiting.... ");
				System.out.println("Schedule of Programs has been managed");
				System.out.println("Thank you. \nPlease come again.");
				System.exit(0);
				break;
			
			default:
				defaultInput();
			}

		
		}
		
	}

	/*******************************************************************************************************
	 - Function Name	:	displayLists()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	13/10/2018
	 - Description		:	displays the various lists to the client
	 ********************************************************************************************************/
	public void displayLists() {
		
		System.out.println("Program Schedule Management mode activated");
		while(true){
			int choose;
			System.out.println("\n"+literal);
			System.out.println(" Enter your Choice from below");
			System.out.println(" 1. Applicant Report ");
			System.out.println(" 2. Report of Programs Scheduled in given time period");
			System.out.println(" 3. Back to Administrator");
			System.out.println(" 4. Exit");
			System.out.println("\n"+literal);
			choose = scan.nextInt();		
			
			switch(choose){
			case 1:
				viewApplicantReports();
				break;
				
			case 2:
				viewProgramSchedule();
				break;
				
			case 3:
				try {
					displayMainMenu();
				} catch (UASException exception) {
					logger.error("Admin Main Menu not reached");
					System.err.println("Error Occurred. Sorry regarding Problem");
				}
				break;
				
			case 4:
				logger.info("Reports viewed. Exiting...");
				System.out.println("Reports have been shown");
				System.out.println("Thank you. \nPlease come again.");
				System.exit(0);
				break;
			
			default:
				defaultInput();
			}

		
		}
		
	}

	
	/*******************************************************************************************************
	 - Function Name	:	addPrograms()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	13/10/2018
	 - Description		:	takes input from admin for adding Programs Offered  
	 ********************************************************************************************************/
	public void addPrograms() throws UASException{
		
		ProgramOffered progOffered = new ProgramOffered();
		String proName;
		
		System.out.print("Enter Program Name : ");
		proName = scan.next();

		if(!adminService.programNameCheck(proName.toUpperCase())){
	
		System.out.println("Entering the Program Name : "+proName.toUpperCase());
		progOffered.setProgName(proName.toUpperCase());
		System.out.print("Enter Program Description :");
		scan.nextLine();
		progOffered.setDesc(scan.nextLine());
		System.out.print("Enter Applicant Eligibility :");
		progOffered.setAppEligibility(scan.nextLine());
		System.out.print("Enter Program Duration(in months) : ");
		try{
			progOffered.setDuration(scan.nextInt());
		}catch(InputMismatchException inputException){
			scan.next();
			System.err.println("Input Mismatch error.\n Please enter a number");
			throw new UASException("Input Mismatch");
		}
		System.out.print("Enter details of Degree Offered :");
		progOffered.setDegreeOffered(scan.next());
		
		try {
			String progName = adminService.addProgramOffered(progOffered);
			if (progName == null){
				System.err.println("Program addition Failed!");
				logger.info("Program addition failed");
			} else {
				System.out.println("Program Added! with name " + progName);
				logger.info("Program Added");
			}
		} catch (UASException e) {
			logger.info("Program offer could not be added");
		}
		} else {
			System.err.println("Program Name already exists");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new UASException("Problem in Sleep");
			}
			managePrograms();
		}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	updatePrograms()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	13/10/2018
	 - Description		:	takes input from admin for updating Programs Offered  
	 ********************************************************************************************************/
	public void updatePrograms() {
		
		ProgramOffered progOffered = new ProgramOffered();
		String proName;
		
		System.out.print("Enter Program Name which needs to be updated : ");
		proName = scan.next();
		System.out.println("\nEntering the Program Name to be updated : "+proName.toUpperCase());
		progOffered.setProgName(proName.toUpperCase());
		
		
		System.out.println("Enter Updated Program Description :");
		scan.nextLine();
		progOffered.setDesc(scan.nextLine());
		System.out.println("Enter Updated Applicant Eligibility :");
		progOffered.setAppEligibility(scan.nextLine());
		
		try {
			String progName = adminService.updateProgramOffered(progOffered);
			if (progName == null){
				System.err.println("Program Update Failed!");
				logger.info("Program not Updated");
			} else {
				System.out.println("Program " + progName+" Updated!");
				logger.info("Program Updated");
			}
		} catch (UASException e) {
			logger.info("Program offer could not be updated");
		}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	deletePrograms()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	13/10/2018
	 - Description		:	takes input from admin for deleting Programs Offered by University 
	 ********************************************************************************************************/
	public void deletePrograms(){
		
		try {
			
			List<ProgramOffered> programOfferedList;
			
			programOfferedList = adminService.getAllOfferedPrograms();

			if (!programOfferedList.isEmpty()) {
				System.out.println("Program Name\t\tDescription\tApplicant Eligibilty\tDuration\tDegree Offered");
				System.out.println("-------------------------------------------------------------------------------------------------------");
				for (ProgramOffered programOffered : programOfferedList) {
					System.out.println(String.format("%s\t\t%s\t%s\t%s\t%s",
									programOffered.getProgName(), programOffered.getDesc(),
									programOffered.getAppEligibility(), programOffered.getDuration(),
									programOffered.getDegreeOffered()));
				}
			} else {
				System.out.println("No Records Found!");
			}
		} catch (UASException e) {
			logger.info("Program offered can not be displayed");
		}
		
		System.out.print("Enter Program Name which needs to be deleted : ");
		String progName = scan.next();
		
		try {
			boolean success = adminService.deleteProgramOffered(progName.toUpperCase());
			if (!success){
				System.err.println("Program Deletion Failed!");
				logger.info("Program Deletion Failed");
			}else{
				System.out.println("Program " + progName.toUpperCase()+" Deleted!");
				logger.info("Program Deleted");
			}
		} catch (UASException e) {
			logger.info("Program offer could not be deleted");
		}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	addProgramSchedule()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	13/10/2018
	 - Description		:	takes input from admin for adding Programs Scheduled  
	 ********************************************************************************************************/
	public void addProgramSchedule() throws UASException {
		
		ProgramScheduled progScheduled = new ProgramScheduled();
		
		System.out.print("Enter Scheduled Program Id : ");
		String scheduleProgId = scan.next();
		
		if(!adminService.programIdCheck(scheduleProgId.toUpperCase()))
		{
		
		System.out.println("\nEntering the Scheduled Program ID : "+scheduleProgId.toUpperCase());
		progScheduled.setScheduleProgId(scheduleProgId.toUpperCase());
		
		try {
			
			List<ProgramOffered> programOfferedList ;
			
			programOfferedList = adminService.getAllOfferedPrograms();

			if (!programOfferedList.isEmpty()) {
				System.out.println("Program Name");
				System.out.println("-------------------------------------------------------------------------------------------------------");
				for (ProgramOffered programOffered : programOfferedList) {
					System.out.println(String.format("%s",
									programOffered.getProgName()));
				}
			} else {
				System.out.println("No Records Found!");
			}
		} catch (UASException e) {
			System.err.println("Program offered list unaccesible");
			logger.info("Program offered list unaccesible");
		}
		
		System.out.print("\nEnter Program Name from the above list : ");
		String progName = scan.next();
		System.out.println("\nEntering the Scheduled Program ID : "+progName.toUpperCase());
		progScheduled.setProgName(progName.toUpperCase());
		System.out.print("Enter Location :");
		scan.nextLine();
		progScheduled.setLocation(scan.nextLine());
		System.out.print("Enter Start Date of Program in DD/MM/YYYY format:");
		String startDate = scan.nextLine();
		
		if(null != startDate && startDate.trim().length() > 0){
		    try {
				Date start = myFormat.parse(startDate);
				java.sql.Date startDateSQL = new java.sql.Date(start.getTime());
				progScheduled.setStart(startDateSQL);
			} catch (ParseException e) {
				System.err.println("Date is of Invalid format. Please try again");
				manageProgramSchedule();
			}
		}
	
		System.out.print("Enter End Date of Program in DD/MM/YYYY format: ");
		String endDate = scan.nextLine();
		
		if(null != endDate && endDate.trim().length() > 0){
		    try {
				Date end =  myFormat.parse(endDate);
				java.sql.Date endDateSQL = new java.sql.Date(end.getTime());
				progScheduled.setEnd(endDateSQL);
			} catch (ParseException e) {
				System.err.println("Date is of Invalid format. Please try again");
				manageProgramSchedule();
			}
		}
		
		System.out.print("Enter No. of Sessions per Week :");
		try {
			progScheduled.setSessionsPerWeek(scan.nextInt());
			
		} catch (InputMismatchException e) {
			logger.info("Invalid No. of Sessions. Please try again");
			System.err.println("Invalid Marks. Please Try Again with Correct Details");
			throw new UASException("\n Invalid Marks. Please Try Again with Correct Details");					
		}
		
		
		try {
			String scheduledProgId = adminService.addProgramScheduled(progScheduled);
			if (scheduledProgId == null) {
				System.err.println("Program Schedule Addition Failed!");
				logger.info("Program Schedule addition failed");
			} else {
				System.out.println("Program Schedule Added! with id " + scheduledProgId);
				logger.info("Program Schedule added");
			}
		} catch (UASException e) {
			logger.info("Program schedule can not be added");
		}
		}else{
			System.err.println("Program Schedule Id already exists");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new UASException("Problem in Sleep");
			}
			manageProgramSchedule();
		}
	}

	/*******************************************************************************************************
	 - Function Name	:	deleteProgramSchedule()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	takes input from admin for deleting Programs Scheduled  
	 ********************************************************************************************************/
	public void deleteProgramSchedule() {
			macService = new MacServiceImpl();
		try {
			List<ProgramScheduled> programScheduled = macService.listPrograms();			
			if (programScheduled != null) {
				System.out.println("Program Id\tProgram Name\tLocation\tStart Date\tEnd Date\tSessions per Week");
				System.out.println("-------------------------------------------------------------------------------------------------------");
				for (ProgramScheduled programSchedule : programScheduled) {
					System.out.println(String.format("%s\t\t%s\t\t%s\t\t%s\t%s\t\t%s",
									programSchedule.getScheduleProgId(), programSchedule.getProgName(), 
									programSchedule.getLocation(),programSchedule.getStart(), 
									programSchedule.getEnd(),programSchedule.getSessionsPerWeek()));
				}				
			} else {
				System.out.println("No Records Found!");
			}	
		} catch (UASException e) {
			logger.error("error occured");
			System.err.println("Error occured in fetching program Schedule list");
		}
		System.out.print("\nEnter Scheduled Program ID which needs to be deleted : ");
		String progId = scan.next();
		System.out.println("\nEntering the Scheduled Program ID : "+progId.toUpperCase());
		
		try {
			int applicants = adminService.deleteProgramScheduled(progId.toUpperCase());
			if (applicants != 0){
				System.out.println("Program  Schedule" + progId.toUpperCase()+" has "+applicants+" applicants");
				System.err.println("Program Schedule Deletion Failed!");
				logger.info("Program Schedule not deleted");
			} else {
				System.out.println("Program  Schedule" + progId.toUpperCase()+" has "+applicants+" applicants");
				System.out.println("Program Schedule" + progId.toUpperCase()+" Deleted!");
				logger.info("Program Schedule deleted");
			}
		} catch (UASException e) {
			logger.info("Program schedule can not be deleted");
		}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	viewProgramSchedule()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	takes input from admin and view Programs Scheduled in a given time period
	 ********************************************************************************************************/
	public void viewProgramSchedule() {
		
		try {
			System.out.println("Reports of programs to be commenced in the given time period");
			
			java.sql.Date  fromDateSQL = null;
			java.sql.Date  toDateSQL = null;
			System.out.print("Enter Start Date of Program in DD/MM/YYYY format:");
			scan.nextLine();
			String fromDate = scan.nextLine();
			
			if(null != fromDate && fromDate.trim().length() > 0){
				
			    try {
					Date start = myFormat.parse(fromDate);
					fromDateSQL = new java.sql.Date(start.getTime());
					
				} catch (ParseException e) {
					
					System.err.println("Date is of Invalid format. Please try again");
				}
			}
			else{
				System.err.println("Please enter date");
			}

			System.out.print("Enter End Date of Program in DD/MM/YYYY format: ");
			String toDate = scan.nextLine();
			
			if(null != toDate && toDate.trim().length() > 0){
				
			    try {
					Date end =  myFormat.parse(toDate);
					toDateSQL = new java.sql.Date(end.getTime());
					
				} catch (ParseException e) {
					System.err.println("Date is of Invalid format. Please try again");
					
				}
			}else{
				System.err.println("Please enter date");
			}

			List<ProgramScheduled> programScheduleList ;
			
			
				programScheduleList = adminService.getDatedProgramsSchedule(fromDateSQL,toDateSQL);
			

			if (!programScheduleList.isEmpty()) {
				System.out.println("Program Id\tProgram Name\tLocation\tStart Date\tEnd Date\tSessions per Week");
				System.out.println("-------------------------------------------------------------------------------------------------------");
				for (ProgramScheduled programScheduled : programScheduleList) {
					System.out.println(String.format("%s\t\t%s\t\t%s\t\t%s\t%s\t\t%s",
									programScheduled.getScheduleProgId(), programScheduled.getProgName(), 
									programScheduled.getLocation(),programScheduled.getStart(), 
									programScheduled.getEnd(),programScheduled.getSessionsPerWeek()));
				}
				logger.info("Program Schedule list Displyed");
			} else {
				System.out.println("No Records Found!");
				logger.info("No Program Schedule found in Date range");
				throw new UASException("No Records");
			}
			} catch (UASException e) {
				logger.info("Problem in accesing program scheduled reports");
			}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	viewApplicantReports()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	takes input from admin and views Applicant Report based on status and scheduled
	  						program id
	 ********************************************************************************************************/
	public void viewApplicantReports() {
		
		Applicant applicants = new Applicant();
		
		try {	
			System.out.println("Applicant Report View Initiated");
			System.out.println("*************************************************************************");
			System.out.print("Enter Scheduled Program Id : ");
			String scheduleProgId = scan.next();
			System.out.println("\nEntering the Scheduled Program ID : "+scheduleProgId.toUpperCase());
			applicants.setScheduleProgId(scheduleProgId.toUpperCase());
			
			System.out.println("\nAPPLICANT'S ACCEPTED");
			applicants.setStatus("ACCEPTED");
			List<Applicant> confirmList;
			
				confirmList = adminService.viewCandidates(applicants);
			
			
			if (confirmList != null) {
				System.out
					.println("Applicant Id\tApplicant Name\tDate of Birth\tQualification\tMarks\tGoals\t\tEmail\t\tProg Id\t\tInterview date");
				System.out
					.println("-----------------------------------------------------------------------------------------------------------------------------------");
				for (Applicant applicant : confirmList) {
				System.out
					.println(String
						.format("%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\t%s",
								applicant.getAppId(),
								applicant.getAppName(),
								applicant.getAppDOB(),
								applicant.getQualification(),
								applicant.getMarks(),
								applicant.getGoals(),
								applicant.getEmailId(),
								applicant.getScheduleProgId(),
								applicant.getDateOfInterview()));
				}
				logger.info("Accepted Applicant list Displyed");
			} else {
				System.out.println("No Records Found!");
				logger.info("No Accepted Applicants found");
			}

			System.out.println("\nAPPLICANT'S CONFIRMED FOR INTERVIEW");
			applicants.setStatus("CONFIRMED");
			List<Applicant> appList = adminService.viewCandidates(applicants);
			if (appList != null) {
				System.out
					.println("Applicant Id\tApplicant Name\tDate of Birth\tQualification\tMarks\tGoals\t\tEmail\t\tProg Id\t\tInterview date");
				System.out
					.println("-----------------------------------------------------------------------------------------------------------------------------------");
				for (Applicant applicant : appList) {
				System.out
					.println(String
						.format("%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\t%s",
								applicant.getAppId(),
								applicant.getAppName(),
								applicant.getAppDOB(),
								applicant.getQualification(),
								applicant.getMarks(),
								applicant.getGoals(),
								applicant.getEmailId(),
								applicant.getScheduleProgId(),
								applicant.getDateOfInterview()));
				}
				logger.info("Confirmed Applicant list Displyed");
			} else {
				System.out.println("No Records Found!");
				logger.info("No Confirmed Applicants found");
			}
			
			System.out.println("\nAPPLICANT'S REJECTED");
			applicants.setStatus("REJECTED");
			List<Applicant> rejectList = adminService.viewCandidates(applicants);
			if (rejectList != null) {
				System.out
					.println("Applicant Id\tApplicant Name\tDate Of Birth\tQualification\tMarks\tGoals\t\tEmail\t\tProg Id\t\tInterview date");
				System.out
					.println("-----------------------------------------------------------------------------------------------------------------------------------");
				for (Applicant applicant : rejectList) {
				System.out
					.println(String
						.format("%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\t%s",
								applicant.getAppId(),
								applicant.getAppName(),
								applicant.getAppDOB(),
								applicant.getQualification(),
								applicant.getMarks(),
								applicant.getGoals(),
								applicant.getEmailId(),
								applicant.getScheduleProgId(),
								applicant.getDateOfInterview()));
				}
				logger.info("Rejected Applicant list Displyed");
			} else {
				System.out.println("No Records Found!");
				logger.info("No Rejected Applicants found");
			}

		} catch (UASException exc) {
			System.err.println("Issue with displaying records");
			logger.info("Issue with displaying records");
		}
		
	}
	
	/*******************************************************************************************************
	 - Function Name	:	defaultInput()
	 - Input Parameters	:	void
	 - Return Type		:	void
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	04/10/2018
	 - Description		:	displays invalid message when input by user is invalid 
	 ********************************************************************************************************/
	public void defaultInput() {
		
		System.out.println("Invalid input");
		System.out.println("Please try again");
		logger.info("Invalid input");
		
	}
	

}
	
	
