package com.capg.uas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramOffered;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.bean.Users;
import com.capg.uas.exception.UASException;
import com.capg.uas.util.ConnectionProvider;

public class AdminDaoImpl implements IAdminDao {

	public static Logger log = Logger.getLogger("AdminDAO");
	

	/*******************************************************************************************************
	 - Function Name	:	getUserByName()
	 - Input Parameters	:	String
	 - Return Type		:	Users
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of users by String userName
	 ********************************************************************************************************/
	@Override
	public Users getUserByName(String userName) throws UASException {
		Users user = null;
		try (Connection connection = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(IQueryMapper.GET_USER)) {

			statement.setString(1, userName);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				user = new Users();
				user.setLoginId(result.getString(1));
				user.setPassword(result.getString(2));
				user.setRole(result.getString(3));
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Fetch Records");
		}
		return user;
	}

	/*******************************************************************************************************
	 - Function Name	:	addProgramOffered()
	 - Input Parameters	:	ProgramOffered
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	adding program offered details in Database to Table Programs_Offered
	 ********************************************************************************************************/
	@Override
	public String addProgramOffered(ProgramOffered progOffered)
			throws UASException {
		String programName = null;
		
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement addStatement = null;
		
		
		if (progOffered != null) {
			
			try {
				addStatement = connection.prepareStatement(IQueryMapper.ADD_PROGRAM_OFFERED_QUERY);
				addStatement.setString(1, progOffered.getProgName());
				addStatement.setString(2, progOffered.getDesc());
				addStatement.setString(3, progOffered.getAppEligibility());
				addStatement.setInt(4, progOffered.getDuration());
				addStatement.setString(5, progOffered.getDegreeOffered());
				int count = addStatement.executeUpdate();
				
				if (count > 0){
						log.info("Program Offered details added successfully");
						programName=progOffered.getProgName();
					
				}
				else {
					log.error("Program Offered insertion failed");
					throw new UASException("Inserting Program Offered details failed ");
				}
				
			} catch (SQLException sqlException) {
				
				log.error(sqlException+"Technical error");
				throw new UASException("Tehnical problem occured refer log");
			}
			finally
			{
				try 
				{
					
					addStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					log.error(sqlException.getMessage()+"Database connection not closed");
					

				}
			}
		}
		return programName;
	}

	/*******************************************************************************************************
	 - Function Name	:	updateProgramOffered()
	 - Input Parameters	:	ProgramOffered
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	updating description and applicant eligibility for given 
	 						program in Database to Table Programs_Offered
	 ********************************************************************************************************/
	@Override
	public String updateProgramOffered(ProgramOffered progOffered)
			throws UASException {
		String programName = null;
		
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement updateStatement = null;
		
			try {
				
				updateStatement=connection.prepareStatement(IQueryMapper.UPDATE_PROGRAM_OFFERED_QUERY);
				updateStatement.setString(1, progOffered.getDesc());
				updateStatement.setString(2, progOffered.getAppEligibility());
				updateStatement.setString(3, progOffered.getProgName());
				int count = updateStatement.executeUpdate();

				if (count > 0){
					log.info("Program Offered details updated successfully:");
						programName=progOffered.getProgName();
					
				}
				else {
					log.error("Program details not updated ");
					throw new UASException("Updating Program Offered details failed ");
				}
				
			} catch (SQLException sqlException) {
				
				log.error(sqlException.getMessage()+"Technical problem");
				throw new UASException("Tehnical problem occured refer log");
			}
			finally
			{
				try 
				{
					
					updateStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					log.error(sqlException.getMessage()+"Database connection not closed");
					

				}
			}
			
			return programName;
		}

	/*******************************************************************************************************
	 - Function Name	:	deleteProgramOffered()
	 - Input Parameters	:	String
	 - Return Type		:	Boolean
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	deleting program offered by program name from Database from 
	 						Table Programs_Offered
	 ********************************************************************************************************/
	@Override
	public boolean deleteProgramOffered(String progName) throws UASException {
		
		boolean result = false;
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement deleteStatement = null;
		
			try {
				
				deleteStatement=connection.prepareStatement(IQueryMapper.DELETE_PROGRAM_OFFERED_QUERY);
				
				deleteStatement.setString(1, progName);
				int count = deleteStatement.executeUpdate();

				if (count > 0){
					log.info("Program Offered successfully deleted");
						result = true;
					
				}
				else {
					log.error("Deletion failed ");
					throw new UASException("Deleting Program Offered details failed ");
				}
				
			} catch (SQLException sqlException) {
				
				log.fatal(sqlException.getMessage()+"Technical problem");
				throw new UASException("Tehnical problem occured refer log");
			}
			finally
			{
				try 
				{
					
					deleteStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					log.error(sqlException.getMessage()+"Database connection not closed");
					

				}
			}
			
			return result;
	}

	/*******************************************************************************************************
	 - Function Name	:	getAllOfferedPrograms()
	 - Input Parameters	:	void
	 - Return Type		:	List<ProgramOffered>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of all programs offered by university
	  						from database table Programs_Offered
	 ********************************************************************************************************/
	@Override
	public List<ProgramOffered> getAllOfferedPrograms() throws UASException {
		
		List<ProgramOffered> programOfferedList = new ArrayList<>();
		ResultSet resultSet = null;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement listStatement = null;
		
		try {
			
			listStatement = connection.prepareStatement(IQueryMapper.LIST_PROGRAMS_OFFERED); 
			
			resultSet = listStatement.executeQuery();
			
			while(resultSet.next()){
				ProgramOffered programOffered = new ProgramOffered();
				programOffered.setProgName(resultSet.getString("ProgramName"));
				programOffered.setDesc(resultSet.getString("description"));
				programOffered.setAppEligibility(resultSet.getString("applicant_eligibility"));
				programOffered.setDuration(resultSet.getInt("duration"));
				programOffered.setDegreeOffered(resultSet.getString("degree_certificate_offered"));
				
				programOfferedList.add(programOffered);
			}

		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Error in fetching data in DAO");
		} finally
		{
			try 
			{
				resultSet.close();
				listStatement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				log.error(sqlException.getMessage()+"Database connection not closed");
				
			}
		}
		return programOfferedList;
	}

	/*******************************************************************************************************
	 - Function Name	:	addProgramScheduled()
	 - Input Parameters	:	ProgramScheduled
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	adding program offered details to Database in 
	 						table Programs_Scheduled 
	 ********************************************************************************************************/
	@Override
	public String addProgramScheduled(ProgramScheduled progScheduled)
			throws UASException {
		String programId = null;
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement addStatement = null;
		
		
		if (progScheduled != null) {
			
			try {
				addStatement = connection.prepareStatement(IQueryMapper.ADD_PROGRAM_SCHEDULED_QUERY);
				addStatement.setString(1, progScheduled.getScheduleProgId());
				addStatement.setString(2, progScheduled.getProgName());
				addStatement.setString(3, progScheduled.getLocation());
				addStatement.setDate(4, progScheduled.getStart());
				addStatement.setDate(5, progScheduled.getEnd());
				addStatement.setInt(6, progScheduled.getSessionsPerWeek());
				
				int count = addStatement.executeUpdate();
				
				if (count > 0){
						log.info("Program Schedule details added successfully");
						programId=progScheduled.getScheduleProgId();
					
				}
				else {
					log.error("Program Schedule details insertion failed ");
					throw new UASException("Inserting Program Scheduled failed ");
				}
				
			} catch (SQLException sqlException) {
				log.error(sqlException.getMessage()+"Technical problem");
				throw new UASException("Tehnical problem occured refer log");
			}
			finally
			{
				try 
				{
					
					addStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					log.error(sqlException.getMessage()+"Database connection not closed");
					

				}
			}
		}
		return programId;
	}

	/*******************************************************************************************************
	 - Function Name	:	deleteProgramScheduled()
	 - Input Parameters	:	String
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	deleting program scheduled by program id from Database
	 						from table Programs_Scheduled 
	 ********************************************************************************************************/
	@Override
	public int deleteProgramScheduled(String progId) throws UASException {
		
		int applicants = 0;
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement deleteStatement = null;
		PreparedStatement countStatement = null;
			try {
				
				countStatement=connection.prepareStatement(IQueryMapper.COUNT_APPLICANT_QUERY);
				countStatement.setString(1, progId);
				applicants = countStatement.executeUpdate();
				if(applicants == 0){
				deleteStatement=connection.prepareStatement(IQueryMapper.DELETE_PROGRAM_SCHEDULED_QUERY);
				deleteStatement.setString(1, progId);
				int count = deleteStatement.executeUpdate();

				if (count > 0){
						log.info("Program Schedule deleted successfully:");
				}
				else {
					log.error("Deletion failed ");
					throw new UASException("Deleting Program Scheduled failed ");
				}
				}
			} catch (SQLException sqlException) {
				log.error(sqlException.getMessage()+"Technical problem");
				throw new UASException("Tehnical problem occured refer log");
			}
			finally
			{
				try 
				{
					countStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					log.error(sqlException.getMessage()+"Database connection not closed");
					

				}
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
	 - Description		:	retrieving details of all scheduled programs commencing
	  						between start date and end date from Database table Programs_SCheduled
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> getDatedProgramsSchedule(Date fromDateSQL,
			Date toDateSQL) throws UASException {
		List<ProgramScheduled> programScheduleList = new ArrayList<>();
		
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement listStatement = null;
		ResultSet resultSet = null;
		try {
			
			listStatement = connection.prepareStatement(IQueryMapper.LIST_PROGRAMS_SCHEDULED_DATES); 
			listStatement.setDate(1, fromDateSQL);
			listStatement.setDate(2, toDateSQL);
			resultSet = listStatement.executeQuery();
			
			
			while(resultSet.next()){
				ProgramScheduled programSchedule = new ProgramScheduled();
				programSchedule.setScheduleProgId(resultSet.getString("Scheduled_program_id"));
				programSchedule.setProgName(resultSet.getString("ProgramName"));
				programSchedule.setLocation(resultSet.getString("Location"));
				programSchedule.setStart(resultSet.getDate("start_date"));
				programSchedule.setEnd(resultSet.getDate("end_date"));
				programSchedule.setSessionsPerWeek(resultSet.getInt("session_per_week"));				
				programScheduleList.add(programSchedule);
			}
			if(programScheduleList.isEmpty()){
				programScheduleList=null;
				log.info("Schedule list not fetched");
			}

		} catch (SQLException except) {
			log.error(except);
			throw new UASException("Error in fetching data in DAO");
		} finally
		{
			try 
			{

				listStatement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				log.error(sqlException.getMessage()+"Database connection not closed");
				
			}
		}
		return programScheduleList;
	}

	/*******************************************************************************************************
	 - Function Name	:	viewCandidates()
	 - Input Parameters	:	Applicant applicants
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	03/10/2018
	 - Description		:	retrieving details of all applicants for given scheduled program id 
	 						and status from Database table Application
	 ********************************************************************************************************/
	@Override
	public List<Applicant> viewCandidates(Applicant applicants)
			throws UASException {
		List<Applicant> appList = null;
		try (
				Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement statement = connection
						.prepareStatement(IQueryMapper.FIND_APPLICANT_PROG_ID_STATUS);) {
			
			statement.setString(1, applicants.getScheduleProgId());
			statement.setString(2, applicants.getStatus());
			ResultSet result = statement.executeQuery();

			appList = new ArrayList<Applicant>();
			while (result.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppId(result.getInt("application_id"));
				applicant.setAppName(result.getString("full_name"));
				applicant.setAppDOB(result.getDate("date_of_birth"));
				applicant.setQualification(result
						.getString("highest_qualification"));
				applicant.setMarks(result.getInt("marks_obtained"));
				applicant.setGoals(result.getString("goals"));
				applicant.setEmailId(result.getString("email_id"));
				applicant.setScheduleProgId(result.getString("Scheduled_program_id"));
				applicant.setStatus(result.getString("status"));
				applicant.setDateOfInterview(result.getDate("Date_Of_Interview"));

				appList.add(applicant);
			}

			if (appList.isEmpty())
				appList = null;
			
		} catch (SQLException exp) {
			log.error(exp);
			throw new UASException("Unable To Fetch Applicants");
		}
		return appList;
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
	public boolean programNameCheck(String progName) throws UASException  {
		
		boolean check = false;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement checkStatement = null;
		
		try{
			checkStatement = connection.prepareStatement(IQueryMapper.CHECK_PROGRAM_NAME);
			ResultSet result = checkStatement.executeQuery();
			while(result.next()){
				if(result.getString("ProgramName").equals(progName)){
					check= true;
					
				}
			}
		} catch (SQLException sqlException) {
				log.error(sqlException+"Technical error");
				throw new UASException("Tehnical problem occured refer log");
		}
		finally
		{
			try 
			{
					
				checkStatement.close();
				
				connection.close();
			}
			catch (SQLException sqlException) 
			{
					
				log.error(sqlException.getMessage()+"Database connection not closed");
			}
		}		
		return check;
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
	public boolean programIdCheck(String progId) throws UASException  {
		
		boolean check = false;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement checkStatement = null;
		
		try{
			checkStatement = connection.prepareStatement(IQueryMapper.CHECK_PROGRAM_ID);
			ResultSet result = checkStatement.executeQuery();
			while(result.next()){
				if(result.getString("Scheduled_program_id").equals(progId)){
					check= true;
					
				}
			}
		} catch (SQLException sqlException) {
				log.error(sqlException+"Technical error");
				throw new UASException("Tehnical problem occured refer log");
		}
		finally
		{
			try 
			{
					
				checkStatement.close();
				
				connection.close();
			}
			catch (SQLException sqlException) 
			{
					
				log.error(sqlException.getMessage()+"Database connection not closed");
			}
		}		
		return check;
	}

}
