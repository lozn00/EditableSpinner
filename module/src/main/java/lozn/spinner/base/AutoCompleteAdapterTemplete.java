package lozn.spinner.base;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2022/1/23
 * 11:03
 */
// extends ListAdapter & Filterable
public abstract class AutoCompleteAdapterTemplete implements ListAdapter, SpinnerAdapter, Filterable {
    @Override
    public Filter getFilter() {
        return null;
    }
}
