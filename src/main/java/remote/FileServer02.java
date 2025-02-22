package remote;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer02 {
    private static final int PORT = 5000;
    private static final String FILE_PATH = "src/res/MP4/mountains.mp4";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                new Thread(() -> {
                    try {
                        sendFile(socket, FILE_PATH);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(Socket socket, String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            // Send file size first
            dos.writeLong(file.length());
            dos.flush();

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;

            System.out.println("Sending file: " + file.getName());

            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.flush();
            System.out.println("File sent successfully!");
        } finally {
            socket.close();
        }
    }
}
