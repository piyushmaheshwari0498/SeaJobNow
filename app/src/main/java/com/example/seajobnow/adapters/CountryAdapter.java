package com.example.seajobnow.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.seajobnow.ApiEntity.request.CountryRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<CountryRequest> {
    private final Context mContext;
    private final List<CountryRequest> mCountryRequests;
    private final List<CountryRequest> mCountryRequestsListAll;
    private final int mLayoutResourceId;

    public CountryAdapter(Context context, int resource, List<CountryRequest> stateRequests) {
        super(context, resource, stateRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCountryRequests = new ArrayList<>(stateRequests);
        this.mCountryRequestsListAll = new ArrayList<>(stateRequests);
    }

    public int getCount() {
        return mCountryRequests.size();
    }

    public CountryRequest getItem(int position) {
        return mCountryRequests.get(position);
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
            CountryRequest countryRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(countryRequest.getCountryName());
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
                return ((CountryRequest) resultValue).getCountryName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<CountryRequest> stateRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (CountryRequest countryRequest : mCountryRequestsListAll) {
                        if (countryRequest.getCountryName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            stateRequestSuggestion.add(countryRequest);
                        }
                    }
                    filterResults.values = stateRequestSuggestion;
                    filterResults.count = stateRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCountryRequests.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CountryRequest) {
                            mCountryRequests.add((CountryRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mCountryRequests.addAll(mCountryRequestsListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
