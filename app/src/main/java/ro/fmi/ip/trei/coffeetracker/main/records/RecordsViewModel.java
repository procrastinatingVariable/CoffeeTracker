package ro.fmi.ip.trei.coffeetracker.main.records;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.FirebaseError;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;
import ro.fmi.ip.trei.coffeetracker.data.RecordRepository;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;
import ro.fmi.ip.trei.coffeetracker.util.Resource;

public class RecordsViewModel extends ViewModel {

    private RecordRepository recordsRepository;

    private MutableLiveData<Resource<List<Record>, FirebaseError>> recordItemsMutable = new MutableLiveData<>();
    public final LiveData<Resource<List<Record>, FirebaseError>> recordItems = recordItemsMutable;

    public RecordsViewModel() {
        recordsRepository = RecordRepository.getInstance();
    }

    public void loadData() {
        recordItemsMutable.setValue(Resource.loading());
        recordsRepository.loadRecords(new RecordRepository.LoadRecordsCallback() {
            @Override
            public void onRecordsLoaded(List<RecordEntity> records) {
                recordItemsMutable.setValue(Resource.success(ModelMapper.mapList(records, ModelMapper::map)));
            }

            @Override
            public void onRecordsNotAvailable() {
            }
        });
    }

}
