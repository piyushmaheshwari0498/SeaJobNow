package com.example.seajobnow.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.seajobnow.ApiEntity.request.StateRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<StateRequest> {
    private final Context mContext;
    private final List<StateRequest> mStateRequests;
    private final List<StateRequest> mStateRequestListAll;
    private final int mLayoutResourceId;

    public StateAdapter(Context context, int resource, List<StateRequest> stateRequests) {
        super(context, resource, stateRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mStateRequests = new ArrayList<>(stateRequests);
        this.mStateRequestListAll = new ArrayList<>(stateRequests);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((StateRequest) resultValue).getStateName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<StateRequest> stateRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (StateRequest stateRequest : mStateRequestListAll) {
                        if (stateRequest.getStateName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            stateRequestSuggestion.add(stateRequest);
                        }
                    }
                    filterResults.values = stateRequestSuggestion;
                    filterResults.count = stateRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mStateRequests.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof StateRequest) {
                            mStateRequests.add((StateRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mStateRequests.addAll(mStateRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
