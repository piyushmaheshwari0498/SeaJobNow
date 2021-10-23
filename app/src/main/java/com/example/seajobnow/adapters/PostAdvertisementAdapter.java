package com.example.seajobnow.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnow.R;
import com.example.seajobnow.activity.AdvertisementDetailsActivity;
import com.example.seajobnow.model.PostAdvertisement;

import java.util.List;

public class PostAdvertisementAdapter extends RecyclerView.Adapter<PostAdvertisementAdapter.MyView> {

    private List<PostAdvertisement> list;
    Context context;

    // Constructor for adapter class
    // which takes a list of String type
    public PostAdvertisementAdapter(Context context, List<PostAdvertisement> horizontalList) {
        this.context = context;
        this.list = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.advertisement_items,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyView holder, int position) {

        // Set the text of each item of
        // Recycler view with the list items
//        holder.cardView.setBackgroundColor(context.getColor(list.get(position).getColor()));
        holder.textView.setText(list.get(position).getAdd_title());
        holder.textViewship.setText(list.get(position).getShip_type());
        holder.textViewexpirydate.setText(list.get(position).getEnd_date());
        holder.textViewdate.setText(list.get(position).getStart_date());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new ShowSnackbar().shortSnackbar(view,list.get(position).getAdd_title());
               /* Intent intent= new Intent(context, AdvertisementDetailsActivity.class);
                intent.putExtra("ad_title",list.get(position).getAdd_title());
                context.startActivity(intent);*/

            }
        });



    }


    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;
        TextView textViewship;
        TextView textViewdate;
        TextView textViewexpirydate;
        CardView cardView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view.findViewById(R.id.ad_title);
            textViewship = (TextView) view.findViewById(R.id.textview_ship_type);
            textViewdate = (TextView) view.findViewById(R.id.textview_postedDate);
            textViewexpirydate = (TextView) view.findViewById(R.id.textview_expiry_date);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }
    }
}

