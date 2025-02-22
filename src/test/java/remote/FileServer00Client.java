package remote;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class FileServer00Client {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // Or the server's IP
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port);
             InputStream inputStream = socket.getInputStream();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             OutputStream outputStream = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream, true)) {

            // Read from server
            StringBuilder stringBuilder = new StringBuilder();
            int chunkSize;
            while ((chunkSize = readInt(bufferedInputStream)) != -1) {
                if (chunkSize == -2) {
                    System.out.println("File not exists");
                    return;
                }
                byte[] chunk = new byte[chunkSize];
                int bytesRead = bufferedInputStream.read(chunk);
                stringBuilder.append(new String(chunk, 0, bytesRead, StandardCharsets.UTF_8));
                System.out.println(stringBuilder);
            }
            System.out.println("File received");

            //send response
            writer.println("OK");
            writer.flush();

            // Print the received data (for verification)
            // System.out.println("Received data:\n" + stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int readInt(BufferedInputStream in) throws IOException {
        byte[] lengthBytes = new byte[4];
        int bytesRead = in.read(lengthBytes);
        if (bytesRead != 4) {
            return -1;
        }
        return byteArrayToInt(lengthBytes);
    }

    public static int byteArrayToInt(byte[] bytes) {
        return (bytes[0] << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}