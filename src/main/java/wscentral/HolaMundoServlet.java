package wscentral;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HolaMundoServlet extends HttpServlet {
	
	public void init(ServletConfig conf)
		    throws ServletException
		  {
		    super.init(conf);
		  }

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Hola Mundo</h1;>");
		out.println("</body>");
		out.println("</html>");
	}

}
