package org.example;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PoliceDepartment {
    private int policeOfficerId;
    private List<ParkingLot> parkingLotList;

    public PoliceDepartment(int policeOfficerId, ParkingLotOwner parkingLotOwner) {
        this.policeOfficerId = policeOfficerId;
        parkingLotList = parkingLotOwner.giveDataToPolice();
    }
    private interface CarConditionChecker {
        boolean checkCondition(Car car);

        String getAdditionalInfo(Car car);
    }

    private void addCarInfo(List<String> ans, int lotIndex, int position, Car car, String additionalInfo) {
        ans.add(String.format("Parking Lot:- %d, Position:- %d, Car number:- %s%s", lotIndex, position,
                car.getLicenseNumber(), additionalInfo));
    }

    private List<String> getInfoByColor(String color) {
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < parkingLotList.size(); i++) {
            Car[] currLot = parkingLotList.get(i).getLot();
            for (int j = 0; j < currLot.length; j++) {
                if (color.equals(currLot[j].getCarColor())) {
                    ans.add("Parking Lot:- " + i + ", Position:- " + j);
                }
            }
        }
        return ans;
    }

    private List<String> getInfoByCondition(CarConditionChecker checker) {
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < parkingLotList.size(); i++) {
            Car[] currLot = parkingLotList.get(i).getLot();
            for (int j = 0; j < currLot.length; j++) {
                if (checker.checkCondition(currLot[j])) {
                    addCarInfo(ans, i, j, currLot[j], checker.getAdditionalInfo(currLot[j]));
                }
            }
        }
        return ans;
    }

    public List<String> locationOfWhiteCar() {
        return getInfoByColor("White");
    }

    public List<String> infoOfBlueCars() {
        return getInfoByCondition(new CarConditionChecker() {
            @Override
            public boolean checkCondition(Car car) {
                return "Blue".equals(car.getCarColor());
            }

            @Override
            public String getAdditionalInfo(Car car) {
                return String.format(", Attendant name:- %s", car.getAttendantName());
            }
        });
    }

    public List<String> infoOfBmwCars() {
        return getInfoByCondition(new CarConditionChecker() {
            @Override
            public boolean checkCondition(Car car) {
                return "BMW".equals(car.getCarCompany());
            }

            @Override
            public String getAdditionalInfo(Car car) {
                return "";
            }
        });
    }

    public List<String> infoOfCarsBasedOnTime() {
        return getInfoByCondition(new CarConditionChecker() {
            @Override
            public boolean checkCondition(Car car) {
                Duration duration = Duration.between(LocalTime.now(), car.getInTime());
                long minutes = duration.toMinutes();
                return minutes <= 30;
            }

            @Override
            public String getAdditionalInfo(Car car) {
                return "";
            }
        });
    }

    public List<String> infoOfSmallAndHandicappedCars() {
        return getInfoByCondition(new CarConditionChecker() {
            @Override
            public boolean checkCondition(Car car) {
                return "Small".equals(car.getCarType()) && car.getHandicapStatus();
            }

            @Override
            public String getAdditionalInfo(Car car) {
                return "";
            }
        });
    }

    public List<String> infoOfCarsInLot(ParkingLot parkinglot) {
        List<String> ans = new ArrayList<>();
        Car[] currLot = parkinglot.getLot();
        for (int j = 0; j < currLot.length; j++) {
            ans.add(String.format("Position:- %d, Car number:- %s, Car colour:- %s, Car company:- %s",
                    j, currLot[j].getLicenseNumber(), currLot[j].getCarColor(), currLot[j].getCarCompany()));
        }
        return ans;
    }
}
