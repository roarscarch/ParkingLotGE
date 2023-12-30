package org.example;

import org.example.Exceptions.CarNotPresentException;
import org.example.Exceptions.ParkinglotFullException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    // Creating instances of various classes for testing
    ParkingLotOwner parkingLotOwner1 = new ParkingLotOwner("Anurag");
    ParkingLotOwner parkingLotOwner2= new ParkingLotOwner("Pokemon");
    ParkingLotOwner parkingLotOwner3 = new ParkingLotOwner("Doraemon");
    ParkingAttendant parkingAttendant1 = new ParkingAttendant("Rahul", 1);
    ParkingAttendant parkingAttendant2 = new ParkingAttendant("Satya Nadela", 1);
    ParkingAttendant parkingAttendant3 = new ParkingAttendant("ChinaMan", 1);
    ParkingLot parkingLot2 = new ParkingLot(2);
    ParkingLot parkingLot3 = new ParkingLot(45);
    ParkingLot parkingLot4 = new ParkingLot(12);
    ParkingLot parkingLot5 = new ParkingLot(7);
    ParkingLot parkingLot1 = new ParkingLot(2);
    Car car1 = new Car(675, "Blue", "CarCompany1", false, "Small");
    Car car2 = new Car(834003, "White", "CarCompany2", false, "Small");
    Car car3 = new Car(3152, "Pink", "BMW", false, "Small");
    Car car4 = new Car(2378, "White", "Tata", true, "Small");
    Car car5 = new Car(1285, "Yellow", "WagonR", false, "Large");
    Car car6 = new Car(87656, "Red", "Mazerati", false, "Large");
    Car car7 = new Car(45545, "Green", "Porsche", false, "Small");

    // Setting up initial conditions for tests
    public void setUp1() {// these functions are made so that we dont have to write the code again and again
        parkingLotOwner1.addAttendant(parkingAttendant1);
        parkingLotOwner1.addLot(parkingLot1);
        parkingLotOwner1.addLot(parkingLot2);
    }

    // Parking several cars for testing
    public void setUp2() {
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
            parkingAttendant1.parkCar(car3);
            parkingAttendant1.parkCar(car4);
        } catch (ParkinglotFullException e) {
            System.out.println("error aa gya bhai. galti se");
        }
    }

    // UC1: Test parking a car
    @Test
    public void testParkingCar() {
        try {
            parkingLot1.parkCar(car1, null);
            assertTrue(parkingLot1.checkCar(car1));
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }
    }

    // UC2: Test unparking a car
    @Test
    public void testUnparkingCar() {
        // Unparking parked car
        try {
            parkingLot1.parkCar(car1, null);
            parkingLot1.unparkCar(car1);
            assertFalse(parkingLot1.checkCar(car1));
        } catch (CarNotPresentException | ParkinglotFullException e) {
            fail("Exception was not expected here");
        }
        

        // Unparking unparked car
        try {
            parkingLot1.unparkCar(car2);
            fail("Exception was expected here");
        } catch (CarNotPresentException e) {
            assertEquals("Car not present in lot!", e.getMessage());
        }
    }

    // UC3: Check full status of the parking lot
    @Test
    public void checkFullStatus() {
        // Checking status when lot is not full
        try {
            parkingLot1.parkCar(car1, null);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        assertFalse(parkingLot1.getFullStatus());

        // Checking status when lot is full
        try {
            parkingLot1.parkCar(car2, null);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        assertTrue(parkingLot1.getFullStatus());
    }

    // UC4: Sharing data with airport security personnel
    @Test
    public void checkAirportStaffAction() {
        AirportSecurity officer1 = new AirportSecurity("Officer 1", 1);
        try {
            parkingLot1.parkCar(car1, null);
            parkingLot1.parkCar(car2, null);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected");
        }

        assertTrue(officer1.checkStatus(parkingLot1));
    }

    // UC5: Removing Full Status
    @Test
    public void checkRemoveFullStatus() {
        try {
            parkingLot1.parkCar(car1, null);
            parkingLot1.parkCar(car2, null);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected");
        }

        try {
            parkingLot1.unparkCar(car1);
        } catch (CarNotPresentException e) {
            fail("Exception was not expected here");
        }

        assertFalse(parkingLot1.getFullStatus());
    }

    // UC6: Parking Attendant parks car
    @Test
    public void testParkingAttendant() {
        parkingLotOwner1.addAttendant(parkingAttendant1);
        parkingLotOwner1.addLot(parkingLot1);

        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        assertTrue(parkingLot1.checkCar(car1));
        assertTrue(parkingLot1.checkCar(car2));
    }

    // UC7: Car owner wants to go home
    @Test
    public void testOwnerGoesHome() {
        setUp1();
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingAttendant1.unparkCar(car1);
        } catch (CarNotPresentException e) {
            fail("Exception was not expected here!");
        }

        assertFalse(parkingAttendant1.checkCar(car1));
    }

    // UC8: Calculate parking fare (Minimum fare of 10 minutes)
    @Test
    public void checkFair() {
        setUp1();
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingLotOwner1.addToAccount(car1, parkingAttendant1.unparkCar(car1));
        } catch (CarNotPresentException e) {
            fail("Exception was not expected here!");
        }

        assertEquals(10.0, parkingLotOwner1.getAccount().get(car1));
    }

    // UC9: Evenly distribute cars
    @Test
    public void checkEvenDistribution() {
        setUp1();
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
            parkingAttendant1.parkCar(car3);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        assertTrue(parkingLot2.checkCar(car3));
    }

    // UC10: Park Handicapped person's car
    @Test
    public void checkParkingHandicapped() {
        setUp1();
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
            parkingAttendant1.parkCar(car3);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingAttendant1.unparkCar(car2);
            parkingAttendant1.unparkCar(car3);
        } catch (CarNotPresentException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingAttendant1.parkCar(car4);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        assertTrue(parkingLot2.checkCar(car4));
    }

    // UC11: Try parking large car to its desired location
    @Test
    public void checkLargeTypeParking() {
        setUp1();
        try {
            parkingAttendant1.parkCar(car1);
            parkingAttendant1.parkCar(car2);
            parkingAttendant1.parkCar(car3);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingAttendant1.unparkCar(car2);
            parkingAttendant1.unparkCar(car3);
        } catch (CarNotPresentException e) {
            fail("Exception was not expected here");
        }

        try {
            parkingAttendant1.parkCar(car5);
        } catch (ParkinglotFullException e) {
            fail("Exception was not expected here");
        }
        assertTrue(parkingLot2.checkCar(car5));
    }

    // UC12: Get location of all white car
    @Test
    public void checkGetWhiteCarLocation() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedLocation = new ArrayList<>();
        expectedLocation.add("Parking Lot:- 0, Position:- 1");
        expectedLocation.add("Parking Lot:- 1, Position:- 1");
        assertEquals(policeDepartment1.locationOfWhiteCar(), expectedLocation);
    }

    // UC13: Get info of blue cars
    @Test
    public void checkGetBlueCarInfo() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedInfo = new ArrayList<>();
        expectedInfo.add("Parking Lot:- 0, Position:- 0, Car number:- 675, Attendant name:- Rahul");
        assertEquals(policeDepartment1.infoOfBlueCars(), expectedInfo);
    }

    // UC14: Get info of all BMW parked
    @Test
    public void checkGetBmwCarInfo() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedInfo = new ArrayList<>();
        expectedInfo.add("Parking Lot:- 1, Position:- 0, Car number:- 3152");
        assertEquals(policeDepartment1.infoOfBmwCars(), expectedInfo);
    }

    // UC15: Get info of cars parked in last 30 mins
    @Test
    public void checkGetInfoBasedOnTime() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedInfo = new ArrayList<>();
        expectedInfo.add("Parking Lot:- 0, Position:- 0, Car number:- 675");
        expectedInfo.add("Parking Lot:- 0, Position:- 1, Car number:- 834003");
        expectedInfo.add("Parking Lot:- 1, Position:- 0, Car number:- 3152");
        expectedInfo.add("Parking Lot:- 1, Position:- 1, Car number:- 2378");
        assertEquals(policeDepartment1.infoOfCarsBasedOnTime(), expectedInfo);
    }

    // UC16: Get info of small and handicapped
    @Test
    public void checkGetInfoOfSmallAndHandicapped() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedInfo = new ArrayList<>();
        expectedInfo.add("Parking Lot:- 1, Position:- 1, Car number:- 2378");
        assertEquals(policeDepartment1.infoOfSmallAndHandicappedCars(), expectedInfo);
    }

    // UC17: Get info of cars at a particular lot
    @Test
    public void checkGetInfoOfCarsInLot() {
        setUp1();
        setUp2();
        PoliceDepartment policeDepartment1 = new PoliceDepartment(1, parkingLotOwner1);
        List<String> expectedInfo = new ArrayList<>();
        expectedInfo.add("Position:- 0, Car number:- 675, Car colour:- Blue, Car company:- CarCompany1");
        expectedInfo.add("Position:- 1, Car number:- 834003, Car colour:- White, Car company:- CarCompany2");
        assertEquals(policeDepartment1.infoOfCarsInLot(parkingLot1), expectedInfo);
    }
}
