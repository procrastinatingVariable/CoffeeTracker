package ro.fmi.ip.trei.coffeetracker.data.callbacks;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;
import ro.fmi.ip.trei.coffeetracker.data.model.ScoreEntity;

public interface GetScoresCallback {
    void onResult(List<ScoreEntity> records);
    void onError();
}
