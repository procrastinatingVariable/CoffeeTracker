package ro.fmi.ip.trei.coffeetracker.data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;

public class RecordsRepository {

    private static RecordsRepository instance = null;

    private RecordsRepository() {
    }

    public static RecordsRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new RecordsRepository();
                }
            }
        }

        return instance;
    }

    public void loadRecords(LoadRecordsCallback callback) {
        List<RecordEntity> data = Arrays.asList(
                new RecordEntity("Coffee 1", 2.56, new Date()),
                new RecordEntity("Coffee 2", 2.4, new Date()),
                new RecordEntity("Coffee 3", 1.0, new Date()),
                new RecordEntity("Coffee 4", 0.5, new Date())
        );
        callback.onRecordsLoaded(data);
    }

    public interface LoadRecordsCallback {
        void onRecordsLoaded(List<RecordEntity> records);
        void onRecordsNotAvailable();
    }

}
