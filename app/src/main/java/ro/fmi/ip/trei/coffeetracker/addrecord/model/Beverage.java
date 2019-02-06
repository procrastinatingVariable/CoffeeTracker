package ro.fmi.ip.trei.coffeetracker.addrecord.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Beverage implements Parcelable {

    public final String imageUrl;
    public final String name;
    public final int caffeineConcentration;

    public Beverage(String imageUrl, String name, int caffeineConcentration) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.caffeineConcentration = caffeineConcentration;
    }

    protected Beverage(Parcel in) {
        imageUrl = in.readString();
        name = in.readString();
        caffeineConcentration = in.readInt();
    }

    public static final Creator<Beverage> CREATOR = new Creator<Beverage>() {
        @Override
        public Beverage createFromParcel(Parcel in) {
            return new Beverage(in);
        }

        @Override
        public Beverage[] newArray(int size) {
            return new Beverage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeInt(caffeineConcentration);
    }
}
