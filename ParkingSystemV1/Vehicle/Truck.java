package ParkingSystemV1.Vehicle;

public class Truck implements Vehicle {
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TRUCK;
    }
}
