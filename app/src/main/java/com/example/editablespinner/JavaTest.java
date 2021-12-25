package com.example.editablespinner;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import lozn.spinner.EditSpinner;
import lozn.timeview.TimeView;

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
            public void onGainFocus(String focusText) {

            }

            /*@Override
            public void onGainFocus() {

            }
*/
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

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        TimeView timeView=null;
        timeView.setFragmentManager(null);//if context not fragment act need set ,
        timeView.setOnTimeChangeListener(new TimeView.onTimeChangeListener() {
            //format time  time
            @Override
            public void onTimeChange(CharSequence s, long time) {
        //时间
            }

        });
        new TimeView(null){
            @Override
            protected void onCreateDatePicker(MaterialDatePicker.Builder<Long> datePicker) {
                //自定义 样式
            }

            @Override
            protected void onCreateTimePicker(MaterialTimePicker materialTimePicker) {
                //自定义 样式
            }
        };


    }
}
