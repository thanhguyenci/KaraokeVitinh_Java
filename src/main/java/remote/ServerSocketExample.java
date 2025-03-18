package remote;

import java.io.*;
import java.net.*;

public class ServerSocketExample {
    public static void main(String args[]) throws Exception {

        // Create server socket
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Waiting for client request");

        // waiting for the client request
        Socket socket = serverSocket.accept();

        // create printer writer for sending data to the Client
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // create buffered reader for reading response from Client
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // receiving message from Client
        String str = in.readLine();
        System.out.println("Client: " + str);

        // sending message to Client
        out.println("Hello from Server");

        // closing connection
        out.close();
        in.close();
        socket.close();
        serverSocket.close();
    }
}