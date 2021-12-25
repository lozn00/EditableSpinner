package lozn.biz;

import java.util.ArrayList;

import lozn.spinner.EditSpinner;
import lozn.spinner.MySpinnerRecycleAdapter;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/12/2
 * 9:09
 */
public class BizUtil {
    public static void genereateDefaultAdapter(EditSpinner editSpinner, CharSequence[] mentries) {
        MySpinnerRecycleAdapter<CharSequence> adapter = new MySpinnerRecycleAdapter<>();
        ArrayList<CharSequence> data = new ArrayList<>();
        for (CharSequence sequence : mentries) {
            data.add(sequence);
        }
        adapter.setData(data);
        editSpinner.setAdapter(adapter);
        /*

            MySpinnerAdapter<CharSequence, ViewDataBinding> adapter = new MySpinnerAdapter<>();
            ArrayList<CharSequence> data = new ArrayList<>();
            for (CharSequence mEntry : mEntries) {
                data.add(mEntry);
            }
            adapter.setData(data);
            setAdapter(adapter);

         */
    }
}
