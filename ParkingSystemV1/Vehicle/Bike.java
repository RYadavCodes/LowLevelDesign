package ParkingSystemV1.Vehicle;

public class Bike implements Vehicle {
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.BIKE;
    }
}
