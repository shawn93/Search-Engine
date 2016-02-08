import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ResultServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        SearchServlet.prepareResponse("Results", response);

        PrintWriter out = response.getWriter();

        ArrayList<SearchResult> results;
        LoginBaseServlet loginbs = new LoginBaseServlet();
        Map<String, String> cookies = loginbs.getCookieMap(request);
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        loginbs.dbhandler.addHistory(timeStamp, cookies.get("query"), loginbs.getUsername(request));
        ArrayList<String> query = new ArrayList<String>();
        query.add(cookies.get("query"));
        results = Driver.invertedindex.search(query);
        loginbs.dbhandler.addHistory(timeStamp, cookies.get("query"), loginbs.getUsername(request));
        printForm(out, results);

        SearchServlet.finishResponse(response);

    }

    @Override
    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response) {
        
        SearchServlet.prepareResponse("Search", response);
    
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

    private void printForm(PrintWriter out, ArrayList<SearchResult> results) {
        out.println("<form action=\"/Results\" method=\"post\">");
        out.println("<table border=\"0\">");
        out.println("\t<tr>");
        out.println("\t\t<td></td>");
        out.println("\t\t<td><input type=\"text\" name=\"Search\" size=\"30\"></td>");
        out.println("\t</tr>");
        out.println("</table>");
        out.println("<p><input type=\"submit\" value=\"Search\"></p>");
        out.println("\n" + results.size() + " Results Found" + "\n\n");

        if (results.size() > 0) {
            out.printf("<ul>%n");
            for (SearchResult result : results) {
                out.printf("<li><a href=\"" + result.getPath() + "\">" + result.getPath() + "</a>%n");
                
            }
            out.printf("</ul>%n");
        }
        out.println("<p><a href=\"/\">Back to Home</a></p>");
        out.println("</form>");
    }
}
