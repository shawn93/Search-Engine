import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class HeaderServer {
	
	public static void main(String[] args) throws Exception {
	
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HeaderServlet.class, "/Header");
		server.setHandler(handler);
		server.start();
		server.join();

	}

	@SuppressWarnings("serial")
    public static class HeaderServlet extends HttpServlet {

		@Override
		protected void doGet(
				HttpServletRequest request,
				HttpServletResponse response)
				throws ServletException, IOException {

			// Check to make sure the browser is not requesting favicon.ico
			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			response.setContentType("text/html");
		
			PrintWriter out = response.getWriter();
			HTTPFetcher httpfetcher = new HTTPFetcher();
			out.printf("<html>%n");
			out.printf("<head><title>Example</title></head>%n");
			out.printf("<body>%n");
			out.printf("<h1>Link Checker</h1>%n");
			out.printf("<p><label for=\"uri_1\">Enter the address (<a href=\"http://www.w3.org/Addressing/\">URL</a>) of a document that you would like to check:</label></p>%n");
			out.printf("<p><input type=\"text\" size=\"50\" id=\"uri_1\" name=\"uri\" value=\"\" /></p>%n");
			out.printf("<fieldset id=\"extra_opt_uri_1\" class=\"moreoptions\">");
			out.printf("<p class=\"submit_button\"><input type=\"submit\" name=\"check\" value=\"Check\" /></p>%n");
			out.printf("<div id=\"w3c-include\">%n");
			out.print("<script type=\"text/javascript\" src=\"http://www.w3.org/QA/Tools/w3c-include.js\"></script>%n");
			out.print("</div>%n");
			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
