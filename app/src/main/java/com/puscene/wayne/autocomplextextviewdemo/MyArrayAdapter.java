package com.puscene.wayne.autocomplextextviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuboo on 2016/12/26 0026.
 */

public class MyArrayAdapter extends ArrayAdapter implements Filterable {
    private Context context;
    private int layoutId;
    private List<String> list;
    private List<String> mUnfilteredData;
    private List<String> filteredData = new ArrayList<>();

    public MyArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        layoutId = resource;
        list = objects;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<String>(list);
            }
            if (constraint == null || constraint.length() == 0) {//若输入筛选条件为空
                mUnfilteredData.clear();
                ArrayList<String> list = (ArrayList<String>) mUnfilteredData;
                results.values = list;
                results.count = list.size();

            } else {//输入不为空
                if (list.size() != 0) {//填充数据不为空
                    String input_string = constraint.toString().toLowerCase();
                    mUnfilteredData.clear();
                    mUnfilteredData.addAll(list);
                    for (String word : mUnfilteredData) {
                        if (word.toLowerCase().contains(input_string)) {
                            filteredData.add(word);
                        }
                    }
                    results.values = filteredData;
                    results.count = filteredData.size();

                } else {//填充数据为空
                    mUnfilteredData.clear();
                    ArrayList<String> list = (ArrayList<String>) mUnfilteredData;
                    results.values = list;
                    results.count = list.size();
                }
            }
            return results;
        }



        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };



    @NonNull
    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
