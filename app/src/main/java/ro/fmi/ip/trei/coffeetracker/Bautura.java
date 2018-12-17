package ro.fmi.ip.trei.coffeetracker;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Bautura {

    private String denumire, dozaj, urlImagine, oreDormite;


    public Bautura() {
    }

    public Bautura(String denumire, String dozaj, String urlImagine, String oreDormite) {
        this.denumire = denumire;
        this.dozaj = dozaj;
        this.urlImagine = urlImagine;
        this.oreDormite = oreDormite;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("denumire", denumire);
        result.put("dozaj", dozaj);
        result.put("urlImagine", urlImagine);
        result.put("oreDormite", oreDormite);
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

    public String getOreDormite() {
        return oreDormite;
    }

    public void setOreDormite(String oreDormite) {
        this.oreDormite = oreDormite;
    }

}
