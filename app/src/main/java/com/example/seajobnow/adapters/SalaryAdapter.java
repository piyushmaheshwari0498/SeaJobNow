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
import com.example.seajobnow.ApiEntity.request.RankRequest;
import com.example.seajobnow.ApiEntity.request.SalaryRequest;
import com.example.seajobnow.R;

import java.util.ArrayList;
import java.util.List;

public class SalaryAdapter extends ArrayAdapter<SalaryRequest> implements Filterable {
    private final Context mContext;
    private final List<SalaryRequest> mSalaryRequest;
    private final List<SalaryRequest> mSalaryRequestListAll;
    private final int mLayoutResourceId;

    public SalaryAdapter(Context context, int resource, List<SalaryRequest> salaryRequests) {
        super(context, resource, salaryRequests);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mSalaryRequest = new ArrayList<>(salaryRequests);
        this.mSalaryRequestListAll = new ArrayList<>(salaryRequests);
    }

    public int getCount() {
        return mSalaryRequest.size();
    }

    public SalaryRequest getItem(int position) {
        return mSalaryRequest.get(position);
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
            SalaryRequest salaryRequest = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.text_spinner);
            name.setText(salaryRequest.getSalary());
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
                return ((SalaryRequest) resultValue).getSalary();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<SalaryRequest> salaryRequestSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (SalaryRequest salaryRequest : mSalaryRequestListAll) {
                        if (salaryRequest.getSalary().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            salaryRequestSuggestion.add(salaryRequest);
                        }
                    }
                    filterResults.values = salaryRequestSuggestion;
                    filterResults.count = salaryRequestSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mSalaryRequest.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof CityRequest) {
                            mSalaryRequest.add((SalaryRequest) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mSalaryRequest.addAll(mSalaryRequestListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
