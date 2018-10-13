package com.capg.uas.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramOffered;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.bean.Users;
import com.capg.uas.dao.AdminDaoImpl;
import com.capg.uas.dao.IAdminDao;
import com.capg.uas.exception.UASException;

public class AdminServiceImpl implements IAdminService {
	
	private IAdminDao adminDao;
	static Logger log = Logger.getLogger("AdminService");
	
	public AdminServiceImpl() {
		adminDao=new AdminDaoImpl();
	}

	
	/*******************************************************************************************************
	 - Function Name	:	validateAdmin()
	 - Input Parameters	:	String, String
	 - Return Type		:	Boolean
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of users from database calls DAO method 
	 						getUserByName() to validate User Login and defining role 'Admin'
	 ********************************************************************************************************/
	@Override
	public boolean validateAdmin(String userName, String password) throws UASException {
		boolean validity =false;
		String role="Admin";
		Users user = adminDao.getUserByName(userName);
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
	 - Function Name	:	addProgramOffered()
	 - Input Parameters	:	ProgramOffered
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	adding program offered details to Database from database call
	  						to DAO method addProgramOffered() after necessary validations
	 ********************************************************************************************************/
	@Override
	public String addProgramOffered(ProgramOffered progOffered)
			throws UASException {
		String programOffer = null;
		if(progOffered!=null && validateProgram(progOffered)){
			programOffer=adminDao.addProgramOffered(progOffered);
		}
		return programOffer;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	updateProgramOffered()
	 - Input Parameters	:	ProgramOffered
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	updating description and applicant eligibility for given program to Database 
	 						from database call to DAO method updateProgramOffered() after 
	 						necessary validations
	 ********************************************************************************************************/
	@Override
	public String updateProgramOffered(ProgramOffered progOffered)
			throws UASException {
		String programOffer = null;
		if(progOffered!=null && isValidDesc(progOffered.getDesc()) &&
				isValidEligibility(progOffered.getAppEligibility())){
			programOffer=adminDao.updateProgramOffered(progOffered);
		}else{
			System.out.println("Invalid Description and Eligibility");
		}
		return programOffer;
		
	}

	
	/*******************************************************************************************************
	 - Function Name	:	deleteProgramOffered()
	 - Input Parameters	:	String
	 - Return Type		:	Boolean
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	deleting program offered by program name from Database by database call
	  						to DAO method deleteProgramOffered() after necessary validations
	 ********************************************************************************************************/
	@Override
	public boolean deleteProgramOffered(String progName) throws UASException {
		boolean success = false;
		if(progName!=null && isValidName(progName)){
			success=adminDao.deleteProgramOffered(progName);
		}else{
			System.out.println("Invalid Program Name");
		}
		return success;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	getAllOfferedPrograms()
	 - Input Parameters	:	void
	 - Return Type		:	List<ProgramOffered>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of all programs offered by university
	  						from database calls DAO method getAllOfferedPrograms()
	 ********************************************************************************************************/
	@Override
	public List<ProgramOffered> getAllOfferedPrograms() throws UASException {

		return adminDao.getAllOfferedPrograms();
	}

	
	
	
	/*******************************************************************************************************
	 - Function Name	:	addProgramScheduled()
	 - Input Parameters	:	ProgramScheduled
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	adding program offered details to Database from database call
	  						to DAO method addProgramScheduled() after necessary validations
	 ********************************************************************************************************/
	@Override
	public String addProgramScheduled(ProgramScheduled progScheduled)
			throws UASException {
		String programSchedule = null;
		if(progScheduled!=null && validateProgramSchedule(progScheduled)){
			programSchedule=adminDao.addProgramScheduled(progScheduled);
		}
		return programSchedule;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	deleteProgramScheduled()
	 - Input Parameters	:	String
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	deleting program scheduled by program id from Database by database call
	  						to DAO method deleteProgramScheduled() after necessary validations
	 ********************************************************************************************************/
	@Override
	public int deleteProgramScheduled(String progId) throws UASException {
		int applicants = 0;
		if(progId!=null && isValidName(progId)){
			applicants=adminDao.deleteProgramScheduled(progId);
		}
		return applicants;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	getDatedProgramsSchedule()
	 - Input Parameters	:	Date, Date
	 - Return Type		:	List<ProgramScheduled>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of all scheduled programs commencing between start date 
	 						and end date from database calls DAO method getDatedProgramsSchedule()
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> getDatedProgramsSchedule(Date fromDateSQL,
			Date toDateSQL) throws UASException {
		List<ProgramScheduled> progList = null;
		if(fromDateSQL!=null && toDateSQL!=null && isValidDate(fromDateSQL) && isValidDate(toDateSQL)){
			progList = adminDao.getDatedProgramsSchedule(fromDateSQL, toDateSQL);
		}
		return progList;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	viewCandidates()
	 - Input Parameters	:	Applicant applicants
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of all applicants for given scheduled program id 
	 						and status from database calls DAO method viewCandidates()
	 ********************************************************************************************************/
	@Override
	public List<Applicant> viewCandidates(Applicant applicants)
			throws UASException {
		List<Applicant> appList = null;
		if(  isValidName(applicants.getScheduleProgId())){
			appList = adminDao.viewCandidates(applicants);
		}
		else{
			System.out.println("Invalid Scheduled Program Id");
		}
		return appList;
		
	}

	/*******************************************************************************************************
	 - Function Name	: validateProgram()
	 - Input Parameters	: ProgramOffered program
	 - Return Type		: Boolean
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 03/10/2018
	 - Description		: validates the ProgramOffered object
	 ********************************************************************************************************/
	public boolean validateProgram(ProgramOffered program) throws UASException
	{
		
		boolean result = false;
		List<String> validationErrors = new ArrayList<String>();

		//Validating description
		if(!(isValidDesc(program.getDesc()))) {
			validationErrors.add("\nDescription can have 20 characters atmost");
		}
		//Validating Program Name
		if(!(isValidName(program.getProgName()))){
			validationErrors.add("\nProgram Name can have 5 characters atmost");
		}
		
		//Validating Applicant Eligibility
		if(!(isValidEligibility(program.getAppEligibility()))) {
			validationErrors.add("\nEligibility can atmost have 40 characters ");
		}
		
		//Validating Duration
		if(!(isValidDuration(program.getDuration()))){
			validationErrors.add("\nDuration must be atleast 1 month" );
		}
		
		//Validating Degree Offered
		if(!(isValidDegree(program.getDegreeOffered()))) {
			validationErrors.add("\nDegree Offered can have 10 characters atmost");
		}
				
		if(!validationErrors.isEmpty()) {
			throw new UASException(validationErrors +"\nPlease Try Again with Correct Details");
		}
		else {
			result = true;
		}
		return result;
	}
	
	public boolean isValidDesc(String desc){
		Pattern descPattern = Pattern.compile("[A-Za-z0-9\\s]{0,20}");
		Matcher descMatcher = descPattern.matcher(desc);
		
		boolean isValid=descMatcher.matches();
		
		return isValid;
	}
	
	public boolean isValidEligibility(String eligibility){
		Pattern eligibilityPattern = Pattern.compile("[A-Za-z0-9\\s]{0,40}");
		Matcher eligibilityMatcher = eligibilityPattern.matcher(eligibility);
		
		boolean isValid=eligibilityMatcher.matches();
		
		return isValid;
	}

	public boolean isValidDegree(String degree) throws UASException {
		Pattern degreePattern = Pattern.compile("[A-Za-z0-9\\s]{0,10}");
		Matcher degreeMatcher = degreePattern.matcher(degree);
		
		boolean isValid=degreeMatcher.matches();
		
		return isValid;
	}

	public boolean isValidDuration(int duration) throws UASException  {
		String period = Integer.toString(duration);
		Pattern durationPattern = Pattern.compile("[1-9]{1}+[0-9]{0,1}");
		Matcher durationMatcher = durationPattern.matcher(period);
		
		boolean isValid=durationMatcher.matches();
		
		return isValid;
	}

	public boolean isValidName(String progName) throws UASException {
		Pattern namePattern = Pattern.compile("[A-Za-z0-9]{0,5}");
		Matcher nameMatcher = namePattern.matcher(progName);
		
		boolean isValid=nameMatcher.matches();
		
		return isValid;
		
	}
	
	
	/*******************************************************************************************************
	 - Function Name	: validateProgramSchedule()
	 - Input Parameters	: ProgramScheduled program
	 - Return Type		: Boolean
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 03/10/2018
	 - Description		: validates the ProgramScheduled object
	 ********************************************************************************************************/
	public boolean validateProgramSchedule(ProgramScheduled program) throws UASException
	{
		
		boolean result = false;
		List<String> validationErrors = new ArrayList<String>();

		//Validating Scheduled Program Id
		if(!(isValidName(program.getScheduleProgId()))) {
			validationErrors.add("\n Schedule Program ID can have 5 characters atmost ");
		}
		//Validating Location
		if(!(isValidDegree(program.getLocation()))){
			validationErrors.add("\n Location can have 10 characters atmost");
		}
		
		//Validating Sessions Per Week
		if(!(isValidDuration(program.getSessionsPerWeek()))) {
			validationErrors.add("\nThere must be atleast 1 session per week");
		}
		
		//Validating Program Name
		if(!(isValidName(program.getProgName()))){
			validationErrors.add("\n Program Name can have 5 characters at max" );
		}
		
		//Validating Start Date
		if(!(isValidDate(program.getStart()))) {
			validationErrors.add("\n Date Should be in DD/MM/YYYY format");
		}
				
		//Validating End Date
		if(!(isValidDate(program.getEnd()))){
			validationErrors.add("\n Date Should be in DD/MM/YYYY format");
		}
		
		
				
		if(!validationErrors.isEmpty()) {
			throw new UASException(validationErrors +"\n Please Try Again with Correct Details");
		}
		else {
			result = true;
		}
		return result;
	}

	public boolean isValidDate(Date dates) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dates;
		String strDate = dateFormat.format(date);
		Pattern namePattern=Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$");
		Matcher nameMatcher=namePattern.matcher(strDate);
		return nameMatcher.matches();
	}

	
	/*******************************************************************************************************
	 - Function Name	: programNameCheck()
	 - Input Parameters	: String progName
	 - Return Type		: Boolean
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 03/10/2018
	 - Description		: validates the ProgramScheduled object
	 ********************************************************************************************************/
	@Override
	public boolean programNameCheck(String progName) throws UASException {
		boolean result =adminDao.programNameCheck(progName.toUpperCase());
		return result;
	}

	/*******************************************************************************************************
	 - Function Name	: programIdCheck()
	 - Input Parameters	: String progId
	 - Return Type		: Boolean
	 - Throws		    : UASException
	 - Author	      	: GROUP2
	 - Creation Date	: 03/10/2018
	 - Description		: validates the ProgramScheduled object
	 ********************************************************************************************************/
	@Override
	public boolean programIdCheck(String progId) throws UASException {
		boolean result =adminDao.programNameCheck(progId.toUpperCase());
		return result;
	}

	
}
