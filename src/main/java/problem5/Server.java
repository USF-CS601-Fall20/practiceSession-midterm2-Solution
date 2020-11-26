package problem5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 8080;
    public static final String SHUTDOWN = "SHUTDOWN";
    private boolean alive = true;

    public void communicate() {
        try (ServerSocket welcomingSocket = new ServerSocket(PORT)) {
            while (alive) {
                System.out.println("Server: Waiting for connection...");
                try (Socket connectionSocket = welcomingSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(connectionSocket.getOutputStream())) {

                    System.out.println("Server: Client connected.");
                    String input = reader.readLine();
                    System.out.println("Server received: " + input);
                    writer.println(input.length());
                    System.out.println("Server sent: " + input.length());

                    if (input.equals(SHUTDOWN)) {
                        alive = false;
                        System.out.println("Server: Shutting down.");
                        welcomingSocket.close();
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.communicate();
    }
}
