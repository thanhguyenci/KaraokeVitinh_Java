package remote;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

public class SecureClient {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change if needed
        int serverPort = 8443; // Change if needed
        String trustStorePath = "src/main/java/remote/tomcatlocalhost10092024jdk17010.jks"; // Replace with your path
        String trustStorePassword = "password"; // Replace with your password

        try {
            // Load the truststore
            KeyStore trustStore = loadKeyStore(trustStorePath, trustStorePassword, "JKS");

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Create an SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            // Create an SSLSocketFactory
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Create an SSLSocket
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, serverPort);

            // Start the handshake
            sslSocket.startHandshake();

            // Send data to the server
            OutputStream outputStream = sslSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println("Hello from the secure client!");

            // Receive data from the server
            InputStream inputStream = sslSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Server response: " + line);
            }

            // Close the connection
            sslSocket.close();

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException | java.security.KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static KeyStore loadKeyStore(String keyStorePath, String keyStorePassword, String keyStoreType) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        try (InputStream keyStoreStream = new FileInputStream(keyStorePath)) {
            keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
        }
        return keyStore;
    }
}