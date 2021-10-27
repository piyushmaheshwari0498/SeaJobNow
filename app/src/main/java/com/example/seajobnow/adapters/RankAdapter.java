package com.example.seajobnow.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.seajobnow.ApiEntity.request.CityRequest;
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends ArrayAdapter<RankRequest> implements Filterable {
    private final Context mContext;
    private final List<RankRequest> mRankRequest;
    private final List<RankRequest> mRankRequestListAll;
    private List<RankRequest> rankRequestSuggestion;
    private final int mLayoutResourceId;

    public RankAdapter(Context context, int resource, List<RankRequest> rankRequests) {
        super(context, resource, rankRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mRankRequest = rankRequests;
        this.mRankRequestListAll = new ArrayList<>(rankRequests);
        this.rankRequestSuggestion = new ArrayList<>();
    }

    public int getCount() {
        return mRankRequest.size();
    }

    public RankRequest getItem(int position) {
        return mRankRequest.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            RankRequest rankRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(rankRequest.getActualRankName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }

    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            RankRequest fruit = (RankRequest) resultValue;
            return fruit.getActualRankName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                rankRequestSuggestion.clear();
                for (RankRequest fruit : mRankRequestListAll) {
                    if (fruit.getActualRankName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        rankRequestSuggestion.add(fruit);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = rankRequestSuggestion;
                filterResults.count = rankRequestSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<RankRequest> tempValues = (ArrayList<RankRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (RankRequest fruitObj : tempValues) {
                    add(fruitObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (RankRequest fruitObj : mRankRequestListAll) {
                    add(fruitObj);
                }
                notifyDataSetChanged();
            }
        }
    };

}
