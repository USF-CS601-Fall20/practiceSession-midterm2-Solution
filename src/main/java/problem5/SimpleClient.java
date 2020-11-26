package problem5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SimpleClient: Reads input from the keyboard and sends it to the server via
 * the socket.  Used to test class Server.
 *
 */
public class SimpleClient extends Thread {
	@Override
	public void run() {
		System.out.println("Client: Started...");
		try (Socket socket = new Socket("localhost", Server.PORT);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			 PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		)
		{
			String input;
			if (!socket.isClosed()) { // sending one request
				input = reader.readLine(); // read user's keyboard input
				writer.println(input); // send it to the server
				writer.flush();
				if (input.equals(Server.SHUTDOWN)) {
					System.out.println("Client: Shutting down server.");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void main(String[] args) {
		SimpleClient client = new SimpleClient();
		client.start();
		try {
			client.join();
		} catch (InterruptedException e) {
			System.out.println("The thread got interrupted " + e);
		}
	}
}