package abstracts;

public abstract class Vehicle {
    private String vehicleId;
    private String model;
    private boolean isAvailable;

    public Vehicle(String vehicleId, String model, boolean isAvailable) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.isAvailable = true;
    }
    //getters
    public String getVehicleId() {
        return vehicleId;
    }
    public String getModel() {
        return model;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    //setter for available other fields are immutable
    protected void setAvailable(boolean available) {
        isAvailable = available;
    }

    //methods
    public abstract void rentOut();
    public abstract void returnVehicle();

    @Override
    public String toString() {
        return vehicleId + " - " + model + " - " + (isAvailable ? "available" : "not available");
    }
}
