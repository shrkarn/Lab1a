package com.capg.uas.dao;

import java.util.List;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;

public interface IApplicantDao {

	List<ProgramScheduled> getAllScheduledPrograms() throws UASException;

	int addApplicant(Applicant applicant) throws UASException;

	String checkApplicationStatus(int applicantId) throws UASException;
	
	public List<String> retrieveAllProgramIds() throws UASException;

	public List<Integer> retrieveAllApplicationIds() throws UASException;

}
