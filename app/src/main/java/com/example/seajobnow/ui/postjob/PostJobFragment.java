package com.example.seajobnow.ui.postjob;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import com.example.seajobnow.ApiEntity.RetrofitBuilder;
import com.example.seajobnow.ApiEntity.request.DepartmentRequest;
import com.example.seajobnow.ApiEntity.request.DesignationRequest;
import com.example.seajobnow.ApiEntity.request.EmployementTypeRequest;
import com.example.seajobnow.ApiEntity.request.PostSpinnerDataRequest;
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.ApiEntity.request.SalaryRequest;
import com.example.seajobnow.ApiEntity.request.ShipTypeRequest;
import com.example.seajobnow.ApiEntity.request.SpinnerDataRequest;
import com.example.seajobnow.ApiEntity.response.PostSpinnerResponse;
import com.example.seajobnow.ApiEntity.response.SpinnerResponse;
import com.example.seajobnow.Apinterface.ApiConnection;
import com.example.seajobnow.LoginActivity;
import com.example.seajobnow.MainActivity;
import com.example.seajobnow.R;

import com.example.seajobnow.RegisterActivity;
import com.example.seajobnow.actions.ShowSnackbar;
import com.example.seajobnow.adapters.CityAdapter;
import com.example.seajobnow.adapters.CountryAdapter;
import com.example.seajobnow.adapters.DepartmentAdapter;
import com.example.seajobnow.adapters.HomePostAdapter;
import com.example.seajobnow.adapters.PostJobsAdapter;
import com.example.seajobnow.adapters.RankAdapter;
import com.example.seajobnow.adapters.SalaryAdapter;
import com.example.seajobnow.adapters.ShipTypeAdapter;
import com.example.seajobnow.adapters.StateAdapter;
import com.example.seajobnow.databinding.FragmentPostjobBinding;
import com.example.seajobnow.model.HomeNews;
import com.example.seajobnow.model.HomePost;
import com.example.seajobnow.model.PostJobs;
import com.example.seajobnow.ui.home.HomeFragment;
import com.example.seajobnow.utils.InternetConnection;
import com.example.seajobnow.utils.RecyclerTouchListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobFragment extends Fragment {

    PostJobViewModel postJobViewModel;
    FragmentPostjobBinding binding;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;

    //Spinner Lists
    List<RankRequest> rankRequestList;
    List<DepartmentRequest> departmentRequestList;
    List<EmployementTypeRequest> employementTypeRequestList;
    List<ShipTypeRequest> shipTypeRequestList;
    List<SalaryRequest> salaryRequestList;

    //Selected Id & Name
    String selectedRankId,selectedDepartmentId,selectedShipId,selectedSalaryId,selectedEmployementTypeId;
    String selectedRankName,selectedDepartmentName,selectedShipName,selectedSalaryName,selectedEmployementTypeMame;

    //Adapters
    RankAdapter rankAdapter;
    DepartmentAdapter departmentAdapter;
    ShipTypeAdapter shipTypeAdapter;
    SalaryAdapter salaryAdapter;

    String fromdate, todate;
    TextInputLayout inputJobName,inputStartDate,inputEndDate,inputspnSalary;
    TextInputLayout inputspnDepartment,inputspnRank,inputspnShip,inputspnLocation;
    TextInputEditText etJobName,startDate,endDate;
    AutoCompleteTextView spnSalary,spnDepartment,spnRank,spnShip,spnLocation,spnEmployementType;

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
        spnEmployementType = bottomSheetDialog.findViewById(R.id.spnEmployementType);

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        showDatePicker();

        if (!internetConnection.isConnected(getContext())) {
            new ShowSnackbar().shortSnackbar(binding.getRoot(),getString(R.string.no_internet));
        }
        else {
            getSpinnerData();
        }

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
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Kitchen Jr. Chef","Hotel","Chef","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Engine","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
    }

    public void getSpinnerData() {
        ApiConnection apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiConnection.class);
        Call<PostSpinnerResponse> call = apiInterface.getPostSpinner();
        call.enqueue(new Callback<PostSpinnerResponse>() {
            @Override
            public void onResponse(Call<PostSpinnerResponse> call, Response<PostSpinnerResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        PostSpinnerDataRequest registerData = response.body().getData();

                        //Rank Data
                        rankRequestList = registerData.getRank();
                        rankAdapter = new RankAdapter(getContext(),R.layout.custom_spinner_item, rankRequestList);
                        spnRank.setThreshold(1);
                        spnRank.setAdapter(rankAdapter);


                        spnRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("rankId", rankRequestList.get(pos).getActualRankId());
                                selectedRankId = rankRequestList.get(pos).getActualRankId();
                                selectedRankName = rankRequestList.get(pos).getActualRankName();
//                                spnRank.setText(selectedRankName);
                            }
                        });

                        //Department Data
                        departmentRequestList = registerData.getDepartment();
                        departmentAdapter = new DepartmentAdapter(getContext(),R.layout.custom_spinner_item, departmentRequestList);
                        spnDepartment.setThreshold(1);
                        spnDepartment.setAdapter(departmentAdapter);


                        spnDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("departmentId", departmentRequestList.get(pos).getCdgmId());
                                selectedDepartmentId = departmentRequestList.get(pos).getCdgmId();
                                selectedDepartmentName = departmentRequestList.get(pos).getCdgmDesignation();
//                                spnDepartment.setText(selectedDepartmentName);
                            }
                        });

                        //Salary Data
                        salaryRequestList = registerData.getSalary();
                        salaryAdapter = new SalaryAdapter(getContext(),R.layout.custom_spinner_item, salaryRequestList);
                        spnSalary.setThreshold(1);
                        spnSalary.setAdapter(salaryAdapter);


                        spnSalary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("salaryId", salaryRequestList.get(pos).getCsmId());
                                selectedSalaryId = salaryRequestList.get(pos).getCsmId();
                                selectedSalaryName = salaryRequestList.get(pos).getSalary();
//                                spnSalary.setText(selectedSalaryName);
                            }
                        });

                        //Ship Type Data
                        shipTypeRequestList = registerData.getShipType();
                        shipTypeAdapter = new ShipTypeAdapter(getContext(),R.layout.custom_spinner_item, shipTypeRequestList);
                        spnShip.setThreshold(1);
                        spnShip.setAdapter(shipTypeAdapter);


                        spnShip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("shipId", shipTypeRequestList.get(pos).getVtId());
                                selectedShipId = shipTypeRequestList.get(pos).getVtId();
                                selectedShipName = shipTypeRequestList.get(pos).getVtName();
//                                spnShip.setText(selectedShipName);
                            }
                        });

                        //Employment Type Type Data
//                        EmployementTypeRequest employementTypeRequest = registerData.getEmployementType();
//                        employementTypeRequestList.add(employementTypeRequest);
//                        customCityAdapter2 = new CityAdapter(getContext(),R.layout.custom_spinner_item, employementTypeRequestList);
//                        spnEmployementType.setThreshold(1);
//                        spnEmployementType.setAdapter(customCityAdapter2);
//
//
//                        spnEmployementType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                                Log.e("employementTypeId", employementTypeRequestList.get(pos).get1());
//                                selectedShipId = employementTypeRequestList.get(pos).get();
//                                selectedShipName = employementTypeRequestList.get(pos).getVtName();
//                                spnShip.setText(selectedShipName);
//                            }
//                        });

                    } else {
                        if (response.body().getStatusCode() == 0) {
                            if (response.body().getStatusMessage().equals("Fail")) {
                                Toast.makeText(getContext(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostSpinnerResponse> call, Throwable t) {

            }
        });
    }


}