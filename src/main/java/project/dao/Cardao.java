package dao;

import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class cardao {

    // Add a new car to the database
    public void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO cars(vehicle_id, model, is_available) VALUES (?, ?, ?)";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getVehicleId());
            ps.setString(2, car.getModel());
            ps.setBoolean(3, car.isAvailable());
            ps.executeUpdate();
        }
    }

    // Retrieve a car by its vehicle ID
    public Car getCarById(String vehicleId) throws SQLException {
        String sql = "SELECT * FROM cars WHERE vehicle_id = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String model = rs.getString("model");
                    boolean isAvailable = rs.getBoolean("is_available");
                    Car car = new Car(vehicleId, model);
                    if (!isAvailable) car.rentOut();
                    else car.returnVehicle();
                    return car;
                }
            }
            return null;
        }
    }

    // Retrieve all cars from the database
    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = dbconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String vehicleId = rs.getString("vehicle_id");
                String model = rs.getString("model");
                boolean isAvailable = rs.getBoolean("is_available");
                Car car = new Car(vehicleId, model);
                if (!isAvailable) car.rentOut();
                else car.returnVehicle();
                cars.add(car);
            }
        }
        return cars;
    }

    // Mark a car as rented (set available to false)
    public void rentCar(String vehicleId) throws SQLException {
        String sql = "UPDATE cars SET is_available = 0 WHERE vehicle_id = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleId);
            ps.executeUpdate();
        }
    }

    // Mark a car as returned (set available to true)
    public void returnCar(String vehicleId) throws SQLException {
        String sql = "UPDATE cars SET is_available = 1 WHERE vehicle_id = ?";
        try (Connection conn = dbconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleId);
            ps.executeUpdate();
        }
    }

    // Update car details
    public void updateCar(Car car)
