package lozn.spinner.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import lozn.spinner.BaseViewHolderI;
import lozn.spinner.base.ListViewRecycleAdapter;
import lozn.spinner.R;


public class DefaultSpinnerRecycleAdapter<T>  extends ListViewRecycleAdapter<T, DefaultSpinnerRecycleAdapter.GenericDataBindViewHolder> {
    private List<T> datafilter;
    private MyFilter myFilter;

    @Override
    public GenericDataBindViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        View inflate = from.inflate(R.layout.simple_spinner_item, viewGroup, false);
        return new GenericDataBindViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(GenericDataBindViewHolder viewHolder, int position) {
        viewHolder.textView.setText(String.valueOf(getData().get(position)));
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }


    public List<T> getSuperData() {
        return super.getData();
    }


    public class MyFilter extends Filter {
   /*     public void setSuperDataLiSt(List superDataLiSt) {
            this.superDataLiSt = superDataLiSt;
        }

        private List<t> superDataLiSt;

        public MyFilter(List<t> superData) {
            this.superDataLiSt = superData;
        }*/

        public MyFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> newData = new ArrayList<>();
            List<T> containData = new ArrayList<>();
            List<T> superData = getSuperData();
            for (T currentItem : superData) {
                if (currentItem.toString().startsWith(constraint.toString())) {
                    newData.add(currentItem);
                } else {
                    if (currentItem.toString().contains(constraint)) {
                        containData.add(currentItem);
                    }
                }
             /*   if (currentItem.toString().contains(constraint)) {
                    newData.add(currentItem);
                }*/
            }

            LinkedHashSet hashSet = new LinkedHashSet();
            if(!containData.isEmpty()){
                newData.addAll(containData);
            }
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
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
//        myFilter.setSuperDataLiSt(getSuperData());
        return myFilter;
    }
    @Override
    public List<T> getData() {
        if (datafilter != null && !datafilter.isEmpty()) {
            return datafilter;
        }
        return super.getData();
    }

    public static class GenericDataBindViewHolder implements BaseViewHolderI {

        private final View rootview;

        public TextView getTextView() {
            return textView;
        }

        private final TextView textView;

        public GenericDataBindViewHolder(View inflate) {
            this.rootview =inflate;
            textView = (TextView) inflate.findViewById(android.R.id.text1);
            textView.setSelected(true);//marquee
        }

        @Override
        public View getItemView() {
            return rootview;
        }
    }
}
