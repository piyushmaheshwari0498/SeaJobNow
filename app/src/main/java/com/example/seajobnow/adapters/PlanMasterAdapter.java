package com.example.seajobnow.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.Log;
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
    public void onBindViewHolder(MyView holder, int position) {

        // Set the text of each item of
        // Recycler view with the list items
//        holder.cardView.setBackgroundColor(context.getColor(list.get(position).getColor()));
        holder.plan_name.setText(list.get(position).getPlan_name());
        holder.tv_package_price.setText(list.get(position).getAnnual_fees());
        holder.tv_plan_duration.setText(list.get(position).getPlan_duration());
        holder.tv_trail_days.setText(list.get(position).getTrial_days());
        holder.tv_trial_cv.setText(list.get(position).getTrial_cv());
        holder.tv_unfilter_data.setText(list.get(position).getUnfiltered_per_week());
        holder.tv_smart_data.setText(list.get(position).getSmart_filtered_status());
        holder.tv_user_per_account.setText(list.get(position).getSub_account_user());
        holder.tv_download_days.setText(list.get(position).getDownload_days());
        holder.tv_download_resume.setText(list.get(position).getCv_download_count()+" / "+list.get(position).getCv_download_type());

        if(list.get(position).getViewColor() != 0)
            holder.plan_color.setBackgroundColor(context.getColor(list.get(position).getViewColor()));

        Log.d("color", String.valueOf(list.get(position).getViewColor()));
        if (list.get(position).getBestOffer()){
            holder.tv_best_offer.setVisibility(View.VISIBLE);
            holder.tv_best_offer.setBackgroundColor(context.getColor(list.get(position).getViewColor()));
        }
        else{
            holder.tv_best_offer.setVisibility(View.GONE);
        }

        holder.plan_name.setTextColor(context.getColor(list.get(position).getViewColor()));
        holder.btn_upgrade.setStrokeColorResource(list.get(position).getViewColor());

        if(!list.get(position).isWhatsapp_alert()){
            holder.img_whatsapp.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isEmail_alert()){
            holder.img_email.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isSms_alert()){
            holder.img_sms.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isRelationship_manager()){
            holder.img_manager.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isVisit_per_month()){
            holder.img_sevice_visit.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isMobile_app_access()){
            holder.img_mob_access.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isUrgent_vacancy()){
            holder.img_urgent.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }

        if(!list.get(position).isBmti_candidate()){
            holder.img_bmti.setImageDrawable(context.getDrawable(R.drawable.black_cross));
        }
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
        TextView tv_best_offer;
        TextView tv_package_price;

        ImageView img_whatsapp;
        ImageView img_email;
        ImageView img_sms;
        ImageView img_manager;
        ImageView img_mob_access;
        ImageView img_urgent;
        ImageView img_bmti;
        ImageView img_sevice_visit;

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
            tv_package_price = (TextView) view.findViewById(R.id.tv_package_price);
            tv_plan_duration = (TextView) view.findViewById(R.id.tv_plan_duration);
            tv_trail_days = (TextView) view.findViewById(R.id.tv_trail_days);
            tv_trial_cv = (TextView) view.findViewById(R.id.tv_trial_cv);
            tv_download_resume = (TextView) view.findViewById(R.id.tv_download_resume);
            tv_unfilter_data = (TextView) view.findViewById(R.id.tv_unfilter_data);
            tv_smart_data = (TextView) view.findViewById(R.id.tv_smart_data);
            tv_user_per_account = (TextView) view.findViewById(R.id.tv_user_per_account);
            tv_download_days = (TextView) view.findViewById(R.id.tv_download_days);
            tv_best_offer = (TextView) view.findViewById(R.id.tv_best_offer);

            img_whatsapp = view.findViewById(R.id.img_whatsapp);
            img_email = view.findViewById(R.id.img_email);
            img_sms = view.findViewById(R.id.img_sms);
            img_manager = view.findViewById(R.id.img_manager);
            img_mob_access = view.findViewById(R.id.img_mob_access);
            img_urgent = view.findViewById(R.id.img_urgent);
            img_bmti = view.findViewById(R.id.img_bmti);
            img_sevice_visit = view.findViewById(R.id.img_sevice_visit);

            plan_color = view.findViewById(R.id.plan_color);
            cardView = (CardView) view.findViewById(R.id.cardview);
        }
    }
}

