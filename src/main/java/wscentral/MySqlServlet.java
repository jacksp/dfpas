package wscentral;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MySqlServlet  extends HttpServlet{
	
	protected void doGet(HttpServletRequest request,
	        HttpServletResponse response) throws ServletException, IOException {
		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		
	       int id =  1;
	      String ingredientes =  "huevo patatas";
	       String nombre =  "tortilla de patadas";
	       String url = String.format("jdbc:mysql://%s:%s/andi", host, port);
	 
	        final String user = "admin9lmwL5t";
	        final String password = "QYNXgYdgeRJB";
	        try {

	            // jdbc try
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = (Connection) DriverManager.getConnection(url, user,
	                    password);
	            // insert values into the first table
	        PreparedStatement s = (PreparedStatement) con
	                    .prepareStatement("INSERT INTO  recetas(id,nombre,ingredientes) VALUES (?,?,?)");

	        s.setInt(1, id);
	        s.setString(2, ingredientes);
	        s.setString(3, nombre);
	        s.executeUpdate();




	        }  catch (SQLException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        PrintWriter out = response.getWriter();
	        out.println("Receta u ruajt me sukses ne server !");
	}

}
