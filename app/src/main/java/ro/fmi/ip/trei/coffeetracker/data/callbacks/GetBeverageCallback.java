package ro.fmi.ip.trei.coffeetracker.data.callbacks;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.BeverageEntity;

public interface GetBeverageCallback {
    void onResult(List<BeverageEntity> beverages);
    void onFailure();
}
