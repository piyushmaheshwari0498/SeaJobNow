package com.example.seajobnow.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;
import com.example.seajobnow.utils.PatternClass;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    AppSharedPreference appSharedPreference;

    public HomeViewModel() {
        appSharedPreference = AppSharedPreference.getAppSharedPreference(new HomeFragment().getContext());
        mText = new MutableLiveData<>();
        String name  = "sea job now";
        String companyName = PatternClass.capitalizeWord(name);
        mText.setValue("Welcome,\n"+ companyName);
    }

    public LiveData<String> getText() {
        return mText;
    }
}