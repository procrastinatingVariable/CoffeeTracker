package ro.fmi.ip.trei.coffeetracker.addcaffeine;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Beverage {

    private String denumire, dozaj, urlImagine;

    public Beverage() {
    }

    public Beverage(String denumire, String dozaj, String urlImagine) {
        this.denumire = denumire;
        this.dozaj = dozaj;
        this.urlImagine = urlImagine;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("denumire", denumire);
        result.put("dozaj", dozaj);
        result.put("urlImagine", urlImagine);
        return result;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDozaj() {
        return dozaj;
    }

    public void setDozaj(String dozaj) {
        this.dozaj = dozaj;
    }

    public String getUrlImagine() {
        return urlImagine;
    }

    public void setUrlImagine(String urlImagine) {
        this.urlImagine = urlImagine;
    }
}
