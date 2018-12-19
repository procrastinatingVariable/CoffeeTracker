package ro.fmi.ip.trei.coffeetracker.addrecord;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Bautura {

    private String denumire;
    private double dozaj;
    private double cantitate;
    private String urlImagine;
    private long timestamp;
    private double oreDormite;


    public Bautura() {
    }

    public Bautura(String denumire, double dozaj, double cantitate, String urlImagine, long oraInregistrare, double oreDormite) {
        this.denumire = denumire;
        this.dozaj = dozaj;
        this.urlImagine = urlImagine;
        this.oreDormite = oreDormite;
        this.timestamp = oraInregistrare;
        this.cantitate = cantitate;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("denumire", denumire);
        result.put("dozaj", dozaj);
        result.put("cantitate", cantitate);
        result.put("urlImagine", urlImagine);
        result.put("oreDormite", oreDormite);
        result.put("timestamp", timestamp);
        return result;
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

    public void setDozaj(double dozaj) {
        this.dozaj = dozaj;
    }

    public String getUrlImagine() {
        return urlImagine;
    }

    public void setUrlImagine(String urlImagine) {
        this.urlImagine = urlImagine;
    }

    public double getOreDormite() {
        return oreDormite;
    }

    public void setOreDormite(double oreDormite) {
        this.oreDormite = oreDormite;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public double getCantitate() {
        return this.cantitate;
    }
}
