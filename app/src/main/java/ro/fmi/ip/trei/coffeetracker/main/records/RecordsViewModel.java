package ro.fmi.ip.trei.coffeetracker.main.records;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;
import ro.fmi.ip.trei.coffeetracker.data.RecordsRepository;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;

public class RecordsViewModel extends ViewModel {

    private RecordsRepository recordsRepository;

    private MutableLiveData<List<Record>> recordItemsMutable = new MutableLiveData<>();
    public final LiveData<List<Record>> recordItems = recordItemsMutable;

    public RecordsViewModel() {
        recordsRepository = RecordsRepository.getInstance();
    }

    public void loadData() {
        recordsRepository.loadRecords(new RecordsRepository.LoadRecordsCallback() {
            @Override
            public void onRecordsLoaded(List<RecordEntity> records) {
                recordItemsMutable.setValue(ModelMapper.mapList(records, ModelMapper::map));
            }

            @Override
            public void onRecordsNotAvailable() {

            }
        });
    }

}
