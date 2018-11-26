package ro.fmi.ip.trei.coffeetracker.data.model;

import java.util.Date;

public class RecordEntity {

    private String name;
    private double quantity;
    private Date registrationDate;

    public RecordEntity(String name, double quantity, Date registrationDate) {
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
