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
import com.example.seajobnow.ApiEntity.request.DepartmentRequest;
import com.example.seajobnow.ApiEntity.request.ShipTypeRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class ShipTypeAdapter extends ArrayAdapter<ShipTypeRequest> implements Filterable {
    private final Context mContext;
    private final List<ShipTypeRequest> mShipRequest;
    private List<ShipTypeRequest> mShipRequestListAll;
    private List<ShipTypeRequest> mShipRequestListSuggestion;
    private final int mLayoutResourceId;

    public ShipTypeAdapter(Context context, int resource, List<ShipTypeRequest> shipRequests) {
        super(context, resource, shipRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mShipRequest = shipRequests;
        this.mShipRequestListAll = new ArrayList<>(shipRequests);
        this.mShipRequestListSuggestion = new ArrayList<>();
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

    @NonNull
    @Override
    public Filter getFilter() {
        return shipFilter;
    }

    private Filter shipFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            ShipTypeRequest shipTypeRequest = (ShipTypeRequest) resultValue;
            return shipTypeRequest.getVtName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                mShipRequestListSuggestion.clear();
                for (ShipTypeRequest shipTypeRequest : mShipRequestListAll) {
                    if (shipTypeRequest.getVtName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        mShipRequestListSuggestion.add(shipTypeRequest);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mShipRequestListSuggestion;
                filterResults.count = mShipRequestListSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<ShipTypeRequest> tempValues = (ArrayList<ShipTypeRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (ShipTypeRequest shipobj : tempValues) {
                    add(shipobj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (ShipTypeRequest shipobj : mShipRequestListAll) {
                    add(shipobj);
                }
                notifyDataSetChanged();
            }
        }
    };
}
