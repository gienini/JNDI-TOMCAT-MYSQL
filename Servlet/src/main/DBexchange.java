package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DBexchange extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Context init;
		try {
			init = new InitialContext();
			Context env = (Context) init.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/tomcat");
			Connection con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM test");
			ResultSet result = ps.executeQuery();
			String nom= "";
			int edat = 0;
			String sortida= "<table><tr><td>NOM</td><td>EDAT</td></tr>";
			while (result.next()){
				nom = result.getString(1);
				edat = result.getInt(2);
				sortida = sortida+"<tr><td>"+nom+"</td><td>"+edat+"</td></tr>";
			}
			
			sortida = sortida+"</table>";
			PrintWriter pw = resp.getWriter();
			pw.write("<style media='screen' type='text/css'>td{border-style:solid;}</style>");
			pw.write(sortida);
		

		} catch (NamingException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
