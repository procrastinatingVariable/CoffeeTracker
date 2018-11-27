package ro.fmi.ip.trei.coffeetracker.data.callbacks;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;

public interface GetRecordsCallback {
    void onResult(List<RecordEntity> records);
    void onError();
}
