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
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<DepartmentRequest> implements Filterable {
    private final Context mContext;
    private final List<DepartmentRequest> mDepartmentRequest;
    private final List<DepartmentRequest> mDepartmentRequestListAll;
    private final int mLayoutResourceId;

    public DepartmentAdapter(Context context, int resource, List<DepartmentRequest> deptRequests) {
        super(context, resource, deptRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mDepartmentRequest = new ArrayList<>(deptRequests);
        this.mDepartmentRequestListAll = new ArrayList<>(deptRequests);
    }

    public int getCount() {
        return mDepartmentRequest.size();
    }

    public DepartmentRequest getItem(int position) {
        return mDepartmentRequest.get(position);
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
            DepartmentRequest departmentRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(departmentRequest.getCdgmDesignation());
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
                return ((DepartmentRequest) resultValue).getCdgmDesignation();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<DepartmentRequest> departmentRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (DepartmentRequest deptRequest : mDepartmentRequestListAll) {
                        if (deptRequest.getCdgmDesignation().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            departmentRequestSuggestion.add(deptRequest);
                        }
                    }
                    filterResults.values = departmentRequestSuggestion;
                    filterResults.count = departmentRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDepartmentRequest.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mDepartmentRequest.add((DepartmentRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mDepartmentRequest.addAll(mDepartmentRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
