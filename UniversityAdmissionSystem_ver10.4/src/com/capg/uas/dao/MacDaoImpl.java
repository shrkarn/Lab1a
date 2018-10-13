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
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.bean.Users;
import com.capg.uas.exception.UASException;
import com.capg.uas.util.ConnectionProvider;

public class MacDaoImpl implements IMacDao {

	public static Logger log = Logger.getLogger("MacDAO");

	/*******************************************************************************************************
	 - Function Name	:	getUserByName()
	 - Input Parameters	:	String
	 - Return Type		:	Users
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of users by String userName
	 ********************************************************************************************************/
	@Override
	public Users getUserByName(String userName) throws UASException {
		Users user = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.GET_USER)) {
					st.setString(1, userName);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user = new Users();
				user.setLoginId(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setRole(rs.getString(3));
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Fetch Records");
		}
		return user;
	}

	/*******************************************************************************************************
	 - Function Name	:	listPrograms()
	 - Input Parameters	:	NA
	 - Return Type		:	List<ProgramScheduled>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all scheduled programs from 
	 						database table Programs_Scheduled
	 ********************************************************************************************************/
	@Override
	public List<ProgramScheduled> listPrograms() throws UASException {
		List<ProgramScheduled> programsList = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.LIST_SCHEDULED_PROGRAMS);) {

			ResultSet rs = st.executeQuery();

			programsList = new ArrayList<ProgramScheduled>();
			while (rs.next()) {
				ProgramScheduled program = new ProgramScheduled();
				program.setScheduleProgId(rs.getString("Scheduled_program_id"));

				program.setProgName(rs.getString("ProgramName"));

				program.setLocation(rs.getString("Location"));

				program.setStart(rs.getDate("start_date"));

				program.setEnd(rs.getDate("end_date"));

				program.setSessionsPerWeek(rs.getInt("session_per_week"));

				programsList.add(program);
			}

			if (programsList.isEmpty())
				programsList = null;
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Fetch Programs");
		}
		return programsList;
	}

	/*******************************************************************************************************
	 - Function Name	:	findProgApplicant()
	 - Input Parameters	:	String
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all applicants for an offered program 
	 						from database Table Application
	 ********************************************************************************************************/
	@Override
	public List<Applicant> findProgApplicant(String pName) throws UASException {

		List<Applicant> appList = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement st = con
						.prepareStatement(IQueryMapper.FIND_PROG_APPLICANT);) {
			st.setString(1, pName);
			ResultSet rs = st.executeQuery();

			appList = new ArrayList<Applicant>();
			while (rs.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppId(rs.getInt("application_id"));
				applicant.setAppName(rs.getString("full_name"));
				applicant.setAppDOB(rs.getDate("date_of_birth"));
				applicant.setQualification(rs.getString("highest_qualification"));
				applicant.setMarks(rs.getInt("marks_obtained"));
				applicant.setGoals(rs.getString("goals"));
				applicant.setEmailId(rs.getString("email_id"));
				applicant.setScheduleProgId(rs.getString("Scheduled_program_id"));
				applicant.setStatus(rs.getString("status"));
				applicant.setDateOfInterview(rs.getDate("Date_Of_Interview"));

				appList.add(applicant);
			}

			if (appList.isEmpty())
				appList = null;
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Fetch Applicants");
		}
		return appList;
	}

	/*******************************************************************************************************
	 - Function Name	:	updateStatus()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	updating status of applicant for given application id
	 ********************************************************************************************************/
	@Override
	public int updateStatus(int aId) throws UASException {
		int isDone = 0;

		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();) {
			try (PreparedStatement stmt = con.prepareStatement(IQueryMapper.REJECT_APPLICANT_STATUS);) {

				stmt.setInt(1, aId);

				int count = stmt.executeUpdate();

				if (count > 0) {
					isDone = aId;
					con.commit();
					return isDone;
				}

			} catch (SQLException exception) {
				con.rollback();
				log.error(exception);
				throw new UASException("Unable To Complete Operation");
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Complete Operation");
		}

		return isDone;

	}

	/*******************************************************************************************************
	 - Function Name	:	assignInterview()
	 - Input Parameters	:	Integer, Date
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	assigning interview date to applicant based on applicant id 
	 ********************************************************************************************************/
	@Override
	public int assignInterview(int aId, Date doiSql) throws UASException {
		int isDone = 0;

		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();) {
			try (PreparedStatement stmt = con.prepareStatement(IQueryMapper.ACCEPT_APPLICANT_STATUS);) {

				stmt.setDate(1, doiSql);
				stmt.setInt(2, aId);

				int count = stmt.executeUpdate();

				if (count > 0) {
					isDone = aId;
					con.commit();
					return isDone;
				}

			} catch (SQLException exception) {
				con.rollback();
				log.error(exception);
				throw new UASException("Unable To Complete Operation");
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Complete Operation");
		}

		return isDone;

	}

	/*******************************************************************************************************
	 - Function Name	:	viewInterviewedCandidates()
	 - Input Parameters	:	NA
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	retrieving details of all applicants to be interviewed from 
	 						database Table Application
	 ********************************************************************************************************/
	@Override
	public List<Applicant> viewInterviewedCandidates() throws UASException {
		List<Applicant> interviewList = null;
		try {
			try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
					PreparedStatement st = con.prepareStatement(IQueryMapper.LIST_INTERVIEWED_CANDIDATES);) {

				ResultSet rs = st.executeQuery();

				interviewList = new ArrayList<Applicant>();
				while (rs.next()) {
					Applicant applicant = new Applicant();
					applicant.setAppId(rs.getInt("application_id"));
					applicant.setAppName(rs.getString("full_name"));
					applicant.setAppDOB(rs.getDate("date_of_birth"));
					applicant.setQualification(rs.getString("highest_qualification"));
					applicant.setMarks(rs.getInt("marks_obtained"));
					applicant.setGoals(rs.getString("goals"));
					applicant.setEmailId(rs.getString("email_id"));
					applicant.setScheduleProgId(rs.getString("Scheduled_program_id"));
					applicant.setStatus(rs.getString("status"));
					applicant.setDateOfInterview(rs.getDate("Date_Of_Interview"));

					interviewList.add(applicant);
				}

				if (interviewList.isEmpty())
					interviewList = null;
			} catch (SQLException exception) {
				log.error(exception);
				throw new UASException("Unable To Fetch Programs");
			}
		} catch (Exception exception) {
			log.error("Exception Occured");
			
		}
		return interviewList;
	}

	/*******************************************************************************************************
	 - Function Name	:	updateInterviewStatus()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	updating applicant status of applicant to CONFIRMED
	 ********************************************************************************************************/
	@Override
	public int updateInterviewStatus(int intrwId) throws UASException {
		int isDone = 0;

		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();) {
			try (PreparedStatement stmt = con.prepareStatement(IQueryMapper.CONFIRM_APPLICANT_STATUS);) {

				stmt.setInt(1, intrwId);

				int count = stmt.executeUpdate();

				if (count > 0) {
					isDone = intrwId;
					con.commit();
					return isDone;
				}

			} catch (SQLException exception) {
				con.rollback();
				log.error(exception);
				throw new UASException("Unable To Complete Operation");
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Complete Operation");
		}

		return isDone;
	}

	/*******************************************************************************************************
	 - Function Name	:	addParticipant()
	 - Input Parameters	:	Integer
	 - Return Type		:	Integer
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	01/10/2018
	 - Description		:	adding participant details to Participant table for confirmed candidates
	 ********************************************************************************************************/
	@Override
	public int addParticipant(int intrwId) throws UASException {
		int isDone = 0;

		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();) {
			try (PreparedStatement stmt = con.prepareStatement(IQueryMapper.FETCH_PARTICIPANT);) {

				stmt.setInt(1, intrwId);

				List<Applicant> interviewList = null;
				ResultSet rs = stmt.executeQuery();

				interviewList = new ArrayList<Applicant>();
				while (rs.next()) {
					Applicant applicant = new Applicant();
					applicant.setAppId(rs.getInt("application_id"));
					applicant.setAppName(rs.getString("full_name"));
					applicant.setAppDOB(rs.getDate("date_of_birth"));
					applicant.setQualification(rs.getString("highest_qualification"));
					applicant.setMarks(rs.getInt("marks_obtained"));
					applicant.setGoals(rs.getString("goals"));
					applicant.setEmailId(rs.getString("email_id"));
					applicant.setScheduleProgId(rs.getString("Scheduled_program_id"));
					applicant.setStatus(rs.getString("status"));
					applicant.setDateOfInterview(rs.getDate("Date_Of_Interview"));

					interviewList.add(applicant);
				}
				
				Applicant participant =interviewList.get(0);
				PreparedStatement st2 = con.prepareStatement(IQueryMapper.ADD_PARTICIPANT);
				st2.setString(1, participant.getEmailId());
				st2.setInt(2, participant.getAppId());
				st2.setString(3, participant.getScheduleProgId());
				int count = st2.executeUpdate();

				
				
				
				if (count > 0) {
					isDone = intrwId;
					con.commit();
					return isDone;
				}

			} catch (SQLException exception) {
				con.rollback();
				log.error(exception);
				throw new UASException("Unable To Complete Operation");
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Complete Operation");
		}

		return isDone;
	
	}

	
	/*******************************************************************************************************
	 - Function Name	:	findAppliedApplicant()
	 - Input Parameters	:	String
	 - Return Type		:	List<Applicant>
	 - Throws			:  	UASException
	 - Author			:	GROUP2
	 - Creation Date	:	12/10/2018
	 - Description		:	retrieving details of all applicants whose status="APPLIED" from 
	 						database Table Application
	 ********************************************************************************************************/
	@Override
	public List<Applicant> findAppliedApplicant(String pName) throws UASException {
		List<Applicant> appList = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE
				.getConnection();
				PreparedStatement st = con
						.prepareStatement(IQueryMapper.FIND_APPLIED_APPLICANT);) {
			st.setString(1, pName);
			ResultSet rs = st.executeQuery();

			appList = new ArrayList<Applicant>();
			while (rs.next()) {
				Applicant applicant = new Applicant();
				applicant.setAppId(rs.getInt("application_id"));
				applicant.setAppName(rs.getString("full_name"));
				applicant.setAppDOB(rs.getDate("date_of_birth"));
				applicant.setQualification(rs.getString("highest_qualification"));
				applicant.setMarks(rs.getInt("marks_obtained"));
				applicant.setGoals(rs.getString("goals"));
				applicant.setEmailId(rs.getString("email_id"));
				applicant.setScheduleProgId(rs.getString("Scheduled_program_id"));
				applicant.setStatus(rs.getString("status"));
				applicant.setDateOfInterview(rs.getDate("Date_Of_Interview"));

				appList.add(applicant);
			}

			if (appList.isEmpty())
				appList = null;
		} catch (SQLException exception) {
			log.error(exception);
			throw new UASException("Unable To Fetch Applicants");
		}
		return appList;
	}

}
