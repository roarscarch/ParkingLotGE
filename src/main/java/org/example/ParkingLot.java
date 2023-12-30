package org.example;

import org.example.Exceptions.CarNotPresentException;
import org.example.Exceptions.ParkinglotFullException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
// parking lot class
public class ParkingLot {
    private int parkingLotCapacity;// it shows the total capacity of parking lot
    private Car[] lot;
    private int cnt;

    public ParkingLot(int parkingLotCapacity) {
        this.cnt = 0;
        this.parkingLotCapacity = parkingLotCapacity;
        this.lot = new Car[parkingLotCapacity];
    }

    private void occupyParkingSpace(Car car, String attendantName) {
        LocalTime inTime = LocalTime.now();// shows local time
        car.setInTime(inTime);
        car.setAttendantName(attendantName);

        for (int i = 0; i < parkingLotCapacity; i++) {
            if (lot[i] == null) {
                lot[i] = car;
                cnt++;
                break;
            }
        }
    }

    public void parkCar(Car car, String attendantName) throws ParkinglotFullException {
        if (cnt == parkingLotCapacity) {// whwn count of cars is same as capacity therefore no capacity
            throw new ParkinglotFullException("Parking lot is full!");
        }

        occupyParkingSpace(car, attendantName);
    }

    private int findCarIndex(Car car) throws CarNotPresentException {
        for (int i = 0; i < parkingLotCapacity; i++) {
            if (lot[i] == car) {
                return i;// if car is present return its index
            }
        }
        throw new CarNotPresentException("Car not present in lot!");
    }

    private void clearParkingSpace(int index) {
        lot[index] = null;
        cnt--;
    }

    public double unparkCar(Car car) throws CarNotPresentException {
        int carIndex = findCarIndex(car);
        clearParkingSpace(carIndex);

        LocalTime inTime = car.getInTime();
        LocalTime outTime = LocalTime.now();

        Duration duration = Duration.between(inTime, outTime);
        long minutes = Math.max(duration.toMinutes(), 10); // Minimum parking time is 10 minutes
        return 1.0 * minutes;
    }

    public boolean checkCar(Car car) {
        return Arrays.asList(lot).contains(car);
    }

    public boolean getFullStatus() {
        return cnt == parkingLotCapacity;// checks if parking lot is full or not
    }

    public Car[] getLot() {
        return lot.clone();
    }

    public int getCnt() {
        return cnt;
    }
}
