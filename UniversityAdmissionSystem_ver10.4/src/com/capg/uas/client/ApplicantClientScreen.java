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
import com.capg.uas.service.ApplicantServiceImpl;
import com.capg.uas.service.IApplicantService;

public class ApplicantClientScreen {	
	private IApplicantService applicantService;
	static Logger logger=Logger.getRootLogger();
	
	
	public void ApplicantStart() throws UASException {
		
		Scanner applicantScan;
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		myFormat.setLenient(false);
		int applicantChoice ;
		applicantService = new ApplicantServiceImpl();
		
		while (true) {
		applicantScan = new Scanner(System.in);
		System.out.println("\n\n**************************************************");
		System.out.println("\nOptions for Applicant:");
		System.out.println("\t1. View List of Scheduled Programs\n\t"
				+ "2. Apply for a Program\n\t"
				+ "3. Track Application Status");
		System.out.println("\n**************************************************");
		System.out.println("Enter 0 to Exit");
		System.out.println("Enter your choice:");
		applicantChoice = applicantScan.nextInt();
		
		switch (applicantChoice) {
		case 1:
			System.out.println("List of Scheduled Programs are as follows:\n");
			try {
				applicantService = new ApplicantServiceImpl();
				List<ProgramScheduled> programScheduledList = applicantService.getAllScheduledPrograms();

				if (!programScheduledList.isEmpty()) {
					System.out.println("Scheduled Program Id\tProgram Name\tLocation\tStart Date\tEnd Date\tSessions Per Week");
					System.out.println("-------------------------------------------------------------------------------------------------------");
					for (ProgramScheduled programScheduled : programScheduledList) {
						System.out.println(String.format("%s\t\t\t%s\t\t%s\t\t%s\t%s\t%s",
										programScheduled.getScheduleProgId(), programScheduled.getProgName(),
										programScheduled.getLocation(), programScheduled.getStart(),
										programScheduled.getEnd(), programScheduled.getSessionsPerWeek()));
					}
				} else {
					System.out.println("No Records Found!");
				}
			} catch (UASException e) {
				logger.info("Enter valid choidce. Records can't  be found");
			}
			break;
			
			
			
			
			
		case 2:
			try {
				
				List<ProgramScheduled> programScheduledList;
				applicantService = new ApplicantServiceImpl();
				programScheduledList = applicantService.getAllScheduledPrograms();

				if (!programScheduledList.isEmpty()) {
					System.out.println("Scheduled Program Id\tProgram Name\tLocation\tStart Date\tEnd Date\tSessions Per Week");
					System.out.println("-------------------------------------------------------------------------------------------------------");
					for (ProgramScheduled programScheduled : programScheduledList) {
						System.out.println(String.format("%s\t\t\t%s\t\t%s\t\t%s\t%s\t%s",
										programScheduled.getScheduleProgId(), programScheduled.getProgName(),
										programScheduled.getLocation(), programScheduled.getStart(),
										programScheduled.getEnd(), programScheduled.getSessionsPerWeek()));
					}
				} else {
					System.out.println("No Records Found!");
				}
			} catch (UASException e) {
				logger.error("Error occured in finding records");
				System.err.println(e.getMessage());
			}

			Applicant applicant = new Applicant();
			
			System.out.println("\n\n\nEnter the Scheduled Program Id of the Program to be applied");
			String scheduleProgId = applicantScan.next();
			
			System.out.println("\nApplying for Program ID: "+scheduleProgId.toUpperCase());
			applicant.setScheduleProgId(scheduleProgId.toUpperCase());
			
			System.out.println("Enter Full Name:");
			applicantScan.nextLine();
			applicant.setAppName(applicantScan.nextLine());
			
			System.out.println("Enter Date of Birth in DD/MM/YYYY format:");
			String dob = applicantScan.nextLine();
			
			if(null != dob && dob.trim().length() > 0){
			    try {
					Date dateOfBirth = myFormat.parse(dob);
					java.sql.Date dobsql = new java.sql.Date(dateOfBirth.getTime());
					applicant.setAppDOB(dobsql);
				} catch (ParseException e) {
					System.err.println("Date is of Invalid format. Please try again");
					break;
				}
			}
			
			System.out.println("Enter Highest Qualification:");
			applicant.setQualification(applicantScan.next());
			
			System.out.println("Enter Marks Obtained");
			try {
				applicant.setMarks(applicantScan.nextInt());
				
			} catch (InputMismatchException except) {
				System.err.println("Invalid Marks. Please try again");
				logger.error("Invalid marks.Please try again");
				break;
			} catch (Exception e) {
				System.err.println("Invalid Marks. Please try again");
				break;
			}
			
			System.out.println("Enter Goals:");
			applicantScan.nextLine();
			applicant.setGoals(applicantScan.nextLine());
			
			System.out.println("Enter Email ID:");
			applicant.setEmailId(applicantScan.next());
			
			try {
				int appId = applicantService.addApplicant(applicant);
				System.out.println("Thank you for Applying. Your Application Id is "+appId);
			} catch (UASException uasException) {
				System.err.println("The following errors occurred: "
						+ uasException.getMessage());
				logger.error("Error in adding the applicant");
			} finally {
				applicantService = null;
				
			}
			break;	
			
			

		case 3:
			applicantService = new ApplicantServiceImpl();
			System.out.println("Enter your application Id to check status:");
			int applicantId = applicantScan.nextInt();

			String status = applicantService.checkApplicationStatus(applicantId);
			if (status != null) {
			System.out.println("Current Status of Application:\n\tApplicant Id: "+applicantId +"\n\tStatus: " +status);
			}
			else {
				System.err.println("Invalid Application ID. Please try again.");
			}
			break;
			
		case 0:
			System.out.println("Thank you! Exiting from Application...\n");
			applicantScan.close();
			System.exit(0);
			break;
			
		default:
			System.err.println("You have selected an Invalid option. "
					+ "Please try again\n");
			break;
		}
	}
	}
}