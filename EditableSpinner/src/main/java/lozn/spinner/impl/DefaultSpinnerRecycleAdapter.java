package lozn.spinner.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import lozn.spinner.BaseViewHolderI;
import lozn.spinner.base.ListViewRecycleAdapter;
import lozn.spinner.R;


public class DefaultSpinnerRecycleAdapter<T>  extends ListViewRecycleAdapter<T, DefaultSpinnerRecycleAdapter.GenericDataBindViewHolder> {
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

    @Override
    public Filter getFilter() {
        return null;
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
