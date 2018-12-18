package ro.fmi.ip.trei.coffeetracker.data.model;

public class RecordEntity {

    private String denumire;
    private double dozaj;
    private long timestamp;
    private String urlImagine;

    public RecordEntity() {

    }

    public RecordEntity(String name, double quantity, long timestamp, String urlImage) {
        this.denumire = name;
        this.dozaj = quantity;
        this.timestamp = timestamp;
        this.urlImagine = urlImage;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getDozaj() {
        return dozaj;
    }

    public void setDozaj(float dozaj) {
        this.dozaj = dozaj;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrlImagine() {
        return urlImagine;
    }

    public void setUrlImagine(String urlImagine) {
        this.urlImagine = urlImagine;
    }
}
