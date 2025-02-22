package remote;

import java.io.*;
import java.net.*;

public class ClientSocketExample {

    public static void main(String args[]) throws Exception {
        // Create socket connection
        Socket socket = new Socket("localhost", 8888);

        // create printer writer for sending data to the server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // create buffered reader for reading response from server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // sending message to server
        out.println("Hello from Client");

        // receiving message from server
        String str = in.readLine();
        System.out.println("Server: " + str);

        // closing connection
        out.close();
        in.close();
        socket.close();
    }
}