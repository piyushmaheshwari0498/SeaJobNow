package com.example.seajobnow.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnow.R;
import com.example.seajobnow.model.HomePost;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.MyView> {
    // List with String type
    private List<HomePost> list;
    Context context;
    // Constructor for adapter class
    // which takes a list of String type
    public HomePostAdapter(Context context, List<HomePost> horizontalList) {
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
                .inflate(R.layout.homepostitems,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyView holder,
                                 int position) {

        // Set the text of each item of
        // Recycler view with the list items
//        holder.cardView.setBackgroundColor(context.getColor(list.get(position).getColor()));
        holder.textView.setText(list.get(position).getTittle());
        holder.textViewcount.setText(list.get(position).getCount());
        holder.imageIcon.setImageDrawable(context.getDrawable(list.get(position).getImage()));
//        holder.cardView.setBackgroundResource(R.drawable.testiamge);
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
        TextView textViewcount;
        ImageView imageIcon;
        CardView cardView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view.findViewById(R.id.textview);
            textViewcount = (TextView) view.findViewById(R.id.textview_count);
            imageIcon = view.findViewById(R.id.img_icon);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }
    }
}

