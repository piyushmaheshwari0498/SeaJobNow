package com.example.seajobnow.ui.plans;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnow.databinding.MyPlansFragmentBinding;
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;

public class MyPlans extends Fragment {

    MyPlansFragmentBinding binding;
    AppSharedPreference appSharedPreference;

    public static MyPlans newInstance() {
        return new MyPlans();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MyPlansFragmentBinding.inflate(inflater, container, false);
        appSharedPreference = AppSharedPreference.getAppSharedPreference(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(appSharedPreference.getString(Constants.INTENT_KEYS.KEY_COMPANY_NAME));

        return binding.getRoot();
    }

}