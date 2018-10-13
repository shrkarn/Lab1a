package com.capg.uas.dao;

public interface IQueryMapper {
	//Queries for Login
	public static final String GET_USER="SELECT * FROM Users WHERE login_id=?";
	
	//Queries for Admin
	public static final String FIND_APPLICANT_PROG_ID_STATUS ="SELECT * FROM (SELECT * FROM Application WHERE Scheduled_program_id= ? ) WHERE status=?";

	public static final String ADD_PROGRAM_OFFERED_QUERY = "INSERT INTO Programs_Offered VALUES (?,?,?,?,?)";

	public static final String ADD_PROGRAM_SCHEDULED_QUERY = "INSERT INTO Programs_Scheduled VALUES (?,?,?,?,?,?)";

	public static final String UPDATE_PROGRAM_OFFERED_QUERY = "UPDATE Programs_Offered SET description= ?, applicant_eligibility= ? WHERE ProgramName=?";
	
	public static final String DELETE_PROGRAM_OFFERED_QUERY = "DELETE FROM Programs_Offered WHERE ProgramName=?";

	public static final String COUNT_APPLICANT_QUERY = "SELECT COUNT(application_id) FROM Application WHERE Scheduled_program_id=?";

	public static final String DELETE_PROGRAM_SCHEDULED_QUERY = "DELETE FROM Programs_Scheduled WHERE Scheduled_program_id=?";

	public static final String LIST_PROGRAMS_OFFERED = "SELECT * FROM Programs_Offered";
	
	public static final String LIST_PROGRAMS_SCHEDULED_DATES = "SELECT * FROM Programs_Scheduled WHERE ( start_date BETWEEN ? AND ?)";
	
	public static final String CHECK_PROGRAM_NAME = "SELECT ProgramName FROM Programs_Offered ";
	
	public static final String CHECK_PROGRAM_ID = "SELECT Scheduled_program_id FROM Programs_Scheduled ";
	
	//Queries for MAC
	public static final String LIST_SCHEDULED_PROGRAMS = "SELECT * FROM Programs_Scheduled";

	public static final String FIND_PROG_APPLICANT="SELECT * FROM Application WHERE Scheduled_program_id IN( Select Scheduled_program_id from Programs_Scheduled where ProgramName=?) ";
	
	public static final String FIND_APPLIED_APPLICANT="SELECT * FROM Application WHERE status='APPLIED' AND Scheduled_program_id IN( Select Scheduled_program_id from Programs_Scheduled where ProgramName=?) ";
	
	public static final String REJECT_APPLICANT_STATUS="UPDATE Application SET status='REJECTED' WHERE application_id=?";
	
	public static final String LIST_INTERVIEWED_CANDIDATES = "SELECT * FROM Application WHERE status='ACCEPTED'";
	
	public static final String FETCH_PARTICIPANT="SELECT * FROM Application where application_id=? ";
	
	public static final String ADD_PARTICIPANT ="INSERT INTO PARTICIPANT VALUES (PARTICIPANT_ID.NEXTVAL,?,?,?)";
	
	public static final String CONFIRM_APPLICANT_STATUS="UPDATE Application SET status='CONFIRMED' WHERE application_id=?";
	
	public static final String ACCEPT_APPLICANT_STATUS="UPDATE Application SET status='ACCEPTED',Date_Of_Interview=? WHERE application_id=?";


	//Queries for Applicant
	public static final String ADD_APPLICANT_QUERY="INSERT INTO Application VALUES(applicationId_seq.NEXTVAL,?,?,?,?,?,?,?,'APPLIED',NULL)";
	
	public static final String APPLICATION_ID_QUERY="SELECT applicationId_seq.CURRVAL FROM DUAL";
	
	public static final String CHECK_APPLICATION_STATUS_QUERY ="SELECT status FROM Application WHERE application_id=?";
	
	public static final String GET_ALL_PROGRAM_ID_QUERY = "SELECT Scheduled_program_id FROM Programs_Scheduled";
	
	public static final String GET_ALL_APPLICATION_ID_QUERY  = "SELECT application_id FROM Application";

	
}
