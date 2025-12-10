package sample;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestStorage {

    public static void saveRequestToFile(String requestId, String userName, String userId,
                                         String userRole, String department, String serviceCategory,
                                         String description, String urgency) {
        try {
            // Create requests folder in current directory
            String currentDir = System.getProperty("user.dir");
            File requestsFolder = new File(currentDir + "/requests");
            if (!requestsFolder.exists()) {
                requestsFolder.mkdir();
            }

            // Create the file
            String filePath = currentDir + "/requests/" + requestId + ".txt";
            FileWriter writer = new FileWriter(filePath);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // ============================================
            // THIS IS WHAT GETS SAVED TO FILE INSTEAD OF CONSOLE
            // ============================================
            writer.write("=".repeat(50) + "\n");
            writer.write("      UNIVERSITY SERVICE REQUEST SYSTEM\n");
            writer.write("=".repeat(50) + "\n\n");

            writer.write("REQUEST CONFIRMATION\n");
            writer.write("-".repeat(30) + "\n");
            writer.write("Request ID: " + requestId + "\n");
            writer.write("Submission Time: " + timestamp + "\n\n");

            writer.write("USER INFORMATION\n");
            writer.write("-".repeat(30) + "\n");
            writer.write("Name: " + userName + "\n");
            writer.write("ID: " + userId + "\n");
            writer.write("Role: " + userRole + "\n");
            writer.write("Department: " + department + "\n\n");

            writer.write("REQUEST DETAILS\n");
            writer.write("-".repeat(30) + "\n");
            writer.write("Service Category: " + serviceCategory + "\n");
            writer.write("Urgency Level: " + urgency + "\n");
            writer.write("Status: Pending\n\n");

            writer.write("DESCRIPTION\n");
            writer.write("-".repeat(30) + "\n");
            writer.write(description + "\n\n");

            writer.write("=".repeat(50) + "\n");
            writer.write("Thank you for using our service system.\n");
            writer.write("Your request has been recorded.\n");
            writer.write("=".repeat(50) + "\n");
            // ============================================

            writer.close();

            System.out.println("✅ Request saved to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error saving request file: " + e.getMessage());
        }
    }
}