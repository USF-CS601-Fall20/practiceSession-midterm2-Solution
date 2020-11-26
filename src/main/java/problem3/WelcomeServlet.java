package problem3;

import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();
        out.println("<html>");
        out.println("<body>");

        if (cookies != null && cookies.length == 1) {
            Cookie nameCookie = cookies[0];
            System.out.println(nameCookie.getName() + " " + nameCookie.getValue());
            if (nameCookie.getName().equals("name")) {
                String name = cookies[0].getValue();
                System.out.println("Found cookie: " + name);
                Cookie c = new Cookie("name", null);
                c.setMaxAge(0);
                response.addCookie(c);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("Welcome, " + name);
                out.println("</body>");
                out.println("</html>");
                return;
            }
        }
       else {
            out.println("<p>Please fill out this form </p>");
            out.println("<form action = \"welcome\" method = \"post\">");
            out.println("<input type = \"text\" name = \"name\"><br>");
            out.println("<input type = \"submit\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException  {
        System.out.println("In doPost");
        String name = request.getParameter("name");
        String cleanName = StringEscapeUtils.escapeHtml4(name);

        response.addCookie(new Cookie("name", cleanName));

        DatabaseHandler db = DatabaseHandler.getInstance();
        db.storeNameInDatabase(cleanName);

        response.sendRedirect(request.getServletPath());
    } // doPost


} // WelcomeServlet class
