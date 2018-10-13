package com.capg.uas.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.dao.ApplicantDaoImpl;
import com.capg.uas.dao.IApplicantDao;
import com.capg.uas.exception.UASException;


public class ApplicantServiceImpl implements IApplicantService {
	
	private IApplicantDao applicantDao;
	public ApplicantServiceImpl() {
		applicantDao = new ApplicantDaoImpl();
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	getAllScheduledPrograms()
	 - Input Parameters	:	NA
	 - Return Type		:	List<ProgramScheduled>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	29/10/2018
	 - Description		:	retrieving details of all scheduled programs from 
	 						database calls DAO method getAllScheduledPrograms()
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> getAllScheduledPrograms() throws UASException {
		return applicantDao.getAllScheduledPrograms();
	}


	
	
	/*******************************************************************************************************
	 - Function Name	:	addApplicant()
	 - Input Parameters	:	Applicant
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	29/10/2018
	 - Description		:	adding applicant details to database calls DAO method 
	 						addApplicant() after necessary validation
	 ********************************************************************************************************/
	@Override
	public int addApplicant(Applicant applicant) throws UASException {
		
		if (validateApplicant(applicant)) {
		return applicantDao.addApplicant(applicant);
		}
		
		else {
			return 0;
		}
		
	}


	
	/*******************************************************************************************************
	 - Function Name	:	checkApplicationStatus()
	 - Input Parameters	:	applicantId
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	29/10/2018
	 - Description		:	checking status of applicant from database calls DAO method 
	 						addApplicant() after necessary validation of applicantId
	 ********************************************************************************************************/
	@Override
	public String checkApplicationStatus(int applicantId) throws UASException {
		
		if (isValidApplicationId(applicantId)) {
		return applicantDao.checkApplicationStatus(applicantId);
		}
		else {
			return null;
		}
	}
		
	
	
	/*******************************************************************************************************
	 - Function Name	:	retrieveAllProgramIds()
	 - Input Parameters	:	NA
	 - Return Type		:	List<String>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	29/10/2018
	 - Description		:	retrieving all Scheduled Program Id's from database, calls DAO method 
	 						retrieveAllProgramIds()
	 ********************************************************************************************************/
	
	public List<String> retrieveAllProgramIds() throws UASException {
		return applicantDao.retrieveAllProgramIds();
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	retrieveAllApplicationIds()
	 - Input Parameters	:	NA
	 - Return Type		:	List<Integer>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	29/10/2018
	 - Description		:	retrieving all Application Id's from database, calls DAO method 
	 						retrieveAllApplicationIds()
	 ********************************************************************************************************/
	
	public List<Integer> retrieveAllApplicationIds() throws UASException {
		return applicantDao.retrieveAllApplicationIds();
	}
	
	
	
	/*******************************************************************************************************
	 - Function Name	: validateApplicant()
	 - Input Parameters	: Applicant applicant
	 - Return Type		: void
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 29/10/2018
	 - Description		: validates the Applicant object
	 ********************************************************************************************************/
	public boolean validateApplicant(Applicant applicant) throws UASException
	{
		
		boolean result ;
		List<String> validationErrors = new ArrayList<String>();

		//Validating applicant name
		if(!(isValidName(applicant.getAppName()))) {
			validationErrors.add("\n Applicant Name Should Be In Alphabets and minimum 3 characters long ! ");
		}
		//Validating DOB
		if(!(isValidDob(applicant.getAppDOB()))){
			validationErrors.add("\n Invalid Date of Birth");
		}
		
		//Validating Qualification
		if(!(isValidQualification(applicant.getQualification()))) {
			validationErrors.add("\n Invalid Qualification");
		}
		
		//Validating Marks
		if(!(isValidMarks(applicant.getMarks()))){
			validationErrors.add("\n Marks Should be a positive Number <= 100" );
		}
		
		//Validating Goals
		if(!(isValidGoals(applicant.getGoals()))) {
			validationErrors.add("\n Invalid Goals");
		}
				
		//Validating Email ID
		if(!(isValidEmailId(applicant.getEmailId()))){
			validationErrors.add("\n Invalid Email-Id");
		}
		
		//Validating Goals
		if(!(isValidScheduleProgId(applicant.getScheduleProgId()))) {
			validationErrors.add("\n Invalid Program ID");
		}
				
		if(!validationErrors.isEmpty()) {
			throw new UASException(validationErrors +"\n Please Try Again with Correct Details");
		}
		else {
			result = true;
		}
		return result;
	}
	
	public boolean isValidName(String applicantName) {
		Pattern namePattern=Pattern.compile("^[a-zA-Z\\s]*$");
		Matcher nameMatcher=namePattern.matcher(applicantName);
		return nameMatcher.matches();
	}
	
	public boolean isValidDob(Date dob) {
		boolean validity ;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dob;
		String strDate = dateFormat.format(date);
		Pattern namePattern=Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
		Matcher nameMatcher=namePattern.matcher(strDate);
		validity = nameMatcher.matches();
		
		java.util.Date curr = new java.util.Date();
		dateFormat.format(curr);
		
		if(date.after(curr)){
			validity=false;
	    }
		return validity;
	}

	
	public boolean isValidQualification(String qualification){
		return (qualification.length() > 1);
	}
	
	public boolean isValidGoals(String goals){
		return (goals.length() > 2);
	}
	
	public boolean isValidMarks(int marks){
		return (marks>=0 && marks<=100);
	}
	
	public boolean isValidEmailId(String email) {
		Pattern namePattern=Pattern.compile("[a-zA-Z][a-zA-Z0-9-.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher nameMatcher=namePattern.matcher(email);
		return nameMatcher.matches();
	}
	
	public boolean isValidScheduleProgId(String scheduleProgId) throws UASException {
		
		List<String> programIdList = retrieveAllProgramIds();
		boolean check = false;
		for (String programId : programIdList) {
			if ((programId.toUpperCase()).equals(scheduleProgId))
			{
				check = true;
			}
		}
		return check;
	}
	
	
	
	/*******************************************************************************************************
	 - Function Name	: isValidApplicationId()
	 - Input Parameters	: Integer
	 - Return Type		: Boolean
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 29/10/2018
	 - Description		: validates the Application Id
	 ********************************************************************************************************/
	public boolean isValidApplicationId(int applicationId)  throws UASException {
		boolean validity = false;
		List<Integer> applicationIdList = retrieveAllApplicationIds();
		for (int applicationIdLoop : applicationIdList) {
			if ( applicationIdLoop == applicationId)
			{
				validity = true;
			}
		}
		return validity;
	}
}