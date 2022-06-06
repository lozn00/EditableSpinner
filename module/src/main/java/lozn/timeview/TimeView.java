package lozn.timeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import lozn.spinner.R;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/10/29
 * 9:37
 */
@Keep
public class TimeView extends LinearLayout implements TextWatcher {

    private static final int DATE = 0;
    private static final int TIME = 1;
    private EditText editText;
    private ImageView imageView;
    private boolean rebuildID = true;
    private long saveTime = System.currentTimeMillis();
    private final String format_date = "yyyy-MM-dd";
    private final String format_time = "yyyy-MM-dd HH:mm:ss";
    private String format = format_date;
    private int mode;

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    /**
     * 这是一个tagert可以用来判断是否改变来控制是否重新拉取数据!
     */
    private String belong;


    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    private TextInputLayout textInputLayout;

    public TimeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER_VERTICAL);
        //https://github.com/jzcruiser/firefox/blob/e1319f0458078f24ab9b82fb6882418f49114b2e/mobile/android/base/MultiChoicePreference.java
        ViewGroup edit_layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.timeview_layout, this, false);//add view

        //edittview
        editText = (EditText) edit_layout.findViewById(R.id.date_edittext);
        textInputLayout = edit_layout.findViewById(R.id.date_textinput_layout);
//        textInputLayout.setId(getId());
        textInputLayout.setBoxStrokeWidthFocused(0);
        textInputLayout.setBoxStrokeWidth(0);
        String hint_str = "";
        int gap = 0;
        editText.setBackgroundColor(Color.TRANSPARENT);
        textInputLayout.setBackgroundColor(Color.TRANSPARENT);//清除背景颜色
        imageView = new ImageView(context);
        imageView.setId(R.id.image);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
            int resourceId = typedArray.getResourceId(R.styleable.TimeView_timeview_icon, 0);

            Drawable drawable = null;
            if (resourceId > 0) {
                drawable = AppCompatResources.getDrawable(context, resourceId);
                ;
            } else {//兼容矢量图片
                drawable = AppCompatResources.getDrawable(context, R.drawable.down_arrow);
            }
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            } else {
                imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.down_arrow));
            }
            hint_str = typedArray.getString(R.styleable.TimeView_timeview_hint);
            textInputLayout.setHint(hint_str);
            String value = typedArray.getString(R.styleable.TimeView_timeview_value);
            String temp_format = typedArray.getString(R.styleable.TimeView_timeview_format);
            mode = typedArray.getInt(R.styleable.TimeView_timeview_mode, TimeView.DATE);
            if (!TextUtils.isEmpty(temp_format)) {
                format = temp_format;
            } else {
                if (mode == TIME) {
                    format = format_time;
                }
            }
            boolean editable = typedArray.getBoolean(R.styleable.TimeView_timeview_editable, false);
            rebuildID = typedArray.getBoolean(R.styleable.TimeView_timeview_rebuild_Id, rebuildID);
            gap = typedArray.getDimensionPixelSize(R.styleable.TimeView_timeview_gap, 5);
            editText.setEnabled(editable);
            if (!TextUtils.isEmpty(value)) {
                try {
                    Date parse = new SimpleDateFormat(format).parse(value);
                    saveTime = parse.getTime();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }
            typedArray.recycle();
        } else {

        }
        if (rebuildID) {//重建id,避免fragment重建 后，导致hint内容重复，
            textInputLayout.setId(View.generateViewId());
            imageView.setId(View.generateViewId());
            editText.setId(View.generateViewId());
        }
//        textInputLayout.setId(R.id.text);
        //paddding 清除
        textInputLayout.setPadding(0, 0, 0, 0);
        imageView.setPadding(0, 0, 0, 0);
        //背景清除
//        int minwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
        addView(imageView);
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        modifyLayout(edit_layout, gap);

        editText.setText(getCurrentFormatTime());
        initEvent();


        //        addView(textInputLayout, labelparam);
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public FragmentManager fragmentManager;

    private void onShowTimeView() {
        MaterialDatePicker.Builder<Long> datePickerBuild = MaterialDatePicker.Builder.datePicker();
        datePickerBuild.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        datePickerBuild.setSelection(saveTime);
        onCreateDatePicker(datePickerBuild);
        MaterialDatePicker<Long> picker = datePickerBuild.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(final Long selection) {
                if (mode == TIME) {
                    final Calendar instance = Calendar.getInstance();//会把以前选择的小时给擦掉。
                    instance.setTimeInMillis(saveTime);
                    final MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H).setHour(instance.get(Calendar.HOUR_OF_DAY)).setMinute(instance.get(Calendar.MINUTE)).build();
                    materialTimePicker.clearOnNegativeButtonClickListeners();
                    materialTimePicker.clearOnPositiveButtonClickListeners();
                    onCreateTimePicker(materialTimePicker);
                    materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hour = materialTimePicker.getHour();
                            int minute = materialTimePicker.getMinute();
                            instance.setTimeInMillis(selection);
                            instance.set(Calendar.HOUR_OF_DAY, hour);
                            instance.set(Calendar.MINUTE, minute);
                            nottificationCallBack(instance.getTimeInMillis());
                        }
                    });
                    if (!materialTimePicker.isAdded()) {
                        if (getContext() instanceof FragmentActivity) {
                            FragmentActivity activity = (FragmentActivity) getContext();
                            materialTimePicker.show(activity.getSupportFragmentManager(), "_datetime");
                        } else if (getFragmentManager() != null) {
                            materialTimePicker.show(getFragmentManager(), "_datetime");
                        } else {
                            throw new RuntimeException(" dataview must use at fragmentactivity");
                        }
                    }


                } else {
                    nottificationCallBack(selection);
                }


            }
        });
        if (!picker.isAdded()) {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) getContext();
                picker.show(activity.getSupportFragmentManager(), "date");
            } else if (getFragmentManager() != null) {
                picker.show(getFragmentManager(), "date");
            } else {
                throw new RuntimeException(" dataview must use at fragmentactivity");
            }
        }
    }

    /**
     * 可以控制选择的返回
     *
     * @param materialTimePicker
     */
    protected void onCreateTimePicker(MaterialTimePicker materialTimePicker) {

    }

    protected void onCreateDatePicker(MaterialDatePicker.Builder<Long> datePicker) {

    }

    private void nottificationCallBack(Long selection) {
        saveTime = selection;
        String formatTime = getCurrentFormatTime();
        editText.removeTextChangedListener(TimeView.this);
        editText.setText(formatTime);
        editText.addTextChangedListener(TimeView.this);

        if (onTimeChangeListener != null) {
            onTimeChangeListener.onTimeChange(formatTime, saveTime);
        }
    }

    private String getCurrentFormatTime() {
        String formatTime;
        try {
            formatTime = new SimpleDateFormat(TimeView.this.format).format(saveTime);

        } catch (Throwable e) {
            e.printStackTrace();
            formatTime = new SimpleDateFormat(TimeView.this.format_date).format(saveTime);

        }
        return formatTime;
    }

    private void modifyLayout(View edit_layout, int gap) {

        LayoutParams paramArrowDown = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramArrowDown.gravity = Gravity.CENTER;
        imageView.setMinimumWidth(30);
        imageView.setLayoutParams(paramArrowDown);
        LayoutParams edittextparam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        edittextparam.width = 0;
        edittextparam.weight = 1;
        edittextparam.gravity = Gravity.CENTER;
        edittextparam.rightMargin = gap;
        addView(edit_layout, 0, edittextparam);


    }

    private void initEvent() {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowTimeView();
            }
        });

        editText.addTextChangedListener(this);
    }

    String focusText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            Date date = new SimpleDateFormat(TimeView.this.format).parse(s + "");
            saveTime = date.getTime();
            textInputLayout.setErrorEnabled(false);
        } catch (ParseException e) {
            e.printStackTrace();
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("格式错误");
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public String getText() {
        return editText.getText().toString();
    }


    public interface onTimeChangeListener {
        void onTimeChange(CharSequence s, long time);
    }

    public void setOnTimeChangeListener(TimeView.onTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    onTimeChangeListener onTimeChangeListener;

    public EditText getEditText() {
        return editText;
    }

}