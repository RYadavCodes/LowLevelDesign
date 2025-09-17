package ParkingSystemV1.Vehicle;

public class Car implements Vehicle {
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.CAR;
    }
}
