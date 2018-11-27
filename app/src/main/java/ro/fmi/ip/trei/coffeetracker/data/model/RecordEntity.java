package ro.fmi.ip.trei.coffeetracker.data.model;

import java.util.Date;

public class RecordEntity {

    private String name;
    private double quantity;
    private long timestamp;

    public RecordEntity() {

    }

    public RecordEntity(String name, double quantity, long timestamp) {
        this.name = name;
        this.quantity = quantity;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
