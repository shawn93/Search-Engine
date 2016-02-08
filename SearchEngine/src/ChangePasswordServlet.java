import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ChangePasswordServlet extends LoginBaseServlet{

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            prepareResponse("Login", response);

            PrintWriter out = response.getWriter();

            out.println("<p style=\"color: red;\">" + "Wrong Password" + "</p>");

            printForm(out);
            finishResponse(response);
        }

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) {
            String oldpass = request.getParameter("user");
            String newpass = request.getParameter("pass");
            String user = getUsername(request);
            
            Status status = dbhandler.authenticateUser(user, oldpass);

            try {
                if (status == Status.OK) {
                    
                    dbhandler.changePW(user, newpass);
                    
                    response.sendRedirect(response.encodeRedirectURL("/"));
                }
                else {
                    response.sendRedirect("/changepassword" + status.ordinal());
                }
            }
            catch (Exception ex) {
                log.error("Unable to process login form.", ex);
            }
        }

        private void printForm(PrintWriter out) {
            out.println("<form action=\"/login\" method=\"post\">");
            out.println("<table border=\"0\">");
            out.println("\t<tr>");
            out.println("\t\t<td>Old Username:</td>");
            out.println("\t\t<td><input type=\"text\" name=\"user\" size=\"30\"></td>");
            out.println("\t</tr>");
            out.println("\t<tr>");
            out.println("\t\t<td>New Password:</td>");
            out.println("\t\t<td><input type=\"password\" name=\"pass\" size=\"30\"></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<p><input type=\"submit\" value=\"Change\"></p>");
            out.println("<p><a href=\"/login\">Back to Home</a></p>");
            out.println("</form>");

        }

}
