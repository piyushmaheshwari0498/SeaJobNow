package com.example.seajobnow.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.seajobnow.ApiEntity.request.CityRequest;
import com.example.seajobnow.ApiEntity.request.CountryRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<CountryRequest> {
    private final Context mContext;
    private final List<CountryRequest> mCountryRequests;
    private final List<CountryRequest> mCountryRequestsListAll;
    private List<CountryRequest> mCountryRequestsSuggestion;
    private final int mLayoutResourceId;

    public CountryAdapter(Context context, int resource, List<CountryRequest> stateRequests) {
        super(context, resource, stateRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCountryRequests = stateRequests;
        this.mCountryRequestsListAll = new ArrayList<>(stateRequests);
        this.mCountryRequestsSuggestion = new ArrayList<>();
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

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    private Filter countryFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CountryRequest countryRequest = (CountryRequest) resultValue;
            return countryRequest.getCountryName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                mCountryRequestsSuggestion.clear();
                for (CountryRequest countryRequest : mCountryRequestsListAll) {
                    if (countryRequest.getCountryName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        mCountryRequestsSuggestion.add(countryRequest);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCountryRequestsSuggestion;
                filterResults.count = mCountryRequestsSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<CountryRequest> tempValues = (ArrayList<CountryRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (CountryRequest countryobj : tempValues) {
                    add(countryobj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (CountryRequest countryobj : mCountryRequestsListAll) {
                    add(countryobj);
                }
                notifyDataSetChanged();
            }
        }
    };
}
