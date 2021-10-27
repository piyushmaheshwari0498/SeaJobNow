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
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<DepartmentRequest> implements Filterable {
    private final Context mContext;
    private final List<DepartmentRequest> mDepartmentRequest;
    private final List<DepartmentRequest> mDepartmentRequestListAll;
    private List<DepartmentRequest> mDepartmentRequestListSuggestion;
    private final int mLayoutResourceId;

    public DepartmentAdapter(Context context, int resource, List<DepartmentRequest> deptRequests) {
        super(context, resource, deptRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mDepartmentRequest = deptRequests;
        this.mDepartmentRequestListAll = new ArrayList<>(deptRequests);
        this.mDepartmentRequestListSuggestion = new ArrayList<>();
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

    @NonNull
    @Override
    public Filter getFilter() {
        return deptFilter;
    }

    private Filter deptFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            DepartmentRequest departmentRequest = (DepartmentRequest) resultValue;
            return departmentRequest.getCdgmDesignation();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                mDepartmentRequestListSuggestion.clear();
                for (DepartmentRequest departmentRequest : mDepartmentRequestListAll) {
                    if (departmentRequest.getCdgmDesignation().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        mDepartmentRequestListSuggestion.add(departmentRequest);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDepartmentRequestListSuggestion;
                filterResults.count = mDepartmentRequestListSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<DepartmentRequest> tempValues = (ArrayList<DepartmentRequest>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (DepartmentRequest deptobj : tempValues) {
                    add(deptobj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                for (DepartmentRequest deptobj : mDepartmentRequestListAll) {
                    add(deptobj);
                }
                notifyDataSetChanged();
            }
        }
    };
}
