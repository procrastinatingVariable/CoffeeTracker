package ro.fmi.ip.trei.coffeetracker.main.model;

import java.util.Date;

public class Record {

    private String name;
    private double quantity;
    private Date registrationDate;

    public Record(String name, double quantity, Date registrationDate) {
        this.name = name;
        this.quantity = quantity;
        this.registrationDate = registrationDate;
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
}
