package com.ecom.auth_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class AuthServiceApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void testConnection() throws Exception {
		System.out.println("Testing DB connection...");
		try (Connection conn = dataSource.getConnection()) {
			System.out.println("Connected to: " + conn.getMetaData().getURL());
		}
	}
}
