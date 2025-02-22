package thanh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 5000;
        int readTimeoutMillis = 10000; // 10 seconds
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(readTimeoutMillis); // Set the read timeout
            System.out.println("Client connected");

            // Start a new thread to handle the client
            new Thread(() -> handleClient(socket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream();
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // The path to the file (replace with your actual file)
            //String filePath = "large_file.txt";
            String filePath = "songlist/songlist.json";
            File file = new File(filePath);
            if (!file.exists()) {
                try (PrintWriter writer = new PrintWriter(filePath)) {
                    for (int i = 0; i < 30000; i++) {
                        writer.println("0123456789");
                    }
                    System.out.println("File created");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!file.canRead()) {
                System.err.println("Cannot read file: " + filePath);
                return; // Exit early if we can't read the file
            }
            System.out.println("File size: " + file.length());

            // Send in chunks (e.g., 4KB chunks)
            int chunkSize = 4096; // 4KB

            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                byte[] chunk = new byte[chunkSize];
                int bytesRead;
                int totalBytesSent = 0;

                while ((bytesRead = bufferedInputStream.read(chunk)) != -1) {
                    totalBytesSent += bytesRead;
                    System.out.println("Server: read " + bytesRead + " bytes from file, total: " + totalBytesSent);
                    // Send the length of the chunk first (4 bytes)
                    byte[] lengthBytes = intToByteArray(bytesRead);
                    try {
                        bufferedOutputStream.write(lengthBytes);
                        bufferedOutputStream.flush(); // Very important!
                        System.out.println("Server: sent length: " + bytesRead);
                    } catch (SocketException e) {
                        System.err.println("Server Socket write error: " + e.getMessage());
                        return;
                    } catch (IOException e){
                        System.err.println("Server: IOException: " + e.getMessage());
                        return;
                    }

                    // Send the chunk
                    try {
                        bufferedOutputStream.write(chunk, 0, bytesRead);
                        bufferedOutputStream.flush(); // Very important!
                        System.out.println("Server: sent chunk of " + bytesRead + " bytes");
                    } catch (SocketException e) {
                        System.err.println("Server Socket write error: " + e.getMessage());
                        return;
                    } catch (IOException e){
                        System.err.println("Server: IOException: " + e.getMessage());
                        return;
                    }
                }
                // Send end of stream (-1).
                byte[] lengthBytes = intToByteArray(-1);
                try {
                    bufferedOutputStream.write(lengthBytes);
                    bufferedOutputStream.flush(); // Very important!
                    System.out.println("Server: sent end of stream marker");
                } catch (SocketException e) {
                    System.err.println("Server Socket write error: " + e.getMessage());
                    return;
                } catch (IOException e){
                    System.err.println("Server: IOException: " + e.getMessage());
                    return;
                }
                //Receive the response
                try {
                    String response = bufferedReader.readLine();
                    System.out.println("Server received response :"+response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Server Read timeout: " + e.getMessage());
                } catch (SocketException e){
                    System.err.println("Server SocketException: " + e.getMessage());
                } catch (IOException e){
                    System.err.println("Server: IOException: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Server Error reading from file: " + e.getMessage());
            }

        } catch (SocketTimeoutException e) {
            System.err.println("Server Read timeout: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Server Error handling client: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (SocketException e) {
                System.err.println("Server Socket close error: " + e.getMessage());
            } catch (IOException e){
                System.err.println("Server Error : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Helper function to convert int to byte array (4 bytes)
    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }
}