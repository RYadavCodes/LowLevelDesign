package ParkingSystemV1.Slot;

import ParkingSystemV1.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class Slot {
    List<Vehicle> vehicles;
    public Slot() {
        this.vehicles = new ArrayList<>();
    }
    abstract void park(Vehicle vehicle);
    abstract void unpark(Vehicle vehicle);
}
