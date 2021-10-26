package com.example.seajobnow.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnow.ApiEntity.request.PlanMasterRequest;
import com.example.seajobnow.R;
import com.example.seajobnow.model.HomeNews;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class PlanMasterAdapter extends RecyclerView.Adapter<PlanMasterAdapter.MyView> {
    // List with String type
    private List<PlanMasterRequest> list;
    Context context;
    // Constructor for adapter class
    // which takes a list of String type
    public PlanMasterAdapter(Context context, List<PlanMasterRequest> horizontalList) {
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
                .inflate(R.layout.item_plan_layout,
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
        holder.plan_name.setText(list.get(position).getPlan_name());
        holder.tv_plan_duration.setText(list.get(position).getPlan_duration());
        holder.plan_color.setBackgroundColor(context.getColor(list.get(position).getViewColor()));
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
        TextView plan_name;
        TextView tv_plan_duration;
        TextView tv_trail_days;
        TextView tv_trial_cv;
        TextView tv_download_resume;
        TextView tv_unfilter_data;
        TextView tv_smart_data;
        TextView tv_user_per_account;
        TextView tv_download_days;

        ImageView img_whatsapp;
        ImageView img_email;
        ImageView img_sms;
        ImageView img_manager;
        ImageView img_mob_access;
        ImageView img_urgent;
        ImageView img_bmti;

        View plan_color;
        CardView cardView;
        MaterialButton btn_upgrade;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            btn_upgrade = view.findViewById(R.id.btn_upgrade);

            plan_name = (TextView) view.findViewById(R.id.tv_plan_name);
            tv_plan_duration = (TextView) view.findViewById(R.id.tv_plan_duration);
            tv_trail_days = (TextView) view.findViewById(R.id.tv_trail_days);
            tv_trial_cv = (TextView) view.findViewById(R.id.tv_trial_cv);
            tv_download_resume = (TextView) view.findViewById(R.id.tv_download_resume);
            tv_unfilter_data = (TextView) view.findViewById(R.id.tv_unfilter_data);
            tv_smart_data = (TextView) view.findViewById(R.id.tv_smart_data);
            tv_user_per_account = (TextView) view.findViewById(R.id.tv_user_per_account);
            tv_download_days = (TextView) view.findViewById(R.id.tv_download_days);

            img_whatsapp = view.findViewById(R.id.img_whatsapp);
            img_email = view.findViewById(R.id.img_email);
            img_sms = view.findViewById(R.id.img_sms);
            img_manager = view.findViewById(R.id.img_manager);
            img_mob_access = view.findViewById(R.id.img_mob_access);
            img_urgent = view.findViewById(R.id.img_urgent);
            img_bmti = view.findViewById(R.id.img_bmti);

            plan_color = view.findViewById(R.id.plan_color);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }
    }
}

