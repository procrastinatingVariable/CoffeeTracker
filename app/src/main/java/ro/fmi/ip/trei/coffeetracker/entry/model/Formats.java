package ro.fmi.ip.trei.coffeetracker.entry.model;

import java.text.SimpleDateFormat;

public class Formats {

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }

    public static SimpleDateFormat getDayFormat() {
        return new SimpleDateFormat("dd/MM");
    }

}
