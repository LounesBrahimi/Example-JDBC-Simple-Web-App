package net.lounes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/database")
public class DatabaseAccess extends HttpServlet{
    
	  public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      // JDBC driver name and database URL
	      final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	      final String DB_URL="jdbc:mysql://localhost:3306/employees?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&useSSL=false";

	      //  Database credentials
	      final String USER = "root";
	      final String PASS = "password";

	      // Set response content type
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Database Result";
	      out.println("<html>\n" +
	         "<head><title>" + title + "</title></head>\n" +
	         "<body bgcolor=\"#f0f0f0\">\n" +
	         "<h1 align=\"center\">" + title + "</h1>\n");
	      try{
	         // Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);

	         // Execute SQL query
	         Statement stmt = conn.createStatement();
	         String sql;
	         sql = "SELECT id, first_name, last_name, username FROM employee";
	         ResultSet rs = stmt.executeQuery(sql);

	         // Extract data from result set
	         while(rs.next()){
	            //Retrieve by column name
	            int id  = rs.getInt("id");
	            String first = rs.getString("first_name");
	            String last = rs.getString("last_name");
	            String username = rs.getString("username");

	            //Display values
	            out.println("ID: " + id);
	            out.println(", Username: " + username);
	            out.println(", First: " + first);
	            out.println(", Last: " + last + "<br>");
	         }
	         out.println("</body></html>");

	         // Clean-up environment
	         rs.close();
	         stmt.close();
	         conn.close();
	      }catch(SQLException se){
	         //Handle errors for JDBC
	         se.printStackTrace();
	      }catch(Exception e){
	         //Handle errors for Class.forName
	         e.printStackTrace();
	      }
	   }
	} 