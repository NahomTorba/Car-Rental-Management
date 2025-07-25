package model;
import abstracts.Vehicle;
import interfaces.Rentable;
import interfaces.Returnable;

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
