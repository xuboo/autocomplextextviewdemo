package com.puscene.wayne.autocomplextextviewdemo;

import android.content.Context;
import android.nfc.Tag;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by xuboo on 2016/12/26 0026.
 */

public class MyAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<String> originList;
    private List<String> list;
    private ArrayFilter mFilter;
    private List<String> mUnfilteredData;
    private List<String> filteredData = new ArrayList<>();
    private String indexStr;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        originList = list;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.check_item, parent, false);
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = getItem(position);
        if (item != null && indexStr != null ) {
//            holder.tv_item.setText(item);

            String strBefore = "";
            String strAfter = "";
            String strAll = item.toLowerCase();
            String choicedStr = indexStr.toLowerCase();
            int index = strAll.indexOf(choicedStr);
            if (index > 0 && index < (strAll.length() - 1)) {
                Log.e(TAG, "getView: index");
                strBefore = strAll.substring(0, strAll.indexOf(indexStr));
                strAfter = strAll.substring(strAll.indexOf(indexStr) + indexStr.length());
            } else if (index == 0) {
                Log.e(TAG, "getView: index == 0");
                strAfter = strAll.substring(strAll.indexOf(indexStr) + indexStr.length());
            } else if (index == (strAll.length() - 1)){
                Log.e(TAG, "getView: index == (strAll.length() - 1)");
                strBefore = strAll.substring(0, strAll.indexOf(indexStr));
            }
            holder.tv_item.setText(Html.fromHtml(strBefore + "<u><font color= 'red' >" + indexStr + "</font></u>" + strAfter));
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView tv_item;
    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();
            Log.e(TAG, "进行筛选");
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<String>(originList);
            }
            if (constraint == null || constraint.length() == 0) {//若输入为空

                mUnfilteredData.clear();
                Log.e(TAG, "进行筛选111111");
                ArrayList<String> list = (ArrayList<String>) mUnfilteredData;
                results.values = list;
                results.count = list.size();

            } else {//输入不为空
                indexStr = constraint.toString();//将输入内容传出去
                if (originList.size() != 0) {//填充数据不为空
                    Log.e(TAG, "进行筛选22222222");
                    String input_string = constraint.toString().toLowerCase();
                    mUnfilteredData.clear();
                    mUnfilteredData.addAll(originList);
                    filteredData.clear();
                    for (String word : mUnfilteredData) {
                        if (word.toLowerCase().contains(input_string)) {
                            filteredData.add(word);
                        }
                    }
                    results.values = filteredData;
                    results.count = filteredData.size();

                } else {//填充数据为空
                    Log.e(TAG, "进行筛选333333333");
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


    }
}
