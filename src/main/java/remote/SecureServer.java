package remote;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class SecureServer {

    public static void main(String[] args) {
        int port = 8443; // Change if needed
        String keyStorePath = "src/main/java/remote/tomcatlocalhost10092024jdk17010.p12"; // Replace with your path
        String keyStorePassword = "password"; // Replace with your password
        String trustStorePath = "src/main/java/remote/tomcatlocalhost10092024jdk17010.jks"; // Replace with your path
        String trustStorePassword = "password"; // Replace with your password

        try {
            // Load the server's keystore
            KeyStore serverKeyStore = loadKeyStore(keyStorePath, keyStorePassword, "PKCS12");

            // Load the server's truststore
            KeyStore serverTrustStore = loadKeyStore(trustStorePath, trustStorePassword, "JKS");

            // Initialize KeyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(serverKeyStore, keyStorePassword.toCharArray());

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(serverTrustStore);

            // Create an SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            // Create an SSLServerSocketFactory
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

            // Create an SSLServerSocket
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
            System.out.println("Secure server started on port " + port);

            while (true) {
                // Accept a connection
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                System.out.println("Client connected: " + sslSocket.getInetAddress());

                // Handle the connection in a new thread
                new Thread(() -> handleConnection(sslSocket)).start();
            }
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(SSLSocket sslSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(sslSocket.getOutputStream(), true)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received from client: " + line);
                writer.println("Server received: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sslSocket.close();
                System.out.println("Client disconnected: " + sslSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
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