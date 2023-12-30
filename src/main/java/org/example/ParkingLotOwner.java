package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotOwner {
    private String lotOwnerName;
    private List<ParkingLot> lots;
    private List<ParkingAttendant> parkingAttendants;
    private Map<Car, Double> account;

    public ParkingLotOwner(String lotOwnerName) {
        this.lotOwnerName = lotOwnerName;
        this.lots = new ArrayList<>();
        this.parkingAttendants = new ArrayList<>();
        this.account = new HashMap<>();
    }

    public void addAttendant(ParkingAttendant parkingAttendant) {
        parkingAttendants.add(parkingAttendant);
    }

    public void addLot(ParkingLot parkingLot) {
        lots.add(parkingLot);
        parkingAttendants.forEach(attendant -> attendant.allotAttendantLots(parkingLot));
    }

    public void addToAccount(Car car, double fare) {
        account.put(car, account.getOrDefault(car, 0.0) + fare);
    }

    public Map<Car, Double> getAccount() {
        return account;
    }

    public List<ParkingLot> giveDataToPolice() {
        return new ArrayList<>(lots);
    }
}
