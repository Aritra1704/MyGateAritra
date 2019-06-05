package com.arpaul.mygate_aritra.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.arpaul.mygate_aritra.daos.UserDao;
import com.arpaul.mygate_aritra.dataaccess.UserRoomDB;
import com.arpaul.mygate_aritra.models.User;

import java.util.List;

public class UserRepo {
    private UserDao userDAO;
    private LiveData<List<User>> mUsers;

    public UserRepo(Application application) {
        UserRoomDB db = UserRoomDB.getDatabase(application);
        userDAO = db.userDao();
        mUsers = userDAO.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    /**
     * Perfom in a non-UI thread
     */
    public void insert(User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
