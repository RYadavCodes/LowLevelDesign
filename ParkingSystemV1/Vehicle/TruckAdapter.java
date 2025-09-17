package ParkingSystemV1.Vehicle;

public class TruckAdapter extends Truck{

    Vehicle vehicle;

    public TruckAdapter(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public VehicleType getVehicleType(){
        return vehicle.getVehicleType();
    }
}
