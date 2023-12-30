package org.example;
/*
what things will parking attendant have?
name , id , and a list of parkinglot

*/

import org.example.Exceptions.CarNotPresentException;
import org.example.Exceptions.ParkinglotFullException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParkingAttendant {
    private String parkingAttendantName;
    private int parkingAttendantId;
    private List<ParkingLot> parkingLotList;

    public ParkingAttendant(String parkingAttendantName, int parkingAttendantId) {
        this.parkingAttendantName = parkingAttendantName;
        this.parkingAttendantId = parkingAttendantId;
        this.parkingLotList = new ArrayList<>();
    }

    public void parkCar(Car car) throws ParkinglotFullException {
        if (car.getHandicapStatus()) {
            parkCarBasedOnCriteria(car, this::findNearestLotForHandicap);
        } else if ("Large".equals(car.getCarType())) {
            parkCarBasedOnCriteria(car, this::findLotWithLeastOccupancy);
        } else {
            parkCarBasedOnCriteria(car, this::findAvailableLot);
        }
    }

    private void parkCarBasedOnCriteria(Car car, ParkingLotFinder finder) throws ParkinglotFullException {
        ParkingLot targetLot = finder.findLot();

        if (targetLot != null) {
            targetLot.parkCar(car, parkingAttendantName);
        } else {
            throw new ParkinglotFullException("No available parking lot");
        }
    }

    private interface ParkingLotFinder {
        ParkingLot findLot();
    }

    private ParkingLot findNearestLotForHandicap() {
        int nearest = Integer.MAX_VALUE;
        ParkingLot targetLot = null;

        for (ParkingLot lot : parkingLotList) {
            Car[] currLot = lot.getLot();
            for (int j = 0; j < currLot.length; j++) {
                if (currLot[j] == null && j < nearest) {
                    nearest = j;
                    targetLot = lot;
                    break;
                }
            }
        }

        return targetLot;
    }

    private ParkingLot findLotWithLeastOccupancy() {
        return parkingLotList.stream()
                .min(Comparator.comparingInt(ParkingLot::getCnt))
                .orElse(null);
    }

    private ParkingLot findAvailableLot() {
        return parkingLotList.stream()
                .filter(lot -> !lot.getFullStatus())
                .findFirst()
                .orElse(null);
    }

    public double unparkCar(Car car) throws CarNotPresentException {
        for (ParkingLot lot : parkingLotList) {
            if (lot.checkCar(car)) {
                return lot.unparkCar(car);
            }
        }
        throw new CarNotPresentException("Car not present in any lot");
    }

    public boolean checkCar(Car car) {
        return parkingLotList.stream().anyMatch(lot -> lot.checkCar(car));
    }

    public void allotAttendantLots(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
    }
}
