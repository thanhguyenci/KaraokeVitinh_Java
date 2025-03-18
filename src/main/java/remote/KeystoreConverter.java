package remote;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.security.Key;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Iterator;

public class KeystoreConverter {

    public static void main(String[] args) {
        String sourceKeystoreFile = "src/main/java/remote/tomcatlocalhost10092024jdk17010.p12"; // Replace with your source keystore file
        String sourceKeystoreType = "PKCS12"; // Replace with the type of your source keystore
        String sourceKeystorePassword = "password"; // Replace with your source keystore password
        String destinationKeystoreFile = "src/main/java/remote/tomcatlocalhost10092024jdk17010.jks"; // Replace with your destination keystore file
        String destinationKeystorePassword = "password"; // Replace with your destination keystore password

        convertKeystore(sourceKeystoreFile, sourceKeystoreType, sourceKeystorePassword, destinationKeystoreFile, destinationKeystorePassword);
    }

    public static void convertKeystore(String sourceKeystoreFile, String sourceKeystoreType, String sourceKeystorePassword, String destinationKeystoreFile, String destinationKeystorePassword) {
        try {
            // 1. Load the source keystore
            KeyStore sourceKeystore = KeyStore.getInstance(sourceKeystoreType);
            FileInputStream sourceKeystoreStream = new FileInputStream(sourceKeystoreFile);
            sourceKeystore.load(sourceKeystoreStream, sourceKeystorePassword.toCharArray());
            sourceKeystoreStream.close();

            // 2. Create a destination keystore (JKS)
            KeyStore destinationKeystore = KeyStore.getInstance("JKS");
            destinationKeystore.load(null, null); // Create an empty keystore

            // 3. Iterate and copy entries
            Enumeration<String> enumeration = sourceKeystore.aliases();
            Iterator<String> iterator = enumeration.asIterator();
            while (iterator.hasNext()){
                String alias = iterator.next();
                if (sourceKeystore.isCertificateEntry(alias)) {
                    Certificate cert = sourceKeystore.getCertificate(alias);
                    destinationKeystore.setCertificateEntry(alias, cert);
                }
                if (sourceKeystore.isKeyEntry(alias)) {
                    Key key = sourceKeystore.getKey(alias, sourceKeystorePassword.toCharArray());
                    Certificate[] certChain = sourceKeystore.getCertificateChain(alias);
                    destinationKeystore.setKeyEntry(alias, key, destinationKeystorePassword.toCharArray(), certChain);
                }
            }

            // 4. Save the destination keystore
            FileOutputStream destinationKeystoreStream = new FileOutputStream(destinationKeystoreFile);
            destinationKeystore.store(destinationKeystoreStream, destinationKeystorePassword.toCharArray());
            destinationKeystoreStream.close();

            System.out.println("Keystore converted successfully from " + sourceKeystoreType + " to JKS.");

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}