package filelist;

import java.io.File;

public class FileLister {
    public static void main(String[] args) {
        // Specify the folder path
        String folderPath = "MIDI California Vietnamese Vol20"; // Replace with the desired folder path

        // Create a File object for the folder
        File folder = new File(folderPath);

        // Check if the folder exists and is indeed a directory
        if (folder.exists() && folder.isDirectory()) {
            // Retrieve the list of files in the folder
            File[] files = folder.listFiles();

            if (files != null && files.length > 0) {
                System.out.println("Files in the folder:");
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println("File: " + file.getName());
                    } else if (file.isDirectory()) {
                        System.out.println("Directory: " + file.getName());
                    }
                }
            } else {
                System.out.println("The folder is empty.");
            }
        } else {
            System.out.println("The specified path is not a valid directory: " + folderPath);
        }
    }
}