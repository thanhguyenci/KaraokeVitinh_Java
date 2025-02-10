package remote;

import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String message = in.readLine();
                    System.out.println("Received: " + message);

                    // Respond to client
                    out.println("Hello, Client!");

                } catch (IOException e) {
                    System.out.println("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
