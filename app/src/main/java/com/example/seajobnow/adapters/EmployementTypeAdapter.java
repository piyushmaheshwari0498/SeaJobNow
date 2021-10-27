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
import com.example.seajobnow.model.EmployementType;
import com.example.seajobnow.session.AppSharedPreference;
import com.example.seajobnow.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EmployementTypeAdapter extends ArrayAdapter<EmployementType>{
    private final Context mContext;
    List<EmployementType> mCityRequests;

    private final int mLayoutResourceId;
    AppSharedPreference appSharedPreference;

    public EmployementTypeAdapter(Context context, int resource, List<EmployementType> cityRequests) {
        super(context, 0, cityRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mCityRequests = new ArrayList<>(cityRequests);
        appSharedPreference = AppSharedPreference.getAppSharedPreference(context);
    }

    public int getCount() {
        return mCityRequests.size();
    }

    public EmployementType getItem(int position) {
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
            EmployementType cityRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            if(cityRequest != null) {
                name.setText(cityRequest.getTypeName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
