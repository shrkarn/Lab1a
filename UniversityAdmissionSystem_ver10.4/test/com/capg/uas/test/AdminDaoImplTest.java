package com.capg.uas.test;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capg.uas.bean.ProgramOffered;
import com.capg.uas.bean.ProgramScheduled;
import com.capg.uas.exception.UASException;
import com.capg.uas.service.AdminServiceImpl;
import com.capg.uas.service.IAdminService;

public class AdminDaoImplTest {

	@Before
	public void setUp() throws UASException {
	}

	@After
	public void tearDown() throws UASException {
	}

	@Test
	public void programOfferedTest() {

		ProgramOffered program = new ProgramOffered();

		program.setProgName("TEST");//Change the Program Name each Time you run the test as it is Primary Key
		program.setDesc("Testing");
		program.setAppEligibility("Testing Phase");
		program.setDuration(10);
		program.setDegreeOffered("Master");
		
		try {
			IAdminService admin = new AdminServiceImpl();
			String progName = admin.addProgramOffered(program);
			
			assertNotNull(progName);
		} catch (UASException uasException) {
			
			System.err.println("The following errors occurred: "
					+ uasException.getMessage());
		}
	}
	
	@Test
	public void programScheduleTest() {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		myFormat.setLenient(false);
		
		ProgramScheduled program = new ProgramScheduled();

		program.setProgName("TEST");
		program.setScheduleProgId("TESTS");//Change the ProgramId each Time you run the test as it is Primary Key
		program.setLocation("Location");
		program.setSessionsPerWeek(10);
		
		String startDate = "15/07/2019";
		String endDate = "15/07/2020";
		try {
			Date start;
			start = myFormat.parse(startDate);
			java.sql.Date startsql = new java.sql.Date(start.getTime());
			program.setStart(startsql);
			
			Date end;
			end = myFormat.parse(endDate);
			java.sql.Date endsql = new java.sql.Date(end.getTime());
			program.setEnd(endsql);
		} catch (ParseException e) {
			System.err.println("Date is of Invalid format. Please try again");
		}
		
		try {
			IAdminService admin = new AdminServiceImpl();
			String progId = admin.addProgramScheduled(program);
			
			assertNotNull(progId);
		} catch (UASException uasException) {
			
			System.err.println("The following errors occurred: "+ uasException.getMessage());
		}
	}

}
