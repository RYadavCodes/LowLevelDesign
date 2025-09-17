package ParkingSystemV1.Vehicle;

public class CarAdapter extends Car{
    Vehicle vehicle;
    public CarAdapter(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    @Override
    public VehicleType getVehicleType(){
        return vehicle.getVehicleType();
    }
}
