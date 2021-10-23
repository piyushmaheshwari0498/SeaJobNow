package com.example.seajobnow.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seajobnow.R;
import com.example.seajobnow.databinding.ActivityPostDetailsBinding;
import com.example.seajobnow.utils.InternetConnection;

public class PostDetails_Activity extends AppCompatActivity {

    InternetConnection internetConnection;
    ActivityPostDetailsBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LayoutInflater inflater = getLayoutInflater();

        internetConnection = new InternetConnection();


        binding.toolbarLayout.setTitle(getIntent().getStringExtra("post_title"));

        TextView textView = findViewById(R.id.job_title);

    }
}