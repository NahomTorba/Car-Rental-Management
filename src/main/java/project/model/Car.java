package main.java.project.model;
import main.java.project.abstracts.Vehicle;
import main.java.project.interfaces.Rentable;
import main.java.project.interfaces.Returnable;

public class Car extends Vehicle implements Rentable, Returnable{

    public Car(String vehicleId, String model) {
        super(vehicleId, model);
    }

    @Override
    public void rentOut() {
        setAvailable(false);
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
    }
}
