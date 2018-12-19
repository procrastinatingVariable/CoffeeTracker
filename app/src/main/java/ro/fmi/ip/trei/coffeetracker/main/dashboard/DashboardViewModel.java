package ro.fmi.ip.trei.coffeetracker.main.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.common.BaseViewModel;
import ro.fmi.ip.trei.coffeetracker.data.RecordRepository;
import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;
import ro.fmi.ip.trei.coffeetracker.util.ModelMapper;

public class DashboardViewModel extends BaseViewModel {

    public final LiveData<List<Record>> data = new MutableLiveData<List<Record>>();
    private final MutableLiveData<List<Record>> mutableData = (MutableLiveData<List<Record>>)data;

    private RecordRepository recordRepository;

    public DashboardViewModel() {
        recordRepository = RecordRepository.getInstance();
    }

    public void fetchCondensedRecords() {
        recordRepository.loadRecords(new RecordRepository.LoadRecordsCallback() {
            @Override
            public void onRecordsLoaded(List<RecordEntity> records) {
                List<Record> mappedData = ModelMapper.mapList(records, ModelMapper::map);
                Collections.sort(mappedData, (o1, o2) -> {
                    long dif = o1.getRegistrationDate().getTime() - o2.getRegistrationDate().getTime();
                    if (dif < 0) {
                        return -1;
                    } else if (dif > 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                //TODO collect records of the same day
                mutableData.setValue(mappedData);
            }

            @Override
            public void onRecordsNotAvailable() {

            }
        });
    }

}
