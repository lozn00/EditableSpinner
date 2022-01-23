package lozn.biz;

import java.util.ArrayList;

import lozn.spinner.EditSpinner;
import lozn.spinner.impl.DefaultSpinnerRecycleAdapter;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/12/2
 * 9:09
 */
public class BizUtil {
    public static void genereateAndSetDefaultAdapter(EditSpinner editSpinner, CharSequence[] mentries) {
        String []mentriesx=new String[]{"a2","a","f","bbc","12345"};
        DefaultSpinnerRecycleAdapter<CharSequence> adapter = new DefaultSpinnerRecycleAdapter<>();
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
