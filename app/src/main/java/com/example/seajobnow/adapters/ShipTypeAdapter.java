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
import com.example.seajobnow.ApiEntity.request.DepartmentRequest;
import com.example.seajobnow.ApiEntity.request.ShipTypeRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class ShipTypeAdapter extends ArrayAdapter<ShipTypeRequest> implements Filterable {
    private final Context mContext;
    private final List<ShipTypeRequest> mShipRequest;
    private final List<ShipTypeRequest> mShipRequestListAll;
    private final int mLayoutResourceId;

    public ShipTypeAdapter(Context context, int resource, List<ShipTypeRequest> shipRequests) {
        super(context, resource, shipRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mShipRequest = new ArrayList<>(shipRequests);
        this.mShipRequestListAll = new ArrayList<>(shipRequests);
    }

    public int getCount() {
        return mShipRequest.size();
    }

    public ShipTypeRequest getItem(int position) {
        return mShipRequest.get(position);
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
            ShipTypeRequest shipRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(shipRequest.getVtName());
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
                return ((ShipTypeRequest) resultValue).getVtName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<ShipTypeRequest> shipRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (ShipTypeRequest shipRequest : mShipRequestListAll) {
                        if (shipRequest.getVtName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            shipRequestSuggestion.add(shipRequest);
                        }
                    }
                    filterResults.values = shipRequestSuggestion;
                    filterResults.count = shipRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mShipRequest.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mShipRequest.add((ShipTypeRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mShipRequest.addAll(mShipRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
