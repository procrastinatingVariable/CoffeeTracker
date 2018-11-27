package ro.fmi.ip.trei.coffeetracker.util;

import android.support.annotation.NonNull;

public class Resource<T, ET> {

    public enum Status {ERROR, LOADING, SUCCESS}

    @NonNull
    public final Status status;

    @NonNull
    public final T data;

    @NonNull
    public final ET error;

    private Resource(@NonNull Status status, @NonNull T data, @NonNull ET error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T, ET> Resource<T, ET> success(@NonNull T data) {
        return new Resource<T, ET>(Status.SUCCESS, data, null);
    }

    public static <T, ET> Resource<T, ET> loading() {
        return new Resource<T, ET>(Status.LOADING, null, null);
    }

    public static <T, ET> Resource<T, ET> error(@NonNull ET error) {
        return new Resource<T, ET>(Status.ERROR, null, error);
    }


}
