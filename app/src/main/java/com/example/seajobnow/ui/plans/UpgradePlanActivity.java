package com.example.seajobnow.ui.plans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.seajobnow.ApiEntity.request.PlanMasterRequest;
import com.example.seajobnow.R;
import com.example.seajobnow.adapters.HomePostAdapter;
import com.example.seajobnow.adapters.PlanMasterAdapter;
import com.example.seajobnow.databinding.ActivityLoginBinding;
import com.example.seajobnow.databinding.ActivityUpgradePlanBinding;
import com.example.seajobnow.model.HomePost;

import java.util.ArrayList;

public class UpgradePlanActivity extends AppCompatActivity {

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ActivityUpgradePlanBinding binding;

    ArrayList<PlanMasterRequest> source;
    PlanMasterAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpgradePlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerViewLayoutManager = new LinearLayoutManager(this);

        // Set LayoutManager on Recycler View
        binding.rvPlans.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvPlans.setLayoutManager(HorizontalLayout);
        // Set adapter on recycler view
        AddItemsToRecyclerViewArrayList();

        // calling constructor of adapter
        // with source list as a parameter
        adapter = new PlanMasterAdapter(this,source);
        binding.rvPlans.setAdapter(adapter);
    }

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PlanMasterRequest("Basic Plan","12 Months", R.color.blue_200));
        source.add(new PlanMasterRequest("Standard Plan","12 Months", R.color.info_color));
        source.add(new PlanMasterRequest("Premium Plan","12 Months + 1 Month", R.color.green_400));
    }
}