package com.capg.uas.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capg.uas.dao.AdminDaoImpl;
import com.capg.uas.exception.UASException;
import com.capg.uas.service.AdminServiceImpl;
import com.capg.uas.util.ConnectionProvider;

public class AdminServiceImplTest {


	@Before
	public void setUp() throws Exception {
		Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement st = con.prepareStatement("INSERT INTO Users values(?,?,?)");
		st.setString(1,"dummy");
		st.setString(2, "p@ss");
		st.setString(3, "Admin");
		st.executeQuery();
		if(con!=null && !con.isClosed())
			con.close();
	}

	@After
	public void tearDown() throws Exception {
		Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		PreparedStatement st = con.prepareStatement("DELETE FROM Users WHERE login_id=?");
		st.setString(1,"dummy");
		st.executeQuery();
		if(con!=null && !con.isClosed())
			con.close();
	}

	@Test
	public void test() throws UASException {
		AdminServiceImpl userService = new AdminServiceImpl();
		try {
			Boolean role = userService.validateAdmin("dummy","p@ss");
			assertEquals(true,role);
			AdminDaoImpl.log.info("1");
			
		} catch (UASException e) {
			AdminDaoImpl.log.error("0");
			throw new UASException("USER Not validated");
		}
	}

}
