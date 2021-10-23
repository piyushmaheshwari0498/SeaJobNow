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

import com.example.seajobnow.ApiEntity.request.CityRequest;
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class RankAdapter extends ArrayAdapter<RankRequest> implements Filterable {
    private final Context mContext;
    private final List<RankRequest> mRankRequest;
    private final List<RankRequest> mRankRequestListAll;
    private final int mLayoutResourceId;

    public RankAdapter(Context context, int resource, List<RankRequest> rankRequests) {
        super(context, resource, rankRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mRankRequest = new ArrayList<>(rankRequests);
        this.mRankRequestListAll = new ArrayList<>(rankRequests);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((RankRequest) resultValue).getActualRankName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<RankRequest> rankRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (RankRequest rankRequest : mRankRequestListAll) {
                        if (rankRequest.getActualRankName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            rankRequestSuggestion.add(rankRequest);
                        }
                    }
                    filterResults.values = rankRequestSuggestion;
                    filterResults.count = rankRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mRankRequest.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mRankRequest.add((RankRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mRankRequest.addAll(mRankRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
