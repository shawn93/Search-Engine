import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class HistoryServlet extends HttpServlet{

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
         LoginBaseServlet loginbs = new LoginBaseServlet();
         String user = loginbs.getUsername(request);

        if (user != null) {
            SearchServlet.prepareResponse("Welcome", response);

            PrintWriter out = response.getWriter();
            out.println("<p>Hello " + user + "!</p>");
            out.println("<p><a href=\"/login?logout\">(logout)</a></p>");
            out.println("<p><a href=\"/\">Back to Home</a></p>");
            out.printf("<p><input type=\"submit\" value=\"Clear History\"></p>\n%n");
            loginbs.dbhandler.outputHistory(request, response);
            SearchServlet.finishResponse(response);
        }
        else {
            response.sendRedirect("/");
        }
    }

    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //clear history
        response.sendRedirect("/history");
        
    }
}
