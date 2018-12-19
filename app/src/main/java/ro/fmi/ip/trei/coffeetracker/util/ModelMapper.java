package ro.fmi.ip.trei.coffeetracker.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.data.model.FirebaseUserEntity;
import ro.fmi.ip.trei.coffeetracker.data.model.RecordEntity;
import ro.fmi.ip.trei.coffeetracker.data.model.UserEntity;
import ro.fmi.ip.trei.coffeetracker.entry.model.Formats;
import ro.fmi.ip.trei.coffeetracker.main.model.Record;

public class ModelMapper {

    public static UserEntity map(ro.fmi.ip.trei.coffeetracker.entry.model.User user) {
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        String birthdateString = Formats.getDateFormat().format(user.getBirthDate());
        newUser.setBirthDate(birthdateString);

        return newUser;
    }

    public static UserEntity map(FirebaseUserEntity user) {
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        return newUser;
    }

    public static FirebaseUserEntity map(UserEntity user) {
        FirebaseUserEntity newUser = new FirebaseUserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        return newUser;
    }

    public static Record map(RecordEntity record) {
        return new Record(
                record.getDenumire(),
                record.getDozaj(),
                record.getCantitate(),
                new Date(record.getTimestamp()),
                record.getUrlImagine());
    }

    public static <T, MT> List<MT> mapList(List<T> list, Mapper<T, MT> mapper) {
        List<MT> mappedList = new ArrayList<>();
        for (T item : list) {
            mappedList.add(mapper.map(item));
        }

        return mappedList;
    }

    public interface Mapper<T, MT> {
        MT map(T value);
    }

}


