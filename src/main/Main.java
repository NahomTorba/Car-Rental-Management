package main;

import main.java.project.ui.MainMenu;
import main.java.project.dao.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to database successfully!");

            // Start the Car Rental Management System
            MainMenu mainMenu = new MainMenu();
            mainMenu.start();

        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
