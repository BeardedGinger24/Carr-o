package com.example.carr_o.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.Spinner;

@Entity(tableName = "log_item")
public class Log {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "maintenance_date")
    private String date;

    @ColumnInfo(name = "maintenance_location")
    private String location;

    @ColumnInfo(name = "mileage")
    private int mileage;

    @ColumnInfo(name = "total_price")
    private double totalPrice;

    @ColumnInfo(name = "maintenance_type")
    private String maintType;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "status")
    private int status;

    public Log(@NonNull int id, String date, String location, int mileage, double totalPrice, String maintType, String notes, int status) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.mileage = mileage;
        this.totalPrice = totalPrice;
        this.maintType = maintType;
        this.notes = notes;
        this.status = status;
    }

    @Ignore
    public Log(String date, String location, int mileage, double totalPrice, String maintType, String notes, int status) {
        this.date = date;
        this.location = location;
        this.mileage = mileage;
        this.totalPrice = totalPrice;
        this.maintType = maintType;
        this.notes = notes;
        this.status = status;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMaintType() {
        return maintType;
    }

    public void setMaintType(String maintType) {
        this.maintType = maintType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
