package thanh;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "192.168.0.172";
        int port = 5000;
        int readTimeoutMillis = 60000; // 60 seconds

        try (Socket socket = new Socket(serverAddress, port)) {
            socket.setSoTimeout(readTimeoutMillis); // Set the read timeout

            System.out.println("Client: Connected to server.");
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            // Reassemble the string
            ByteArrayOutputStream stringBuilder = new ByteArrayOutputStream();
            byte[] lengthBytes = new byte[4];
            int bytesRead;
            int totalBytesReceived = 0;
            while (true) {
                // Read the length prefix (4 bytes)
                try {
                    int read = bufferedInputStream.read(lengthBytes);
                    if (read == -1) {
                        // end of stream.
                        System.out.println("Client: end of stream.");
                        break;
                    }

                    int chunkLength = byteArrayToInt(lengthBytes);
                    if (chunkLength == -1) {
                        System.out.println("Client: end of transmission.");
                        break;
                    }
                    System.out.println("Client: read chunk length: " + chunkLength);
                    byte[] chunk = new byte[chunkLength];
                    bytesRead = bufferedInputStream.read(chunk, 0, chunkLength);
                    if (bytesRead != chunkLength) {
                        // Handle error
                        System.err.println("Client: Error reading chunk");
                        break;
                    }
                    stringBuilder.write(chunk);
                    totalBytesReceived += bytesRead;
                    System.out.println("Client: received chunk of " + bytesRead + " bytes, total: " + totalBytesReceived);
                } catch (SocketTimeoutException e) {
                    System.err.println("Client: Read timeout: " + e.getMessage());
                    break;
                } catch (SocketException e) {
                    System.err.println("Client SocketException: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    System.err.println("Client IOException: " + e.getMessage());
                    break;
                }
            }
            String receivedString = new String(stringBuilder.toByteArray(), StandardCharsets.UTF_8);
            System.out.println("Client: Received large string of " + receivedString.length() + " characters.");
            //Send the response
            try {
                bufferedWriter.write("data well received");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("Client: Response sent.");
            } catch (SocketException e){
                System.err.println("Client: Socket Exception : "+ e.getMessage());
            } catch (IOException e) {
                System.err.println("Client: IO Exception : "+ e.getMessage());
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Client: Read timeout: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Client: Connection error: " + e.getMessage());
        }
    }

    // Helper function to convert byte array (4 bytes) to int
    public static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }
}