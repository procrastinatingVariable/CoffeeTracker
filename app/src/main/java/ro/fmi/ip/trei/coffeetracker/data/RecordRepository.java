package ro.fmi.ip.trei.coffeetracker.data;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.callbacks.GetRecordsCallback;
import ro.fmi.ip.trei.coffeetracker.data.firebase.FirebaseRecordDatabaseHelper;
import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;

public class RecordRepository {

    private static volatile RecordRepository instance = null;


    private FirebaseRecordDatabaseHelper recordDatabaseHelper;
    private SessionManager sessionManager;

    private RecordRepository() {
        recordDatabaseHelper = FirebaseRecordDatabaseHelper.getInstance();
        sessionManager = SessionManager.getInstance();
    }


    public static RecordRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new RecordRepository();
                }
            }
        }

        return instance;
    }


    public void loadRecords(LoadRecordsCallback callback) {
        String currentUserPhoneNumber = sessionManager.getUser().getPhoneNumber();
        recordDatabaseHelper.getRecords(currentUserPhoneNumber, new GetRecordsCallback() {
            @Override
            public void onResult(List<RecordEntity> records) {
                if (records == null || records.isEmpty()) {
                    callback.onRecordsNotAvailable();
                }
                callback.onRecordsLoaded(records);
            }

            @Override
            public void onError() {
                callback.onRecordsNotAvailable();
            }
        });
    }

    public interface LoadRecordsCallback {
        void onRecordsLoaded(List<RecordEntity> records);
        void onRecordsNotAvailable();
    }

}
