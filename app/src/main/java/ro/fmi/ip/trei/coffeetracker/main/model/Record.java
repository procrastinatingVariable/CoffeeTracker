package ro.fmi.ip.trei.coffeetracker.main.model;

import java.util.Date;

public class Record {

    private String name;
    private double quantity;
    private double dosage;
    private Date registrationDate;
    private String imageUrl;

    public Record(String name, double quantity, Date registrationDate) {
        this.name = name;
        this.quantity = quantity;
        this.registrationDate = registrationDate;
        this.imageUrl = "";
    }

    public Record(String name, double quantity, Date registrationDate, String imageUrl) {
        this(name, quantity, registrationDate);
        this.imageUrl = imageUrl;
    }

    public Record(String name, double quantity, double dosage, Date registrationDate, String imageUrl) {
        this(name, quantity, registrationDate, imageUrl);
        this.dosage = dosage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }
}
