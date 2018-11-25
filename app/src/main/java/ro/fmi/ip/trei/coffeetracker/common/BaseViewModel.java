package ro.fmi.ip.trei.coffeetracker.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ro.fmi.ip.trei.coffeetracker.util.SingleLiveEvent;

public abstract class BaseViewModel extends ViewModel {

    private SingleLiveEvent<Integer> currentScreenEvent = new SingleLiveEvent<>();

    public void setCurrentScreen(int screenId) {
        currentScreenEvent.setValue(screenId);
    }

    public LiveData<Integer> getCurrentScreenEvent() {
        return currentScreenEvent;
    }

}
