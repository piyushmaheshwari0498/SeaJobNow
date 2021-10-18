package com.example.seajobnow.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    AppSharedPreference appSharedPreference;

    public HomeViewModel() {
        appSharedPreference = AppSharedPreference.getAppSharedPreference(new HomeFragment().getContext());
        mText = new MutableLiveData<>();
        mText.setValue("Welcome,\n"+ appSharedPreference.getString(Constants.INTENT_KEYS.KEY_COMPANY_NAME));
    }

    public LiveData<String> getText() {
        return mText;
    }
}