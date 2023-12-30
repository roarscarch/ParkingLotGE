package org.example;
import java.time.LocalTime;

public class Car {
    private int licenseNumber;
    private String carColor;
    private String carCompany;
    private LocalTime inTime;
    private boolean isHandicap;
    private String carType;
    private String attendantName;

    public Car(int licenseNumber, String carColor, String carCompany, boolean isHandicap, String carType){
        this.licenseNumber = licenseNumber;
        this.carColor = carColor;
        this.carCompany = carCompany;
        this.isHandicap = isHandicap;
        this.carType = carType;
    }

    public void setInTime(LocalTime inTime){
        this.inTime = inTime;
    }

    public LocalTime getInTime(){
        return inTime;
    }

    public boolean getHandicapStatus(){
        return isHandicap;
    }

    public String getCarType(){
        return carType;
    }



    public String getLicenseNumber(){
        return licenseNumber +"";
    }

    public String getCarColor(){
        return carColor;
    }

    public void setAttendantName(String name){
        this.attendantName = name;
    }

    public String getAttendantName(){
        return attendantName;
    }

    public String getCarCompany(){
        return carCompany;
    }

}

