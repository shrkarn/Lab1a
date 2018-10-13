package com.capg.uas.service;

import java.util.List;

import com.capg.uas.bean.Applicant;
import com.capg.uas.bean.ProgramOffered;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;

public interface IAdminService {

	public boolean validateAdmin(String userName, String password) throws UASException;

	public String addProgramOffered(ProgramOffered progOffered) throws UASException;

	public String updateProgramOffered(ProgramOffered progOffered) throws UASException;

	public boolean deleteProgramOffered(String progName) throws UASException;

	public List<ProgramOffered> getAllOfferedPrograms() throws UASException;

	public String addProgramScheduled(ProgramScheduled progScheduled) throws UASException;

	public int deleteProgramScheduled(String progId) throws UASException;

	public List<ProgramScheduled> getDatedProgramsSchedule(
			java.sql.Date fromDateSQL, java.sql.Date toDateSQL) throws UASException;

	public List<Applicant> viewCandidates(Applicant applicants) throws UASException;

	public boolean programNameCheck(String progName) throws UASException;
	
	public boolean programIdCheck(String progId) throws UASException;

}
