package com.example.seajobnow.ui.advertisement;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnow.R;
import com.example.seajobnow.adapters.PostAdvertisementAdapter;
import com.example.seajobnow.adapters.PostJobsAdapter;
import com.example.seajobnow.databinding.AdvertisementFragmentBinding;
import com.example.seajobnow.databinding.FragmentPostjobBinding;
import com.example.seajobnow.model.PostAdvertisement;
import com.example.seajobnow.model.PostJobs;
import com.example.seajobnow.ui.postjob.PostJobViewModel;
import com.example.seajobnow.utils.InternetConnection;

import java.util.ArrayList;

public class Advertisement extends Fragment {

    AdvertisementFragmentBinding binding;
    AdvertisementViewModel advertisementViewModel;
    InternetConnection internetConnection;
    ArrayList<PostAdvertisement> source;
    PostAdvertisementAdapter adapter;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    public static Advertisement newInstance() {
        return new Advertisement();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        advertisementViewModel = new ViewModelProvider(this).get(AdvertisementViewModel.class);

        internetConnection = new InternetConnection();

        binding = AdvertisementFragmentBinding.inflate(inflater, container, false);

      /*  binding.rvPostedAdvertisement.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.fabAdvertisement.getVisibility() == View.VISIBLE) {
                    binding.fabAdvertisement.hide();
                } else if (dy < 0 && binding.fabAdvertisement.getVisibility() != View.VISIBLE) {
                    binding.fabAdvertisement.show();
                }
            }
        });*/

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

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        binding.rvPostedAdvertisement.setLayoutManager(RecyclerViewLayoutManager);

        adapter = new PostAdvertisementAdapter(getContext(),source);
        binding.rvPostedAdvertisement.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
            AddItemsToRecyclerViewArrayList();
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

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PostAdvertisement("Urgently Requires 2/O,C/E,2/E,E/O","Container Ship, Reefer Container","10/10/2021","12/10/2021"));
        source.add(new PostAdvertisement("Urgently Requires Master,2/O,C/E,2/E,E/O","Container Ship, Reefer Vessel","10/10/2021","12/10/2021"));
        source.add(new PostAdvertisement("Urgently Requires Master,2/O,2/E","Container Ship, Reefer Container","10/10/2021","12/10/2021"));
        source.add(new PostAdvertisement("Urgently Requires Master,2/O,C/E,E/O","Container Ship, Reefer Vessel","10/10/2021","12/10/2021"));
    }

}