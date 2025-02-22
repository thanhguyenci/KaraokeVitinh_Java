package remote;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;

public class SocketServer01 {

    // Helper function to convert int to byte array (4 bytes)
    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    private static void handleClient(Socket socket) {
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedReader bufferedReader = null;
        try {
            outputStream = socket.getOutputStream();
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // The path to the file
            String filePath = "songlist/songlist1.json";
            File file = new File(filePath);

            if (!file.exists()) {
                System.err.println("File not found: " + filePath);
                // Send error code.
                byte[] lengthBytes = intToByteArray(-2);
                bufferedOutputStream.write(lengthBytes);
                bufferedOutputStream.flush();
                return;
            }

            // Send in chunks directly from the file
            int chunkSize = 120 * 1024; // 120KB chunk size
            System.out.println("Chunk size : " + chunkSize / 1024 + " KB");

            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                byte[] chunk = new byte[chunkSize];
                int bytesRead;

                while ((bytesRead = bufferedInputStream.read(chunk)) != -1) {
                    // Send the length of the chunk first (4 bytes)
                    byte[] lengthBytes = intToByteArray(bytesRead);
                    try {
                        bufferedOutputStream.write(lengthBytes);
                        bufferedOutputStream.flush();
                    } catch (SocketException e) {
                        System.err.println("Socket write error: 1 " + e.getMessage());
                        return; // Exit the loop on socket error
                    }

                    // Send the chunk
                    try {
                        bufferedOutputStream.write(chunk, 0, bytesRead);
                        bufferedOutputStream.flush();
                    } catch (SocketException e) {
                        System.err.println("Socket write error: 2 " + e.getMessage());
                        return; // Exit the loop on socket error
                    }
                }

                // Send end of stream (-1).
                byte[] lengthBytes = intToByteArray(-1);
                try {
                    bufferedOutputStream.write(lengthBytes);
                    bufferedOutputStream.flush();
                } catch (SocketException e) {
                    System.err.println("Socket write error: 3 " + e.getMessage());
                    return; // Exit the loop on socket error
                }

                // Receive the response (optional, for confirmation or other client actions)
                try {
                    String response = bufferedReader.readLine();
                    System.out.println("Client response: " + response);
                    if (response != null && response.equalsIgnoreCase("OK")) {
                        System.out.println("Transfer success");
                    }
                } catch (SocketTimeoutException e) {
                    System.err.println("Read timeout: " + e.getMessage());
                } catch (SocketException e) {
                    System.err.println("Socket error: " + e.getMessage());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    //  bufferedOutputStream.close();// Do not close bufferedOutputStream
                    bufferedOutputStream.flush();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (outputStream != null) {
                    //  outputStream.close();// Do not close outputStream
                    outputStream.flush();
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            new Thread(() -> handleClient(socket)).start();
        }
    }
    public static String formatFileSize(long fileSizeInBytes) {
        double fileSizeInMB = (double) fileSizeInBytes / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(fileSizeInMB) + " MB";
    }
}