package com.capg.uas.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capg.uas.exception.UASException;

public class MainClientScreen {
	static Logger logger=Logger.getRootLogger();
	public static void main(String[] args) throws UASException  {
		Scanner scan ;
		PropertyConfigurator.configure("resources/log4j.properties");
		int choice;

		while (true) {
			System.out.println("\n**************************************************");
			System.out.println("Welcome to University Admission System Portal");
			System.out.println("\n**************************************************");
			System.out.println("\nEnter as:");
			System.out.println("\t1. Student/Applicant\n\t"
					+ "2. Member of admission committee\n\t"
					+ "3. Administrator");
			System.out.println("\n**************************************************");
			System.out.println("Enter 0 to Exit");
			System.out.println("Enter your choice:");
			scan=new Scanner(System.in);
			try{
			choice = scan.nextInt();
			switch (choice) {
			
			case 1:
				System.out.println("Accessing Portal as Student/Applicant...\n");
				ApplicantClientScreen applicantClient=new ApplicantClientScreen();
				applicantClient.ApplicantStart();
				break;
			
			case 2:
				MacClientScreen macClient=new MacClientScreen();
				System.out.println("Accessing Portal as "
						+ "Member of admission committee...");
				macClient.start();
				break;
			
			case 3:
				System.out.println("Accessing Portal as Administrator...\n");
				AdminClientScreen adminClient=new AdminClientScreen();	
					adminClient.start();
				break;
			
			case 0:
				System.out.println("Thank you! Exiting from Application...\n");
				scan.close();
				System.exit(0);
				break;
			
			default:
				System.err.println("You have selected an Invalid option. "
						+ "Please try again\n");
				break;
			}
		}catch(InputMismatchException e){
			System.err.println("please enter valid choice.");
			logger.error("please enter valid choice.");
		}
		}
	}
}