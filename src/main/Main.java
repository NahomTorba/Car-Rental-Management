package main;

import main.java.project.dao.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
