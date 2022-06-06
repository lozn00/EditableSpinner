package lozn.spinner.impl;

import android.widget.Filter;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import lozn.spinner.BaseViewHolderI;

public  class AutoCompleteSpinnerAdapter<T> extends DefaultSpinnerRecycleAdapter<T> {
    private MyFilter myFilter;

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
//        myFilter.setSuperDataLiSt(getSuperData());
        return myFilter;
    }

    public class MyFilter extends Filter {

        public MyFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> newData = new ArrayList<>();
            List<T> superData = getSuperData();
            for (T currentItem : superData) {
                if (currentItem.toString().contains(constraint)) {
                    newData.add(currentItem);
                }
            }
            LinkedHashSet hashSet=new LinkedHashSet();
            hashSet.addAll(newData);
            hashSet.addAll(superData);
            newData.clear();
            newData.addAll(hashSet);
            results.values = newData;

            results.count = newData.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datafilter = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public List<T> getData() {
        if (datafilter != null && !datafilter.isEmpty()) {
            return datafilter;
        }
        return super.getData();
    }

    public List<T> getSuperData() {
        return super.getData();
    }

    @Override
    public int getCount() {
        if (datafilter != null && !datafilter.isEmpty()) {
            return datafilter.size();
        }
        return super.getCount();
    }

    public List<T> getDatafilter() {
        return datafilter;
    }

    public void setDatafilter(List<T> datafilter) {
        this.datafilter = datafilter;
    }

    public List<T> datafilter;
}
