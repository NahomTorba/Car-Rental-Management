package model;

public class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    // Overloading: no discount
    public double calculateCost() {
        double ratePerDay = 500.0;
        return ratePerDay * days;
    }

    // Overloading: with discount percent
    public double calculateCost(double discountPercent) {
        double total = calculateCost();
        return total - (total * discountPercent / 100);
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return customer.getName() + " rented " + car.getModel() + " for " + days + " day(s). Cost: $" + calculateCost();
    }
}
