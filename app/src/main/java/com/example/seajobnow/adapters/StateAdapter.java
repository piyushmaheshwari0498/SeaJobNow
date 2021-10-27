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
import com.example.seajobnow.ApiEntity.request.StateRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<StateRequest> {
    private final Context mContext;
    private final List<StateRequest> mStateRequests;
    private final List<StateRequest> mStateRequestListAll;
    private List<StateRequest> mStateRequestListSuggestion;
    private final int mLayoutResourceId;

    public StateAdapter(Context context, int resource, List<StateRequest> stateRequests) {
        super(context, resource, stateRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mStateRequests = stateRequests;
        this.mStateRequestListAll = new ArrayList<>(stateRequests);
        this.mStateRequestListSuggestion = new ArrayList<>();
    }

    public int getCount() {
        return mStateRequests.size();
    }

    public StateRequest getItem(int position) {
        return mStateRequests.get(position);
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
            StateRequest stateRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(stateRequest.getStateName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return stateFilter;
    }

    private Filter stateFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            StateRequest stateRequest = (StateRequest) resultValue;
            return stateRequest.getStateName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                mStateRequestListSuggestion.clear();
                for (StateRequest stateRequest : mStateRequestListAll) {
                    if (stateRequest.getStateName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        mStateRequestListSuggestion.add(stateRequest);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mStateRequestListSuggestion;
                filterResults.count = mStateRequestListSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<StateRequest> tempValues = (ArrayList<StateRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (StateRequest stateObj : tempValues) {
                    add(stateObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (StateRequest stateObj : mStateRequestListAll) {
                    add(stateObj);
                }
                notifyDataSetChanged();
            }
        }
    };
}
