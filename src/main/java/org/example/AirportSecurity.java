package org.example;

public class AirportSecurity {
    private String staffName;
    private int staffId;

    public AirportSecurity(String staffName, int staffId){
        this.staffId = staffId;
        this.staffName = staffName;
    }

    public boolean checkStatus(ParkingLot parkingLot){
        return parkingLot.getFullStatus();
    }

}