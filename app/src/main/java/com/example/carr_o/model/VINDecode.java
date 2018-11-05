package com.example.carr_o.model;

public class VINDecode {

    int year;
    String make;
    String model;
    String trim;
    String body_type;
    String transmission;
    String drivetrain;
    String engine;
    String doors;
    String tank_size;
    String std_seating;
    String highway_miles;
    String city_miles;

    public VINDecode(int year, String make, String model, String trim, String body_type, String transmission, String drivetrain, String engine, String doors, String tank_size, String std_seating, String highway_miles, String city_miles) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.trim = trim;
        this.body_type = body_type;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.engine = engine;
        this.doors = doors;
        this.tank_size = tank_size;
        this.std_seating = std_seating;
        this.highway_miles = highway_miles;
        this.city_miles = city_miles;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getTank_size() {
        return tank_size;
    }

    public void setTank_size(String tank_size) {
        this.tank_size = tank_size;
    }

    public String getStd_seating() {
        return std_seating;
    }

    public void setStd_seating(String std_seating) {
        this.std_seating = std_seating;
    }

    public String getHighway_miles() {
        return highway_miles;
    }

    public void setHighway_miles(String highway_miles) {
        this.highway_miles = highway_miles;
    }

    public String getCity_miles() {
        return city_miles;
    }

    public void setCity_miles(String city_miles) {
        this.city_miles = city_miles;
    }
}
