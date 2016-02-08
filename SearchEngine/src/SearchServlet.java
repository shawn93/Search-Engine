
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {
    protected static Logger log = LogManager.getLogger();

    public static void prepareResponse(String title, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();

            writer.printf("<!DOCTYPE html>%n%n");
            writer.printf("<html lang=\"en\">%n%n");
            writer.printf("<head>%n");
            writer.printf("\t<title>%s</title>%n", title);
            writer.printf("\t<meta charset=\"utf-8\">%n");
            writer.printf("</head>%n%n");
            writer.printf("<body>%n%n");
        }
        catch (IOException ex) {
            log.warn("Unable to prepare HTTP response.");
            return;
        }
    }

    public static void finishResponse(HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();

            writer.printf("%n");
            writer.printf("<p style=\"font-size: 10pt; font-style: italic;\">");
            writer.printf("Last updated at %s.", getDate());
            writer.printf("</p>%n%n");

            writer.printf("</body>%n");
            writer.printf("</html>%n");

            writer.flush();

            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        catch (IOException ex) {
            log.warn("Unable to finish HTTP response.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }
    
    public static String getDate() {
        String format = "hh:mm a 'on' EEE, MMM dd, yyyy";
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        prepareResponse("Search", response);

        PrintWriter out = response.getWriter();

        LoginBaseServlet loginbs = new LoginBaseServlet();
        String user = loginbs.getUsername(request);

        out.println("<form action=\"/Search\" method=\"post\">");
        if (user != null) {
            out.println("\t\t<td> Logged in As: " + user + "</td>");
        } else {
            out.println("\t\t<td>Register or Login Below</td>");
        }
        out.println("<table border=\"0\">");

        out.println("\t\t<td><input type=\"text\" name=\"Search\" size=\"30\"></td>");
        out.println("\t</tr>");
        out.println("</table>");
        out.println("<p><input type=\"submit\" value=\"Search\"></p>");
        if (user == null) {
            out.print("<p><a href=\"/login\">Login.</a></p>");
            out.print("<p><a href=\"/register\">New User? Register Here.</a></p>");

        } else {
            out.println("<p><a href=\"/history\">View History</a></p>");
            out.println("<p><a href=\"/changepassword\">Change Password</a></p>");
            out.println("<p><a href=\"/login?logout\">(logout)</a></p>");
        }
        out.println("</form>");

        finishResponse(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        prepareResponse("Search", response);

        String search = request.getParameter("Search");
        Cookie cookie = new Cookie("query", search);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        try {
            response.sendRedirect(response.encodeRedirectURL("/results"));
        } catch (IOException e) {
            System.out.println("Failed Redirect");
        }

    }

}