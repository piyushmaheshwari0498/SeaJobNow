package com.example.seajobnow.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnow.MainActivity;
import com.example.seajobnow.R;
import com.example.seajobnow.actions.ShowSnackbar;
import com.example.seajobnow.adapters.HomeNewsAdapter;
import com.example.seajobnow.adapters.HomePostAdapter;
import com.example.seajobnow.databinding.FragmentHomeBinding;
import com.example.seajobnow.model.HomeNews;
import com.example.seajobnow.model.HomePost;
import com.example.seajobnow.ui.postjob.PostJobFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    TextView textView_company_name;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // Array list for recycler view data source
    ArrayList<HomePost> source;
    ArrayList<HomeNews> sourceNews;

    // adapter class object
    HomePostAdapter adapter;
    HomeNewsAdapter newsAdapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

      //  if (context instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitle("Home");
       // }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle("Home");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_company_name = binding.textCompanyName;

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView_company_name.setText(s);
            }
        });

        binding.buttonPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new ShowSnackbar().shortSnackbar(view,"Button Clicked");
                Fragment postFragment = new PostJobFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,postFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        binding.rvPost.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();
        AddNewsItemsToRecyclerViewArrayList();

        // calling constructor of adapter
        // with source list as a parameter
        adapter = new HomePostAdapter(getContext(),source);
        newsAdapter = new HomeNewsAdapter(getContext(),sourceNews);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvPost.setLayoutManager(HorizontalLayout);
        // Set adapter on recycler view
        binding.rvPost.setAdapter(adapter);

        binding.rvNews.setLayoutManager(RecyclerViewLayoutManager);
        binding.rvNews.setAdapter(newsAdapter);
    }

    //To avoid memory Leak
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new HomePost("Interview Scheduled","50 counts",R.drawable.icon_1,R.color.blue_500));
        source.add(new HomePost("Application Sent","70 counts",R.drawable.icon_2,R.color.green_400));
        source.add(new HomePost("Profile Viewed","50 counts",R.drawable.icon_3,R.color.red_400));
        source.add(new HomePost("Unread Message","99+ counts",R.drawable.icon_4,R.color.yellow_400));
    }

    public void AddNewsItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        sourceNews = new ArrayList<>();
        sourceNews.add(new HomeNews("We will succeed, and we will create irreversible momentum with our modernization efforts over the next 24 months. Commandant Gen",R.drawable.icon_1,R.color.blue_500));
        sourceNews.add(new HomeNews("“What it will be replaced with is not necessarily another vehicle,” Smith said. “It could be, but the capability is to also control air and ground robotics and provide reconnaissance.",R.drawable.icon_1,R.color.green_400));
        sourceNews.add(new HomeNews("Either model would provide our reserve pilots with the opportunity to become significant contributors to our daily operations",R.drawable.icon_1,R.color.red_400));
        sourceNews.add(new HomeNews("Started shedding all RQ-21 aircraft and introduced MQ-9A and VBat Unmanned Aerial Systems for additional experimentation.",R.drawable.icon_1,R.color.yellow_400));
    }

}