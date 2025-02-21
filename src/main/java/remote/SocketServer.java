package remote;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SocketServer {


    public static void main(String[] args) throws IOException {

        /*Gson gson = new Gson();
        JsonObject rootObject = new JsonObject();

        File folder = new File("MIDI California Vietnamese Vol20");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //System.out.println("File " + i + " " + listOfFiles[i].getName().replace(".mid"," "));
                String format = String.format("%06d", i); //replace(".mid"," "
                rootObject.addProperty(String.format(format, i), listOfFiles[i].getName().replace(".mid", " "));
            } else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        String jsonString = gson.toJson(rootObject);
        System.out.println(jsonString);*/

        /*String filePath = "songlist/songlist.json";
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write the JSON string to the file
            writer.write(jsonString);
            System.out.println("JSON written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        int port = 5000;
        int readTimeoutMillis = 10000;

        //connection01(port, jsonString);

        ServerSocket serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(readTimeoutMillis);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // Start a new thread to handle the client
            //new Thread(() -> handleClient(socket, jsonString)).start();
            new Thread(() -> handleClient(socket)).start();
        }


        /*String serverAddress = "192.168.0.172";
        int port = 5000;

        Socket socket = new Socket(serverAddress, port);
        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // The large string (replace with your actual string)
        String largeString = jsonString;// "This is a very large string..."; // Add many characters

        // Send in chunks (e.g., 4KB chunks)
        int chunkSize = 4096; // 4KB
        byte[] stringBytes = largeString.getBytes(StandardCharsets.UTF_8);
        int totalBytes = stringBytes.length;
        int offset = 0;

        while (offset < totalBytes) {
            int bytesToSend = Math.min(chunkSize, totalBytes - offset);
            byte[] chunk = new byte[bytesToSend];
            System.arraycopy(stringBytes, offset, chunk, 0, bytesToSend);

            // Send the length of the chunk first (4 bytes)
            byte[] lengthBytes = intToByteArray(bytesToSend);
            bufferedOutputStream.write(lengthBytes);
            // Send the chunk
            bufferedOutputStream.write(chunk);
            bufferedOutputStream.flush(); // Important!

            offset += bytesToSend;
        }
        //Send end of stream (-1).
        byte[] lengthBytes = intToByteArray(-1);
        bufferedOutputStream.write(lengthBytes);
        bufferedOutputStream.flush();
        socket.close();*/
    }

    // Helper function to convert int to byte array (4 bytes)
    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    private static void handleClient(Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream();
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // The path to the JSON file (replace with your actual file)
            /*String filePath = "songlist/songlist.json";
            File file = new File(filePath);
            if (!file.exists()) {
                try (PrintWriter writer = new PrintWriter(filePath)) {
                    writer.println("{\"name\":\"Jane Doe\",\"age\":25}");
                    System.out.println("File created");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/

            String filePath = "songlist/songlist1.json";
            File file = new File(filePath);
            if (!file.exists()) {
                try (PrintWriter writer = new PrintWriter(filePath)) {
                    for (int i = 0; i < 190000000; i++) {
                        writer.println("0123456789");
                    }
                    System.out.println("File created");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // Send in chunks directly from the file
            //int chunkSize = 120 * 1014; // 4KB
            int chunkSize = 8192;
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                byte[] chunk = new byte[chunkSize];
                int bytesRead;

                while ((bytesRead = bufferedInputStream.read(chunk)) != -1) {
                    // Send the length of the chunk first (4 bytes)
                    byte[] lengthBytes = intToByteArray(bytesRead);
                    try {
                        bufferedOutputStream.write(lengthBytes);
                        bufferedOutputStream.flush(); // Very important!
                    } catch (SocketException e) {
                        System.err.println("Socket write error: 1 " + e.getMessage());
                        return;
                    }

                    // Send the chunk
                    try {
                        bufferedOutputStream.write(chunk, 0, bytesRead);
                        bufferedOutputStream.flush(); // Very important!
                    } catch (SocketException e) {
                        System.err.println("Socket write error: 2 " + e.getMessage());
                        return;
                    }
                }

                // Send end of stream (-1).
                byte[] lengthBytes = intToByteArray(-1);
                /*bufferedOutputStream.write(lengthBytes);
                bufferedOutputStream.flush();*/
                try {
                    bufferedOutputStream.write(lengthBytes);
                    bufferedOutputStream.flush(); // Very important!
                } catch (SocketException e) {
                    System.err.println("Socket write error: 3 " + e.getMessage());
                    return;
                }
                //Receive the response
                /*String response = bufferedReader.readLine();
                System.out.println(response);*/
                try {
                    String response = bufferedReader.readLine();
                    System.out.println(response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Read timeout: 4 " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connection01(int port, String jsonString) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String message = in.readLine();
                    System.out.println("Received: " + message);

                    // Respond to client
                    //out.println("Hello, Client!");
                    out.println(jsonString);

                } catch (IOException e) {
                    System.out.println("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handleClient(Socket socket, String string) {
        try (OutputStream outputStream = socket.getOutputStream();
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // The large string to send (replace with your actual data source)
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 1000000000; i++) {
                //stringBuilder.append("This is a very large string to send from server. ");
                stringBuilder.append(string);
            }
            String largeString = stringBuilder.toString();

            // Send in chunks (e.g., 4KB chunks)
            int chunkSize = 4096; // 4KB
            byte[] stringBytes = largeString.getBytes(StandardCharsets.UTF_8);
            int totalBytes = stringBytes.length;
            int offset = 0;

            while (offset < totalBytes) {
                int bytesToSend = Math.min(chunkSize, totalBytes - offset);
                byte[] chunk = new byte[bytesToSend];
                System.arraycopy(stringBytes, offset, chunk, 0, bytesToSend);

                // Send the length of the chunk first (4 bytes)
                byte[] lengthBytes = intToByteArray(bytesToSend);
                bufferedOutputStream.write(lengthBytes);

                // Send the chunk
                bufferedOutputStream.write(chunk);
                bufferedOutputStream.flush(); // Very important!

                offset += bytesToSend;
            }
            // Send end of stream (-1).
            byte[] lengthBytes = intToByteArray(-1);
            bufferedOutputStream.write(lengthBytes);
            bufferedOutputStream.flush();
            //Receive the response
            String response = bufferedReader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
