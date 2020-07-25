package com.example.shophere;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;

    private LiveData<List<User>> mAllUser;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUser = mRepository.getAllUser();
    }
    public void insert(User user) {
        mRepository.insert(user);
    }
    public void deleteAll() {
        mRepository.deleteAll();
    }
    public void deleteUser(User user) {
        mRepository.deleteUser(user);
    }
    LiveData<List<User>> getAllUser() {
        return mAllUser;
    }
}
