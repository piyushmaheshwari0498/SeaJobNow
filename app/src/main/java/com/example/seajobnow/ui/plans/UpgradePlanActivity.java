package com.example.seajobnow.ui.plans;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpgradePlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*getSupportActionBar().setTitle("Upgrade Plan");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        RecyclerViewLayoutManager = new LinearLayoutManager(this);
        // Set LayoutManager on Recycler View
        binding.rvPlans.setLayoutManager(RecyclerViewLayoutManager);

        // Set Horizontal Layout Manager
        // for Recycler view
//        HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        binding.rvPlans.setLayoutManager(HorizontalLayout);
        // Set adapter on recycler view
        AddItemsToRecyclerViewArrayList();
        // calling constructor of adapter
        // with source list as a parameter
        adapter = new PlanMasterAdapter(this,source);
        binding.rvPlans.setAdapter(adapter);
//        smoothScroller.setTargetPosition(1);
//        HorizontalLayout.startSmoothScroll(smoothScroller);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(binding.rvPlans);

//        autoScroll();


    }

    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == adapter.getItemCount())
                    count = 0;
                if(count < adapter.getItemCount()){
                    binding.rvPlans.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        // teal_500
        // yellow_400
        // green_400

        source = new ArrayList<>();
        source.add(new PlanMasterRequest("Basic Plan","4 Resumes", "14 Days","1 Lakh",
                "12 Months","1 Resume","Month","5",
                "NA","Monday to Friday","1 User",false,false,
                false,false,false,false,false,true,
                R.color.red_200,false));

        source.add(new PlanMasterRequest("Standard Plan","4 Resumes", "14 Days","1.5 Lakh",
                "12 Months","5 Resume","Day","0",
                "YES","Monday to Friday","3 User",false,true,
                false,false,false,false,false,true,
                R.color.blue_500,false));

        source.add(new PlanMasterRequest("Premium Plan","4 Resumes", "14 Days","2.5 Lakh",
                "12 Months + 1 Month","15 Resume","Day","0",
                "YES","7 Days a week","UNLIMITED",true,true,
                true,true,true,true,true,true,
                R.color.yellow_400,true));

    }
}