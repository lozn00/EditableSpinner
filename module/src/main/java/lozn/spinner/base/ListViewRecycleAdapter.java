package lozn.spinner.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import lozn.spinner.BaseViewHolderI;
import lozn.spinner.OnItemLongClickListener;

/**
 * Created by luozheng on 2015/11/7.
 * update 2016-01-07 15:10:03
 */
public abstract class ListViewRecycleAdapter<T, VH extends BaseViewHolderI> extends FilterableBaseAdapter {
    public List<T> data;
    public int mPage;
    private String TAG = "DefaultAdapter";
    public ListViewRecycleAdapter() {
    }
    public List<T> getData() {
        return this.data;
    }
    public ListViewRecycleAdapter<T, VH> setData(List<T> data) {
        Log.i(TAG, "parent method call");
        this.data = data;
        return this;
    }
    public ListViewRecycleAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        VH viewHolder;
        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent, getItemViewType(position));//请再里面给itenview赋值。。。
            viewHolder.getItemView().setTag(viewHolder);

        } else {
            viewHolder = (VH) convertView.getTag();
            onConvertView(convertView, viewHolder);
        }
        onBindViewHolder(viewHolder, position);
        if (onItemLongClickListener != null) {
            viewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return onItemLongClickListener.onItemLongClick((ViewGroup) convertView, v, position);
                }
            });
        }
        return viewHolder.getItemView();
    }

    public void onConvertView(View convertView, VH vh) {

    }



    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    OnItemLongClickListener onItemLongClickListener;

    /**
     * 创建一个viewholder,并给view的itemview赋值
     *
     * @return
     */
    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    //viewholder
    public abstract void onBindViewHolder(VH viewHolder, int position);

    @Override
    public Object getItem(int position) {
        return getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

//    public DefaultAdapter() {
//    }
}