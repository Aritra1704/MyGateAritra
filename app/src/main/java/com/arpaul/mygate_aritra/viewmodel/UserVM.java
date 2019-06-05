package com.arpaul.mygate_aritra.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.arpaul.mygate_aritra.models.User;
import com.arpaul.mygate_aritra.repo.UserRepo;
import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.StringUtils;

import java.util.Calendar;
import java.util.List;

import static com.arpaul.utilitieslib.CalendarUtils.DATE_FORMAT1;
import static com.arpaul.utilitieslib.CalendarUtils.DATE_TIME_FORMAT1;

public class UserVM extends AndroidViewModel {
    private UserRepo mRepository;
    private LiveData<List<User>> mAllUsers;

    /**
     * Never pass context to the instances.
     * @param application
     */
    public UserVM(Application application) {
        super(application);
        mRepository = new UserRepo(application);
        mAllUsers = mRepository.getUsers();
    }

    public LiveData<List<User>> getUsers() { return mAllUsers; }

    public void insert(User user) {
        user.setPasscode(createPasscode());
        mRepository.insert(user);
    }

    private String createPasscode() {
        StringBuilder strBuilder = new StringBuilder();
        String year = CalendarUtils.getDateinPattern("yyyy");
        strBuilder.append(year.substring(2,4));

        Calendar calendar = Calendar.getInstance();
        String timeinmil = calendar.getTimeInMillis() + "";
        strBuilder.append(timeinmil.substring(timeinmil.length() - 4, timeinmil.length()));

        return strBuilder.toString();

//        long epochTimeOfApp = CalendarUtils.getTimeinMilliesPattern("2019-01-01", DATE_FORMAT1);
//        long currentTimeinSec = CalendarUtils.getTimeinMilliesPattern(CalendarUtils.getDateinPattern(DATE_TIME_FORMAT1), DATE_TIME_FORMAT1);
//        long diff = (currentTimeinSec - epochTimeOfApp) / (1000);
//        Log.d("createPasscode>> ", "diff >> " + diff);
//
//        long val = Long.parseLong(diff + "", 8);
//        Log.d("createPasscode>> ", "val >> " + val);
//        String result = val + "";
//        if(val < 10000000)
//            result = "0"+result;
//        Log.d("createPasscode>> ", "result >> " + result);
//        return result;
    }
}
