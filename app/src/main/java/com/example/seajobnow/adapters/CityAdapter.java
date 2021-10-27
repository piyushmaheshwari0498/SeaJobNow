package com.example.seajobnow.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityAdapter extends ArrayAdapter<CityRequest> implements Filterable {
    private final Context mContext;
    List<CityRequest> mCityRequests;
    List<CityRequest> mCityRequestListAll;
    List<CityRequest> mCityRequestSuggestion;
    private final int mLayoutResourceId;
    AppSharedPreference appSharedPreference;

    public CityAdapter(Context context, int resource, List<CityRequest> cityRequests) {
        super(context, 0, cityRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityRequests = cityRequests;
        this.mCityRequestListAll = new ArrayList<>(cityRequests);
        this.mCityRequestSuggestion = new ArrayList<>();
        appSharedPreference = AppSharedPreference.getAppSharedPreference(context);
    }

    public int getCount() {
        return mCityRequests.size();
    }

    public CityRequest getItem(int position) {
        return mCityRequests.get(position);
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
            CityRequest cityRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            if(cityRequest != null) {
                name.setText(cityRequest.getCityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return cityFilter;
    }

    private Filter cityFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CityRequest cityRequest = (CityRequest) resultValue;
            return cityRequest.getCityName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                mCityRequestSuggestion.clear();
                for (CityRequest cityRequest : mCityRequestListAll) {
                    if (cityRequest.getCityName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        mCityRequestSuggestion.add(cityRequest);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCityRequestSuggestion;
                filterResults.count = mCityRequestSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<CityRequest> tempValues = (ArrayList<CityRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (CityRequest cityObj : tempValues) {
                    add(cityObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (CityRequest cityObj : mCityRequestListAll) {
                    add(cityObj);
                }
                notifyDataSetChanged();
            }
        }
    };
}
