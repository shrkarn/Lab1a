package com.capg.uas.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capg.uas.bean.Applicant;
import com.capg.uas.exception.UASException;
import com.capg.uas.service.ApplicantServiceImpl;
import com.capg.uas.service.IApplicantService;

public class ApplicationTest {

	@Before
	public void setUp() throws UASException {

		//System.out.println("Test Case - Begin");
	}

	@After
	public void tearDown() throws UASException {
		//System.out.println("Test Case - End");
	}

	@Test
	public void test() throws UASException {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		myFormat.setLenient(false);

		Applicant applicant = new Applicant();

		applicant.setAppName("Test User");
		applicant.setDateOfInterview(null);
		applicant.setEmailId("testuser@capg.com");
		applicant.setGoals("Good Professional");
		applicant.setMarks(95);
		applicant.setQualification("B.Tech");
		applicant.setScheduleProgId("MATHS");
		applicant.setStatus(null);

		String dob = "15/07/1945";
		
		try {
			Date dateOfBirth;
			dateOfBirth = myFormat.parse(dob);
			java.sql.Date dobsql = new java.sql.Date(dateOfBirth.getTime());
			applicant.setAppDOB(dobsql);
		} catch (ParseException e) {
			System.err.println("Date is of Invalid format. Please try again");
		}
		
		try {
			IApplicantService applicantService = new ApplicantServiceImpl();
			int appId = applicantService.addApplicant(applicant);
			//System.out.println("Thank you for Applying. Your Application Id is "+appId);
			assertNotNull(appId);
		} catch (UASException uasException) {
			//logger.error("exception occurred", uasException);
			System.err.println("The following errors occurred: "
					+ uasException.getMessage());
		}
	}

}
