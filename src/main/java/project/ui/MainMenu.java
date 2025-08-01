package main.java.project.ui;

import main.java.project.model.Car;
import main.java.project.model.Customer;
import main.java.project.model.Rental;
import main.java.project.service.RentalService;
import main.java.project.util.CarNotAvailableException;
import main.java.project.util.CarNotFoundException;
import main.java.project.util.CustomerNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private RentalService rentalService = new RentalService();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("=== Welcome to Car Rental System ===");

        boolean exit = false;

        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addCar();
                        break;
                    case "2":
                        registerCustomer();
                        break;
                    case "3":
                        rentCar();
                        break;
                    case "4":
                        returnCar();
                        break;
                    case "5":
                        viewCars();
                        break;
                    case "6":
                        viewCustomers();
                        break;
                    case "7":
                        viewRentals();
                        break;
                    case "0":
                        System.out.println("Exiting... Goodbye!");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (CarNotAvailableException | CustomerNotFoundException | CarNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Car");
        System.out.println("2. Register Customer");
        System.out.println("3. Rent Car");
        System.out.println("4. Return Car");
        System.out.println("5. View All Cars");
        System.out.println("6. View All Customers");
        System.out.println("7. View All Rentals");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private void addCar() throws SQLException {
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine().trim();

        System.out.print("Enter Car Model: ");
        String model = scanner.nextLine().trim();

        rentalService.addCar(new Car(carId, model));
        System.out.println("Car added successfully.");
    }

    private void registerCustomer() throws SQLException {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine().trim();

        rentalService.addCustomer(new Customer(customerId, name));
        System.out.println("Customer registered successfully.");
    }

    private void rentCar() throws SQLException, CarNotAvailableException, CustomerNotFoundException, CarNotFoundException {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine().trim();

        System.out.print("Enter Number of Rental Days: ");
        int days = Integer.parseInt(scanner.nextLine().trim());

        String message = rentalService.rentCar(customerId, carId, days);
        System.out.println(message);
    }

    private void returnCar() throws SQLException, CarNotFoundException {
        System.out.print("Enter Car ID to return: ");
        String carId = scanner.nextLine().trim();

        String message = rentalService.returnCar(carId);
        System.out.println(message);
    }

    private void viewCars() throws SQLException {
        List<Car> cars = rentalService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("No cars found.");
        } else {
            System.out.println("\n=== Cars ===");
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    private void viewCustomers() throws SQLException {
        List<Customer> customers = rentalService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("\n=== Customers ===");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private void viewRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        if (rentals.isEmpty()) {
            System.out.println("No rentals found.");
        } else {
            System.out.println("\n=== Rentals ===");
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        }
    }
}
