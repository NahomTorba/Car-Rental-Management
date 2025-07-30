package main.java.project.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogger {
    private static final String LOG_FILE = "rental_log.txt";

    public static void log(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timeStampedMessage = LocalDateTime.now() + " - " + message;
            bw.write(timeStampedMessage);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Logging failed: " + e.getMessage());
        }
    }
}
