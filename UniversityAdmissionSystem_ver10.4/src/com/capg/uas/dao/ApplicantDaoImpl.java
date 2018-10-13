package com.capg.uas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;
import com.capg.uas.util.ConnectionProvider;


public class ApplicantDaoImpl implements IApplicantDao {
	
	private Logger logger = Logger.getLogger("ApplicantDAO");
		public ApplicantDaoImpl() {
	}
	
	
	/*******************************************************************************************************
	 - Function Name	:	getAllScheduledPrograms()
	 - Input Parameters	:	NA
	 - Return Type		:	List<ProgramScheduled>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all scheduled programs from database
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> getAllScheduledPrograms() throws UASException {
		List<ProgramScheduled> programScheduledList = new ArrayList<>();
		ResultSet resultSet = null;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(IQueryMapper.LIST_SCHEDULED_PROGRAMS); 
			
		resultSet = statement.executeQuery();
			
			
			while(resultSet.next()){
				ProgramScheduled programScheduled = new ProgramScheduled();
				programScheduled.setScheduleProgId(resultSet.getString("Scheduled_program_id"));
				programScheduled.setProgName(resultSet.getString("ProgramName"));
				programScheduled.setLocation(resultSet.getString("Location"));
				programScheduled.setStart(resultSet.getDate("start_date"));
				programScheduled.setEnd(resultSet.getDate("end_date"));
				programScheduled.setSessionsPerWeek(resultSet.getInt("session_per_week"));
				
				programScheduledList.add(programScheduled);
			}
			
			if(programScheduledList.isEmpty()){
				programScheduledList=null;
				logger.info("Schedule list not fetched");
			}
			else {
				logger.info("Scheduled Program list fetched Successfully");
			}
		} catch (SQLException exception) {
			logger.error(exception);
			throw new UASException("Error in fetching data in DAO");
		} finally
		{
			try 
			{
				resultSet.close();
				statement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				logger.error(sqlException.getMessage());
				
			}
		}
		return programScheduledList;
	}

	
	/*******************************************************************************************************
	 - Function Name	:	addApplicant()
	 - Input Parameters	:	Applicant
	 - Return Type		:	int
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	adding applicant details to database
	 ********************************************************************************************************/
	@Override
	public int addApplicant(Applicant applicant) throws UASException {
		int application_id = 0;
		
		ResultSet resultSet = null;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement addStatement = null;
		PreparedStatement appIdStatement = null;
		
		if (applicant != null) {
			
			try {
				addStatement = connection.prepareStatement(IQueryMapper.ADD_APPLICANT_QUERY);
				addStatement.setString(1, applicant.getAppName());
				addStatement.setDate(2, applicant.getAppDOB());
				addStatement.setString(3, applicant.getQualification());
				addStatement.setInt(4, applicant.getMarks());
				addStatement.setString(5, applicant.getGoals());
				addStatement.setString(6, applicant.getEmailId());
				addStatement.setString(7, applicant.getScheduleProgId());
				int count = addStatement.executeUpdate();
				if (count > 0){
					logger.info("Applicant details added successfully:");
					appIdStatement = connection.prepareStatement(IQueryMapper.APPLICATION_ID_QUERY);
					resultSet=appIdStatement.executeQuery();
					if(resultSet.next())
					{
						application_id=resultSet.getInt(1);
					}
				}
				else {
					logger.error("Inserting applicant details failed ");
					throw new UASException("Inserting applicant details failed ");
				}
				
			} catch (SQLException sqlException) {
				
				logger.error(sqlException.getMessage());
				throw new UASException("Technical problem occured refer log");
			}
			finally
			{
				try 
				{
					resultSet.close();
					addStatement.close();
					appIdStatement.close();
					connection.close();
				}
				catch (SQLException sqlException) 
				{
					
					logger.error(sqlException.getMessage());
					

				}
			}
		}
		return application_id;
	}



	/*******************************************************************************************************
	 - Function Name	:	checkApplicationStatus()
	 - Input Parameters	:	applicantId
	 - Return Type		:	String
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	checking status of applicant from database
	 ********************************************************************************************************/
	@Override
	public String checkApplicationStatus(int applicantId) throws UASException {
		String status = null;
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement checkStatement = null;
		ResultSet resultset = null;
		
		try {
			checkStatement = connection.prepareStatement(IQueryMapper.CHECK_APPLICATION_STATUS_QUERY);
			checkStatement.setInt(1,applicantId);
			resultset = checkStatement.executeQuery();
			if(resultset.next())
			{
				status = resultset.getString(1);			
			}
			if( status != null)
			{
				logger.info("Applicant Status Record Found Successfully");
			}
			else
			{
				logger.info("Applicant Status Record Not Found");
			}
			
		} catch (SQLException sqlException) {
			
			logger.error(sqlException.getMessage());
			throw new UASException("Technical problem occured refer log");
		} finally
		{
			try 
			{
				resultset.close();
				checkStatement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				logger.error(sqlException.getMessage());
				
			}
		}
		
		return status;
	}



	/*******************************************************************************************************
	 - Function Name	:	retrieveAllProgramIds()
	 - Input Parameters	:	NA
	 - Return Type		:	List<String>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving all Scheduled Program Ids from database, calls dao method 
	 						retrieveAllProgramIds()
	 ********************************************************************************************************/
	@Override
	public List<String> retrieveAllProgramIds() throws UASException {
		List<String> programIdList = new ArrayList<String>();
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement checkStatement = null;
		ResultSet resultset = null;
		
		try {
			checkStatement = connection.prepareStatement(IQueryMapper.GET_ALL_PROGRAM_ID_QUERY);
			resultset = checkStatement.executeQuery();
			while(resultset.next())
			{
				programIdList.add(resultset.getString("Scheduled_program_id"));		
			}
			if( programIdList.isEmpty())
			{
				logger.info("Scheduled Program id's not retrieved.");
			}
			else
			{
				logger.info("Scheduled Program id's retrieved Successfully");
			}
			
		} catch (SQLException sqlException) {
			
			logger.error(sqlException.getMessage());
			throw new UASException("Technical problem occured refer log");
		} finally
		{
			try 
			{
				resultset.close();
				checkStatement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				logger.error(sqlException.getMessage());
				
			}
		}
		return programIdList;
	}


	/*******************************************************************************************************
	 - Function Name	:	retrieveAllApplicationIds()
	 - Input Parameters	:	NA
	 - Return Type		:	List<String>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving all Application Id's from database
	 ********************************************************************************************************/
	@Override
	public List<Integer> retrieveAllApplicationIds() throws UASException {
		List<Integer> applicationIdList = new ArrayList<Integer>();
		Connection connection = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement checkStatement = null;
		ResultSet resultset = null;
		
		
		try {
			checkStatement = connection.prepareStatement(IQueryMapper.GET_ALL_APPLICATION_ID_QUERY);
			resultset = checkStatement.executeQuery();
			while(resultset.next())
			{
				applicationIdList.add(resultset.getInt("application_id"));		
			}
			if( applicationIdList.isEmpty())
			{
				logger.info("Application id's not retrieved.");
			}
			else
			{
				logger.info("Application id's retrieved Successfully");
			}
		} catch (SQLException sqlException) {
			
			logger.error(sqlException.getMessage());
			throw new UASException("Technical problem occured refer log");
		} finally
		{
			try 
			{
				resultset.close();
				checkStatement.close();
				connection.close();
			}
			catch (SQLException sqlException) 
			{
				
				logger.error(sqlException.getMessage());
				
			}
		}
		return applicationIdList;
	}
}