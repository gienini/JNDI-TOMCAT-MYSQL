package holamundo;
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

public class HolaMundo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		String var = "";
		String foo = "";
		if (req.getParameterNames().hasMoreElements())
			var = req.getParameterNames().nextElement();

		Context init;
		try {
			init = new InitialContext();
			Context env = (Context) init.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/tomcat");
			Connection con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM test");
			ResultSet result = ps.executeQuery();
			result.next();
			foo = result.getString(1);

		} catch (NamingException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		pw.write("<style media='screen' type='text/css'>h1{color:" + var
				+ ";}</style>");

		pw.write("<H1>HOLA MUNDO</H1>" + var + foo);

	}

}
