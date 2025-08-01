package main.java.project.dao;

import main.java.project.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class customerDAO {

    // Add a new customer to the database
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers(customer_id, name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerId());
            ps.setString(2, customer.getName());
            ps.executeUpdate();
        }
    }

    // Retrieve a customer by their ID
    public Customer getCustomerById(String customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    return new Customer(customerId, name);
                }
            }
            return null;
        }
    }

    // Get all customers from the database
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String customerId = rs.getString("customer_id");
                String name = rs.getString("name");
                customers.add(new Customer(customerId, name));
            }
        }
        return customers;
    }

    // Update customer details
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name = ? WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCustomerId());
            ps.executeUpdate();
        }
    }

    // Delete a customer by ID
    public void deleteCustomer(String customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerId);
            ps.executeUpdate();
        }
    }
}
