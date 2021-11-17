package com.example.editablespinner;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import lozn.spinner.EditSpinner;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/11/17
 * 17:18
 */
public class JavaTest  extends Activity {
    public void test(){
        EditSpinner editSpinner=null;
        editSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editSpinner.setSelection(-1);
        String text = editSpinner.getText();//设置-1清空文本，  这里将得到空

        editSpinner.setOnValueChangeListener(new EditSpinner.OnValueChangeListener() {
            @Override
            public void onLossFocus() {

            }

            @Override
            public void onLossFocusAndTextChange() {
                    //spiner失去焦点，并且内容发生改变的时候触发
            }

            @Override
            public void onTextChanged(CharSequence s) {

            }

            @Override
            public void onGainFocus() {

            }

            /**
             * 返回true更新 状态，那么基本上不会调用  onLossFocusAndTextChange
             * @param position
             * @param selectedItem
             * @return
             */
            @Override
            public boolean onItemSelectPostionChanged(int position, String selectedItem) {
                return false;
            }
        });



    }
}
