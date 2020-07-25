package com.example.shophere;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUser;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getInstance(application);
        mUserDao = db.userDao();
        mAllUser = mUserDao.getAllUser();
    }
    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }
    public void deleteAll() {
        new deleteAllWordsAsyncTask(mUserDao).execute();
    }
    public void deleteUser(User user) {
        new deleteWordAsyncTask(mUserDao).execute(user);
    }
    public LiveData<List<User>> getAllUser() {
        return mAllUser;
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        private insertAsyncTask(UserDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao mAsyncTaskDao;

        private deleteAllWordsAsyncTask(UserDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class deleteWordAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        private deleteWordAsyncTask(UserDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDao.deleteUser(users[0]);
            return null;
        }
    }
}
