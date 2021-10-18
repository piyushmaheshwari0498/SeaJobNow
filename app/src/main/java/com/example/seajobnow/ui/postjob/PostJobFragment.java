package com.example.seajobnow.ui.postjob;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.seajobnow.LoginActivity;
import com.example.seajobnow.MainActivity;
import com.example.seajobnow.R;

import com.example.seajobnow.adapters.HomePostAdapter;
import com.example.seajobnow.adapters.PostJobsAdapter;
import com.example.seajobnow.databinding.FragmentPostjobBinding;
import com.example.seajobnow.model.HomeNews;
import com.example.seajobnow.model.HomePost;
import com.example.seajobnow.model.PostJobs;
import com.example.seajobnow.ui.home.HomeFragment;
import com.example.seajobnow.utils.InternetConnection;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PostJobFragment extends Fragment {

    PostJobViewModel postJobViewModel;
    FragmentPostjobBinding binding;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;

    String fromdate, todate;
    TextInputLayout inputJobName,inputStartDate,inputEndDate,inputspnSalary;
    TextInputLayout inputspnDepartment,inputspnRank,inputspnShip,inputspnLocation;
    TextInputEditText etJobName,startDate,endDate;
    AutoCompleteTextView spnSalary,spnDepartment,spnRank,spnShip,spnLocation;

    InternetConnection internetConnection;

    // Array list for recycler view data source
    ArrayList<PostJobs> source;

    // adapter class object
    PostJobsAdapter adapter;

    // Linear Layout Manager
    LinearLayoutManager linearLayoutManager;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    public PostJobFragment() {
        //Blank Constructor for fragment to fragment navigation
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        getActivity().setTitle(R.string.menu_post_job);

        postJobViewModel = new ViewModelProvider(this).get(PostJobViewModel.class);

        internetConnection = new InternetConnection();

        binding = FragmentPostjobBinding.inflate(inflater, container, false);

        binding.fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        AddItemsToRecyclerViewArrayList();

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

        adapter = new PostJobsAdapter(getContext(),source);
        binding.rvPostedJobs.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshItems();

        binding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshItems();
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        binding.rvPostedJobs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.fabNote.getVisibility() == View.VISIBLE) {
                    binding.fabNote.hide();
                } else if (dy < 0 && binding.fabNote.getVisibility() != View.VISIBLE) {
                    binding.fabNote.show();
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.postjob_bottom_sheet);
        bottomSheetDialog.setCancelable(false);

        //For Scrolling on Keyboard visible
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //Expand Dialog on Visible
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        MaterialButton save_post = bottomSheetDialog.findViewById(R.id.btn_save_post);
        TextView cancel_post = bottomSheetDialog.findViewById(R.id.btn_cancel_post);

        bottomSheetDialog.show();

        inputJobName = bottomSheetDialog.findViewById(R.id.inputJobName);
        inputStartDate = bottomSheetDialog.findViewById(R.id.inputStartDate);
        inputEndDate = bottomSheetDialog.findViewById(R.id.inputEndDate);
        inputspnRank = bottomSheetDialog.findViewById(R.id.inputspnRank);
        inputspnDepartment = bottomSheetDialog.findViewById(R.id.inputspnDepartment);
        inputspnSalary = bottomSheetDialog.findViewById(R.id.inputspnSalary);
        inputspnShip = bottomSheetDialog.findViewById(R.id.inputspnShip);
        inputspnLocation = bottomSheetDialog.findViewById(R.id.inputspnLocation);

        etJobName = bottomSheetDialog.findViewById(R.id.etJobName);
        startDate = bottomSheetDialog.findViewById(R.id.etStartDate);
        endDate = bottomSheetDialog.findViewById(R.id.etEndDate);

        spnRank = bottomSheetDialog.findViewById(R.id.spnRank);
        spnDepartment = bottomSheetDialog.findViewById(R.id.spnDepartment);
        spnShip = bottomSheetDialog.findViewById(R.id.spnShip);
        spnSalary = bottomSheetDialog.findViewById(R.id.spnSalary);
        spnLocation = bottomSheetDialog.findViewById(R.id.spnLocation);

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        showDatePicker();

        save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });


    }

    public void showDatePicker(){
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int newMonth = monthOfYear + 1;
                                String monthObtained = newMonth < 10 ? "0" + newMonth : String.valueOf(newMonth);
                                String dayObtained = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String displayDate = dayObtained + "-" + monthObtained + "-" + year;
                                startDate.setText(displayDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int newMonth = monthOfYear + 1;
                                String monthObtained = newMonth < 10 ? "0" + newMonth : String.valueOf(newMonth);
                                String dayObtained = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String displayDate = dayObtained + "-" + monthObtained + "-" + year;
                                endDate.setText(displayDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });

    }


    void refreshItems() {
        // Load items
        // ...
        if (!internetConnection.isConnected(getContext())) {
            // ringProgressDialog.dismiss();
            no_internet();
            //  Custom_Toast.warning(getContext(), getResources().getString(R.string.no_internet));
        } else {
            binding.llDataFound.setVisibility(View.VISIBLE);
            binding.llNoDataFound.setVisibility(View.GONE);
//            salereturnDetails();
        }
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation

        binding.swipeRefreshLayout.setRefreshing(false);
    }

    public void no_internet() {
        binding.llDataFound.setVisibility(View.GONE);
        binding.llNoDataFound.setVisibility(View.VISIBLE);
        binding.retryBtn.setVisibility(View.VISIBLE);
        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_internet_connection));
        binding.noDataText.setText(getResources().getString(R.string.no_internet));
    }

    public void something_wrong() {
        binding.llDataFound.setVisibility(View.GONE);
        binding.llNoDataFound.setVisibility(View.VISIBLE);
        binding.retryBtn.setVisibility(View.VISIBLE);
        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
        binding.noDataText.setText(getResources().getString(R.string.something_wrong));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // System.out.println("Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //  System.out.println("Potrait");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        if (context instanceof HomeFragment) {
//            ((MainActivity) context).setTitle("Home");
//        }
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Post Jobs");
    }

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","$10000 - $20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Kitchen Jr. Chef","Hotel","Chef","$10000 - $20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Engine","Enginer","Master","$10000 - $20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","$10000 - $20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));

    }


}