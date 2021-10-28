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

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.seajobnow.ApiEntity.request.PlanMasterRequest;
import com.example.seajobnow.R;
import com.example.seajobnow.adapters.HomePostAdapter;
import com.example.seajobnow.adapters.PlanMasterAdapter;
import com.example.seajobnow.databinding.ActivityLoginBinding;
import com.example.seajobnow.databinding.ActivityUpgradePlanBinding;
import com.example.seajobnow.model.HomePost;
import com.example.seajobnow.utils.CenterZoomLayoutManager;
import com.example.seajobnow.utils.CustomZoomLayoutManager;

import java.util.ArrayList;

public class UpgradePlanActivity extends AppCompatActivity {

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    // Layout Manager
    CustomZoomLayoutManager RecyclerViewLayoutManager;
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

        // Set LayoutManager on Recycler View
//        RecyclerViewLayoutManager = new CustomZoomLayoutManager(this);
//        binding.rvPlans.setLayoutManager(RecyclerViewLayoutManager);


        // Set Horizontal Layout Manager
        // for Recycler view
       /* HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        binding.rvPlans.setLayoutManager(HorizontalLayout);
        int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        binding.parentLayout.getLayoutParams().width = displayWidth - dpToPx(2) * 4;
        SnapHelper snapHelper = new PagerSnapHelper();
        binding.rvPlans.setLayoutManager(HorizontalLayout);
        snapHelper.attachToRecyclerView(binding.rvPlans);*/
        // Set adapter on recycler view
        AddItemsToRecyclerViewArrayList();
        // calling constructor of adapter
        // with source list as a parameter
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        binding.rvPlans.setLayoutManager(layoutManager);
        binding.rvPlans.setHasFixedSize(true);

        adapter = new PlanMasterAdapter(this,source);
        binding.rvPlans.setAdapter(adapter);
        binding.rvPlans.addOnScrollListener(new CenterScrollListener());
//        smoothScroller.setTargetPosition(1);
//        HorizontalLayout.startSmoothScroll(smoothScroller);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(binding.rvPlans);

//        autoScroll();


    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
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