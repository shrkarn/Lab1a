package com.capg.uas.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.bean.Users;
import com.capg.uas.dao.IMacDao;
import com.capg.uas.dao.MacDaoImpl;
import com.capg.uas.exception.UASException;


public class MacServiceImpl implements IMacService {

	private IMacDao dao=new MacDaoImpl();
	
	public MacServiceImpl() {
		 dao=new MacDaoImpl();
		
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	validateMac()
	 - Input Parameters	:	String, String
	 - Return Type		:	boolean
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of users from database calls DAO method 
	 						getUserByName() to validate User Login and defining role 'MAC'
	 ********************************************************************************************************/
	@Override
	public boolean validateMac(String userName, String password) throws UASException {
		boolean validity;
		String role="MAC";
		Users user = dao.getUserByName(userName);
		if(user==null)
			throw new UASException("No Such Login Id");
		else if(!password.equals(user.getPassword())  )
			throw new UASException("Password Mismatch");
		else if(!role.matches(user.getRole()))
			throw new UASException("Role Mismatch");
		
		else
			validity=true;
		
		return validity;
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	listPrograms()
	 - Input Parameters	:	NA
	 - Return Type		:	List<ProgramScheduled>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all scheduled programs from 
	 						database call to DAO method listPrograms()
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> listPrograms() throws UASException {
		return dao.listPrograms();
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	findProgApplicant()
	 - Input Parameters	:	String
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all applicants for an offered program 
	 						from database call to DAO method findProgApplicant()
	 ********************************************************************************************************/
	@Override
	public List<Applicant> findProgApplicant(String pName) throws UASException {
		return dao.findProgApplicant(pName);
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	updateStatus()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	updating status of applicant for given application id
	 						database call to DAO method updateStatus()
	 ********************************************************************************************************/
	@Override
	public int updateStatus(int aId) throws UASException {
		return dao.updateStatus(aId);
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	assignInterview()
	 - Input Parameters	:	Integer, Date
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	assigning interview date to applicant by applicant id from 
	 						database call to DAO method assignInterview()
	 ********************************************************************************************************/
	@Override
	public int assignInterview(int aId,Date doiSql) throws UASException {
		
		return dao.assignInterview(aId,doiSql);
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	viewInterviewedCandidates()
	 - Input Parameters	:	NA
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all applicants to be interviewed from 
	 						database call to DAO method viewInterviewedCandidates()
	 ********************************************************************************************************/
	@Override
	public List<Applicant> viewInterviewedCandidates() throws UASException {
		return dao.viewInterviewedCandidates();
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	updateInterviewStatus()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	updating applicant status of applicant to confirmed from 
	 						database call to DAO method updateInterviewStatus()
	 ********************************************************************************************************/
	@Override
	public int updateInterviewStatus(int intrwId) throws UASException {
		return dao.updateInterviewStatus(intrwId);
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	addParticipant()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	adding participant details to Participant table for confirmed candidates
	 						from database call to DAO method addParticipant()
	 ********************************************************************************************************/
	@Override
	public int addParticipant(int intrwId) throws UASException {
		
		return dao.addParticipant(intrwId);
	}
	
	
	
	/*******************************************************************************************************
	 - Function Name	:	isValidDoi()
	 - Input Parameters	:	String
	 - Return Type		:	Boolean
	 - Throws			:  	NA
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	validating the interview date to be in correct format
	 ********************************************************************************************************/
	@Override
	public boolean isValidDoi(String doi) {
		
		Pattern namePattern = Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
		Matcher nameMatcher = namePattern.matcher(doi);
		return nameMatcher.matches();
	}


	/*******************************************************************************************************
	 - Function Name	:	findAppliedApplicant()
	 - Input Parameters	:	String
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	retrieving details of all applicants whose status="APPLIED" from 
	 						database call to DAO method findAppliedApplicant()
	 ********************************************************************************************************/
	@Override
	public List<Applicant> findAppliedApplicant(String pName) throws UASException {
		
		return dao.findAppliedApplicant(pName);
	}


	/*******************************************************************************************************
	 - Function Name	:	isFutureDate()
	 - Input Parameters	:	Date
	 - Return Type		:	Boolean
	 - Throws			:  	N.A.
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	check whether the input date is in future or not
	 ********************************************************************************************************/
	@Override
	public boolean isFutureDate(java.util.Date date) {
		boolean isFuture=true;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date curr = new java.util.Date();
		dateFormat.format(curr);
		if(date.before(curr)){
			isFuture=false;
        }
		return isFuture;
	}
	

}
