package ro.fmi.ip.trei.coffeetracker.main.model;

import java.text.SimpleDateFormat;

public class Formats {

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy");
    }

    public static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat("hh:mm");
    }

}
