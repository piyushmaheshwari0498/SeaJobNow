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

import com.example.seajobnow.ApiEntity.request.CityRequest;
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
    private final int mLayoutResourceId;
    AppSharedPreference appSharedPreference;

    public CityAdapter(Context context, int resource, List<CityRequest> cityRequests) {
        super(context, 0, cityRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityRequests = new ArrayList<>(cityRequests);
        this.mCityRequestListAll = new ArrayList<>(cityRequests);
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

    private Filter cityFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((CityRequest) resultValue).getCityName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<CityRequest> cityRequestSuggestion = new ArrayList<>();
            appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_CITY_ID,"");

            if (constraint == null || constraint.length() == 0) {
                cityRequestSuggestion.addAll(mCityRequestListAll);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CityRequest cityRequest : mCityRequestListAll) {
                    if (cityRequest.getCityName().toLowerCase().trim().startsWith(filterPattern)) {
                        cityRequestSuggestion.add(cityRequest);
                        Log.e("selected cityId",cityRequest.getCityId());
                        Log.e("selected cityName",cityRequest.getCityName());
                        appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_CITY_ID,cityRequest.getCityId());
                    }
                }
                //Log.e("cityId values",cityRequestSuggestion.toString());
                filterResults.values = cityRequestSuggestion;
                filterResults.count = cityRequestSuggestion.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCityRequests.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mCityRequests.add((CityRequest) object);
                        }
                    }
//                    Log.d("cityId values", results.values.toString());
//                    mCityRequests.addAll((ArrayList<CityRequest>) results.values);
                    notifyDataSetChanged();
                } /*else if (constraint == null) {
                    // no filter, add entire original list back in
                    mCityRequests.addAll(mCityRequestListAll);
                    notifyDataSetInvalidated();
                }*/
//            mCityRequests.addAll((ArrayList<CityRequest>) results.values);
        }
    };


    @Override
    public Filter getFilter() {
        return cityFilter;
    }
}
