package remote;

import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Change this to your server's IP
        int port = 5000; // Change this to match your server's port

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Sending a message to the server
            out.println("Hello, Server!");

            // Reading response from the server
            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

