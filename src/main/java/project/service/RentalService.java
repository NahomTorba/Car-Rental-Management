package main.java.project.service;

import main.java.project.dao.CarDAO;
import main.java.project.dao.customerDAO;
import main.java.project.model.Car;
import main.java.project.model.Customer;
import main.java.project.model.Rental;
import main.java.project.util.CarNotAvailableException;
import main.java.project.util.CarNotFoundException;
import main.java.project.util.CustomerNotFoundException;
import main.java.project.util.FileLogger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private CarDAO carDAO = new CarDAO();
    private customerDAO customerDAO = new customerDAO();

    private List<Rental> rentals = new ArrayList<>();

    public void addCar(Car car) throws SQLException {
        carDAO.addCar(car);
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    public String rentCar(String customerId, String carId, int days)
            throws CustomerNotFoundException, CarNotFoundException, CarNotAvailableException, SQLException {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        Car car = carDAO.getCarById(carId);
        if (car == null) {
            throw new CarNotFoundException("Car with ID " + carId + " not found.");
        }

        if (!car.isAvailable()) {
            throw new CarNotAvailableException("Car " + car.getModel() + " is not available.");
        }

        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive.");
        }

        car.rentOut();
        carDAO.updateCar(car);

        Rental rental = new Rental(car, customer, days);
        rentals.add(rental);

        FileLogger.log("Customer " + customerId + " rented car " + carId + " for " + days + " days.");

        return "Rental successful: " + rental.toString();
    }

    public String returnCar(String carId) throws CarNotFoundException, SQLException {
        Car car = carDAO.getCarById(carId);
        if (car == null) {
            throw new CarNotFoundException("Car with ID " + carId + " not found.");
        }
        if (car.isAvailable()) {
            return "Car is already available.";
        }
        car.returnVehicle();
        carDAO.updateCar(car);

        FileLogger.log("Car " + carId + " returned.");

        return "Car returned successfully.";
    }

    public List<Car> getAllCars() throws SQLException {
        return carDAO.getAllCars();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }
}
