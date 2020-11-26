package problem2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class WelcomeServer {
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(WelcomeServlet.class, "/welcome");
        server.setHandler(handler);

        server.start();
        server.join();
    }

}
