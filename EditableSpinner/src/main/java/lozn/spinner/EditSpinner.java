
import static android.widget.AdapterView.INVALID_POSITION;
import static android.widget.Spinner.MODE_DIALOG;
import static android.widget.Spinner.MODE_DROPDOWN;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import lozn.spinner.EditInnerSpinner;
import lozn.spinner.R;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/10/29
 * 9:37
 */

public class EditSpinner extends LinearLayout {
    private static final int LAYOUT_MODE_EXPAND = 0;
    private static final int LAYOUT_MODE_INNER = 1;
    private static final int LAYOUT_MODE_TOP_LABEL_ARROW = 2;
    private static final int LAYOUT_MODE_LEFT_LABEL = 3;
    private LinearLayout _titleWrap;
    private TextView _tvLabel;
    @LayoutMode
    private int layoutMode = LAYOUT_MODE_TOP_LABEL_ARROW;
    private boolean editable = false;

    @IntDef({LAYOUT_MODE_EXPAND, LAYOUT_MODE_INNER, LAYOUT_MODE_TOP_LABEL_ARROW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutMode {
    }

    private TextView editText;
    private EditInnerSpinner appCoSpinner;
    private ImageView imageView;
    private OnSpinnerClickListener spinnerClickListener;
    private boolean rebuildID = false;
    private ZTextWatcher watcher;
    private boolean _defaultChoose = true;
    private ViewGroup edit_layout;
    private AdapterView.OnItemSelectedListener onItemSelectedListenerInner;

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

    public EditInnerSpinner getAppCompatSpinner() {
        return appCoSpinner;
    }


    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    private TextInputLayout textInputLayout;

    public EditSpinner(Context context) {
        super(context);
        init(context, null, 0);
    }

    public EditSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public EditSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initWithParam(context, attrs, defStyleAttr);

    }

    protected void initWithParam(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER_VERTICAL);
        //https://github.com/jzcruiser/firefox/blob/e1319f0458078f24ab9b82fb6882418f49114b2e/mobile/android/base/MultiChoicePreference.java
        String hint_str = "";
        int gap = 0;
        imageView = new ImageView(context);
        imageView.setId(pub.devrel.easypermissions.R.id.image);
        CharSequence[] mEntries = null;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditSpinner);
            mEntries = typedArray.getTextArray(R.styleable.EditSpinner_spinner_item);
            int resourceId = typedArray.getResourceId(R.styleable.EditSpinner_spinner_icon, R.drawable.ic_dropdown_selector);
            int mode = typedArray.getInt(R.styleable.EditSpinner_spinner_mode, MODE_DROPDOWN);
            layoutMode = typedArray.getInt(R.styleable.EditSpinner_spinner_layout_mode, layoutMode);
            _defaultChoose = typedArray.getBoolean(R.styleable.EditSpinner_spinner_defaultchoose, _defaultChoose);
            editable = typedArray.getBoolean(R.styleable.EditSpinner_spinner_editable, editable);
            findView(context, layoutMode, editable);

            if (mode == MODE_DIALOG) {
                //REBUILD VIEW 或者在这之前就重新加载布局
                ViewGroup spinnerContainer = edit_layout.findViewById(R.id.spinner_container);
                spinnerContainer.removeAllViews();
                appCoSpinner = new EditInnerSpinner(context, mode);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.BOTTOM;
                appCoSpinner.setVisibility(INVISIBLE);
                spinnerContainer.addView(appCoSpinner, params);

            }

            Drawable drawable;
            if (resourceId > 0) {
                drawable = AppCompatResources.getDrawable(context, resourceId);
                ;
            } else {//兼容矢量图片
                drawable = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_down_gray);
            }

            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            hint_str = typedArray.getString(R.styleable.EditSpinner_spinner_hint);
            appCoSpinner.setPrompt(hint_str);
            if (layoutMode == LAYOUT_MODE_TOP_LABEL_ARROW) {
                _tvLabel.setText(hint_str);
            } else {
                textInputLayout.setHint(hint_str);
            }
            String value = typedArray.getString(R.styleable.EditSpinner_spinner_value);
            rebuildID = typedArray.getBoolean(R.styleable.EditSpinner_spinner_rebuild_Id, rebuildID);
            gap = typedArray.getDimensionPixelSize(R.styleable.EditSpinner_spinner_gap, 5);
            if (editText instanceof EditableEdittext) {
                ((EditableEdittext) editText).setEditable(editable);

            }
            if (!TextUtils.isEmpty(value)) {
                setSpinnerText(value);
            }
            typedArray.recycle();
        } else {
            findView(context, layoutMode, false);
        }

        if (rebuildID) {//重建id,避免fragment重建 后，导致hint内容重复，这种情况在viewpager里面使用才会出现。
            if (textInputLayout != null) {
                textInputLayout.setId(View.generateViewId());
            } else if (_titleWrap != null) {
                _titleWrap.setId(View.generateViewId());
                _tvLabel.setId(View.generateViewId());
            }
            imageView.setId(View.generateViewId());
            editText.setId(View.generateViewId());
            appCoSpinner.setId(View.generateViewId());
        }
//        textInputLayout.setId(R.id.text);
        //paddding 清除
        if (textInputLayout != null) {
            textInputLayout.setPadding(0, 0, 0, 0);
        }
        imageView.setPadding(0, 0, 0, 0);
        //背景清除
//        int minwidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());

        if (layoutMode == LAYOUT_MODE_EXPAND) {
            addView(imageView);

        } else if (layoutMode == LAYOUT_MODE_INNER) {
            edit_layout.addView(imageView);

        } else if (layoutMode == LAYOUT_MODE_TOP_LABEL_ARROW) {
            _titleWrap.addView(imageView);
        }
        onItemSelectedListenerInner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.w("focus", editText.hasFocus() + ","+","+editText.isFocused()+","+editText.isAccessibilityFocused());
                if (position < 0) {//|| appCoSpinner.getMyselection() < 0) { //无法做到默认为空。没办法
                    if (!TextUtils.isEmpty(editText.getText().toString())) {
                        setTextDisableNotify("");
                    } else {

                    }
                    onNothingSelected(parent);
                    return;
                }
                appCoSpinner.setMyselection(position);
                Object selectedItem = appCoSpinner.getSelectedItem();
                String beforeText = editText.getText().toString();
                setTextDisableNotify(String.valueOf(selectedItem));
                if (editText instanceof EditText) {
                    ((EditText) editText).selectAll();

                }
//                editText.requestFocus();
                if (EditSpinner.this.onItemSelectedListener != null) {
                    EditSpinner.this.onItemSelectedListener.onItemSelected(parent, view, position, id);
                }
                if (onValueChangeListener != null && beforeText != null && (!beforeText.equals(selectedItem + ""))) {
                    if (onValueChangeListener.onItemSelectPostionChanged(position, String.valueOf(selectedItem))) {
                        focusText = String.valueOf(selectedItem);
                    } else {
                        if (editText.isFocused()) {
                            focusText = String.valueOf(selectedItem);
                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                EditSpinner.this.onItemSelectedListener.onNothingSelected(parent);
            }
        };
//        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        if (mEntries != null && mEntries.length > 0) {
            if (!isInEditMode()) {
                MySpinnerAdapter<CharSequence, SimpleSpinnerItemBinding> adapter = new MySpinnerAdapter<>();
                ArrayList<CharSequence> data = new ArrayList<>();
                for (CharSequence mEntry : mEntries) {
                    data.add(mEntry);
                }
                adapter.setData(data);
                setAdapter(adapter, false);

            }
        }


        //set layout param
        modifyLayout(edit_layout, gap, layoutMode, editText);
        initMy2Event();


        //        addView(textInputLayout, labelparam);
    }

    private void findView(Context context, int layoutMode, boolean editable) {
        //add view
        switch (layoutMode) {
            case LAYOUT_MODE_INNER:
            case LAYOUT_MODE_EXPAND:
                edit_layout = (ViewGroup) LayoutInflater.from(context).inflate(editable ? R.layout.edit_autotip_spinner_edit_layout : R.layout.edit_spinner_edit_layout, this, false);
                textInputLayout = edit_layout.findViewById(R.id.spinner_textinput_layout);
                break;
            case LAYOUT_MODE_TOP_LABEL_ARROW:
                edit_layout = (ViewGroup) LayoutInflater.from(context).inflate(editable ? R.layout.edit_spinner_label_layout : R.layout.edit_spinner_label_disable_edit_layout, this, false);
                _titleWrap = (LinearLayout) edit_layout.findViewById(R.id.title_wrap);
                _tvLabel = (TextView) _titleWrap.findViewById(R.id.tv_label);
                break;
            default:
                break;
        }
        editText = edit_layout.findViewById(R.id.spinner_edittext);
        appCoSpinner = edit_layout.findViewById(R.id.spinner_inner);
    }

    private void setSpinnerText(String value) {
        if (!editText.getText().toString().equals(value)) {//autocompletetext 设置了adapter,adapter又重新设置text.
            if (editText instanceof AutoCompleteTextView) {
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
                autoCompleteTextView.setThreshold(1000);
                editText.setText(value);
                editText.post(() -> autoCompleteTextView.setThreshold(1));//避免提示在选择spinner的内容的时候又一次出现。

                return;
//                autoCompleteTextView.enoughToFilter(1000);

            }
            editText.setText(value);//Attempt to invoke interface method 'void android.text.TextWatcher.beforeTextChanged(java.lang.CharSequence, int, int, int)
        }
    }

    public static int dip2px(Context context, float dpValue) {
     /*   final float scale = context.getResources().getDisplayMetrics().density;//density=dpi/160
        return (int) (dpValue * scale + 0.5f);
*/
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpValue, context.getResources().getDisplayMetrics());
    }

    private void modifyLayout(View edit_layout, int gap, int layoutMode, TextView editText) {

        int minBox = dip2px(edit_layout.getContext(), 30);
        if (layoutMode == LAYOUT_MODE_TOP_LABEL_ARROW) {
            LinearLayout.LayoutParams paramArrowDown = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            paramArrowDown.gravity = Gravity.CENTER;
            imageView.setMinimumWidth(minBox);
            imageView.setMinimumHeight(minBox);
            int padding = dip2px(edit_layout.getContext(), 5);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setLayoutParams(paramArrowDown);

        } else if (layoutMode == LAYOUT_MODE_EXPAND) {
            LinearLayout.LayoutParams paramArrowDown = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramArrowDown.gravity = Gravity.CENTER;
            imageView.setMinimumWidth(minBox);
            imageView.setMinimumHeight(minBox);
            int padding = dip2px(edit_layout.getContext(), 5);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setLayoutParams(paramArrowDown);

        } else if (layoutMode == LAYOUT_MODE_INNER) {
            FrameLayout.LayoutParams paramArrowDown = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramArrowDown.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
            paramArrowDown.rightMargin = dip2px(edit_layout.getContext(), 4);
            imageView.setMinimumWidth(minBox);
            imageView.setMinimumHeight(minBox);
            int width = imageView.getWidth();
            if (width == 0) {
                imageView.measure(0, 0);
                width = imageView.getMeasuredWidth();
            }
            width = width + paramArrowDown.rightMargin;
            editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), width, editText.getPaddingBottom());
            imageView.setLayoutParams(paramArrowDown);

        }

        LinearLayout.LayoutParams edittextparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        edittextparam.width = 0;
        edittextparam.weight = 1;
        edittextparam.gravity = Gravity.CENTER;
        edittextparam.rightMargin = gap;
        addView(edit_layout, 0, edittextparam);
    }

    private void initMy2Event() {

        if (editText instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
            autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                SpinnerAdapter adapter = appCoSpinner.getAdapter();
                if (adapter instanceof DefaultListViewAdapter) { //创建一个新的adapter,是为了解决重复触发问题，如果不创建一个新的，autocompletetextview继续用之前的，输入2个之后会自动触发select 而且触发两次，实际上不应该进行触发只需要进行提示。

                    DefaultListViewAdapter editTextAdapter = (DefaultListViewAdapter) ((AutoCompleteTextView) editText).getAdapter();
                    DefaultListViewAdapter<?, ?> defaultListViewAdapter = (DefaultListViewAdapter<?, ?>) adapter;
                    defaultListViewAdapter.setData(editTextAdapter.getData());
                    defaultListViewAdapter.notifyDataSetChanged();
                } else {
                    throw new RuntimeException("spinner   DefaultListViewAdapter adapter is aspected!");
                }
                focusText = editText.getText().toString();//避免失去焦点触发onTextChange.
                appCoSpinner.setSelection(position);
                appCoSpinner.setMyselection(position);
                if (onValueChangeListener != null) {
                    onValueChangeListener.onTextAutoCompleteChoose(position, id);
                }
//                    onItemSelectedListenerInner.onItemSelected(parent, view, position, id);

            });
        }
        appCoSpinner.setOnItemSelectedListener(onItemSelectedListenerInner);
        View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerClickListener != null && spinnerClickListener.onClickConsumed()) {
                    return;
                }
                if (appCoSpinner.getSelectedItemPosition() == 0) {
                    Object selectedItem = appCoSpinner.getSelectedItem();
                    if (selectedItem != null) {
                        setTextDisableNotify(selectedItem + "");//当clearText的时候又选中当前的时候，不会触发onOptionChange,所以点击就自动给它设置吧。没其他更好的办法了!

                    }
                }
                appCoSpinner.performClick();
            }
        };
        if (layoutMode == LAYOUT_MODE_TOP_LABEL_ARROW) {
            _titleWrap.setOnClickListener(onClickListener);

        } else {
            imageView.setOnClickListener(onClickListener);
        }


  /*      dropDownView.setOnClickListener(v -> {
            if (spinnerClickListener != null && spinnerClickListener.onClick()) {
                return;
            }
            if (appCoSpinner.getSelectedItemPosition() == 0) {
                Object selectedItem = appCoSpinner.getSelectedItem();
                if (selectedItem != null) {
                    setTextNoNotify(selectedItem + "");//当clearText的时候又选中当前的时候，不会触发onOptionChange,所以点击就自动给它设置吧。没其他更好的办法了!

                }
            }
            appCoSpinner.performClick();
        });*/
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (onValueChangeListener != null) {
                return onValueChangeListener.onEditorAction(v, actionId, event);
            }
            return false;
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusText = editText.getText().toString();
                if (onValueChangeListener != null) {
                    onValueChangeListener.onGainFocus(focusText);
                }
            } else {
                if (onValueChangeListener != null) {
                    onValueChangeListener.onLossFocus();
                /*    if (appCoSpinner.getMyselection() != -1) {
                        Object text = appCoSpinner.getSelectedItem();
                        if(text!=null&&!text.toString().equals(editText.getText().toString())){

                        }
                    }*/
                    if (!editText.getText().toString().equals(focusText)) {
                        focusText = editText.getText().toString();
                        if (onValueChangeListener != null) {
                            onValueChangeListener.onLossFocusAndTextChange();
                        }
                    }
                }
            }

        });
        watcher = new ZTextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!this.isAllowNotify()) {
                    return;
                }
                if (appCoSpinner.getAdapter() != null) {
                    SpinnerAdapter adapter = appCoSpinner.getAdapter();
                    int count1 = adapter.getCount();
                    boolean find = false;
                    for (int i = 0; i < count1; i++) {
                        Object item = adapter.getItem(i);
                        if (item != null & String.valueOf(item).equals(s.toString())) {
                            appCoSpinner.setMyselection(i);
                            find = true;
                        }
                    }
                    if (!find) {
                        appCoSpinner.setMyselection(-1);
                    }
                }
                if (onValueChangeListener != null) {
                    onValueChangeListener.onTextChanged(s);
                }
            }

        };
        editText.addTextChangedListener(watcher);
    }

    String focusText;

    public Context getPopupContext() {
        return appCoSpinner.getPopupContext();
    }

    public void setPopupBackgroundDrawable(Drawable background) {
        appCoSpinner.setPopupBackgroundDrawable(background);
    }

    public void setPopupBackgroundResource(int resId) {
        appCoSpinner.setPopupBackgroundResource(resId);
    }

    public Drawable getPopupBackground() {
        return appCoSpinner.getPopupBackground();
    }

    public void setDropDownVerticalOffset(int pixels) {
        appCoSpinner.setDropDownVerticalOffset(pixels);
    }

    public int getDropDownVerticalOffset() {
        return appCoSpinner.getDropDownVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int pixels) {
        appCoSpinner.setDropDownHorizontalOffset(pixels);
    }

    public int getDropDownHorizontalOffset() {
        return appCoSpinner.getDropDownHorizontalOffset();
    }

    public void setDropDownWidth(int pixels) {
        appCoSpinner.setDropDownWidth(pixels);
    }

    public int getDropDownWidth() {
        return appCoSpinner.getDropDownWidth();
    }

    /**
     * Caused by: java.lang.RuntimeException: setOnItemClickListener cannot be used with a spinner.
     *
     * @param l
     */
    @Deprecated
    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
//        appCoSpinner.setOnItemClickListener(l);
    }

    public void setPrompt(CharSequence prompt) {
        appCoSpinner.setPrompt(prompt);
    }

    public void setPromptId(int promptId) {
        appCoSpinner.setPromptId(promptId);
    }

    public CharSequence getPrompt() {
        return appCoSpinner.getPrompt();
    }

    public void setSelection(int position, boolean animate) {
        appCoSpinner.setSelection(position, animate);
    }

    public void setSelection(int position) {
        appCoSpinner.setSelection(position);
    }

    public View getSelectedView() {
        return appCoSpinner.getSelectedView();
    }

    public SpinnerAdapter getAdapter() {
        return appCoSpinner.getAdapter();
    }

    public int getCount() {
        return appCoSpinner.getCount();
    }

    public boolean performItemClick(View view, int position, long id) {
        return appCoSpinner.performItemClick(view, position, id);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        appCoSpinner.setOnItemLongClickListener(listener);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public String getText() {

        String selectedItem = editText.getText().toString();
        return selectedItem;
    }

    public void clearSelect() {
        setTextDisableNotify("");
        appCoSpinner.setMyselection(-1);
        appCoSpinner.setSuperSectionAndDisableNotify(-1);
        /**
         * 虽然能改变选中，但是是无效的，当弹出的时候选中之前的依然不会触发，说明没有取消 调用super是为了防止又触发一次onItemSelection
         */

    }

    /**
     * 点了这个下拉列表箭头
     *
     * @param spinnerClickListener
     */
    public void setOnSpinnerClickListener(OnSpinnerClickListener spinnerClickListener) {
        this.spinnerClickListener = spinnerClickListener;
    }

    public boolean isEmptyList() {
        return getAdapter() == null || getAdapter().isEmpty();
    }

    public void setText(String text) {
        setSpinnerText(text);
        appCoSpinner.setSuperSectionAndDisableNotify(-1);
    }

    public CharSequence getHint() {
        if (_tvLabel != null) {
            return _tvLabel.getText();
        }
        return textInputLayout.getHint();
    }

    public void clearData() {

        setAdapter(null);
        setText("");
    }

    public void setData(List data) {
        if (isInEditMode()) {
            return;
        }
        MySpinnerAdapter<Object, SimpleSpinnerItemBinding> adapter = new MySpinnerAdapter<>();
        adapter.setData(data);
        setAdapter(adapter);
    }

    /**
     * 当做普通编辑框用。
     */
    public void hideSpinner() {
        imageView.setVisibility(View.GONE);
    }


    public interface OnSpinnerClickListener {
        /**
         * 返回true,表示拦截系统的，不让逻辑继续往下走!
         *
         * @return
         */
        boolean onClickConsumed();

    }

    public interface OnValueChangeListener {
        void onLossFocus();

        void onLossFocusAndTextChange();

        void onTextAutoCompleteChoose(int position, long id);

        void onTextChanged(CharSequence s);

        void onGainFocus(String focusText);

        /**
         * return true  change focusText status 也就是说失去焦点就不会触发了 //item选择触发后 那么失去焦点的改变就不会触发了。，
         *
         * @param position
         * @param selectedItem
         * @return
         */
        boolean onItemSelectPostionChanged(int position, String selectedItem);

        boolean onEditorAction(TextView v, int actionId, KeyEvent event);
    }

    public OnValueChangeListener getOnValueChangeListener() {
        return onValueChangeListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public static class SimpleOnValueChangeListener implements OnValueChangeListener {

        @Override
        public void onLossFocus() {

        }

        @Override
        public void onLossFocusAndTextChange() {

        }

        @Override
        public void onTextAutoCompleteChoose(int position, long id) {

        }


        @Override
        public void onTextChanged(CharSequence s) {

        }

        @Override
        public void onGainFocus(String focusText) {

        }

        @Override
        public boolean onItemSelectPostionChanged(int position, String selectedItem) {
            return false;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            return false;
        }
    }

    OnValueChangeListener onValueChangeListener;

    public int getSelectedItemPosition() {
      /*  if (appCoSpinner.getMyselection() == INVALID_POSITION) { //由于无法选择默认这里不能返回-1 ，否则出大事情
            return INVALID_POSITION;
        }*/
        return appCoSpinner.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        return appCoSpinner.getSelectedItemId();
    }

    public String getSelectedItem() {
       /* if(appCoSpinner.getSelectedItemPosition()<=0){
            return editText.getText().toString();//清空选择的时候返回空
        },*/
        Object selectedItem = appCoSpinner.getSelectedItem();
        if (appCoSpinner.getSelectedItemPosition() < 0 || selectedItem == null) {
            if (!TextUtils.isEmpty(editText.getText())) {
                return editText.getText().toString();
            } else {
                return "";
            }
        }
        return selectedItem + "";
    }

    public Object getSelectedItemObj() {
        if (appCoSpinner.getMyselection() == -1 && TextUtils.isEmpty(editText.getText().toString())) {
            return null;
        }
        Object selectedItem = appCoSpinner.getSelectedItem();
        return selectedItem;
    }

    public int getPositionForView(View view) {
        return appCoSpinner.getPositionForView(view);
    }

    public int getFirstVisiblePosition() {
        return appCoSpinner.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        return appCoSpinner.getLastVisiblePosition();
    }

    public void setEmptyView(View emptyView) {
        appCoSpinner.setEmptyView(emptyView);
    }

    public View getEmptyView() {
        return appCoSpinner.getEmptyView();
    }


    public Object getItemAtPosition(int position) {
        return appCoSpinner.getItemAtPosition(position);
    }

    public long getItemIdAtPosition(int position) {
        return appCoSpinner.getItemIdAtPosition(position);
    }

    /*
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        appCompatSpinner.setOnClickListener(l);
    }*/

    public void setAdapter(SpinnerAdapter adapter) {
        setAdapter(adapter, true);
    }

    public void setAdapter(SpinnerAdapter adapter, boolean choose) {
        setAdapter(adapter, choose ? 0 : -1);
    }

    public void setAdapter(SpinnerAdapter adapter, int position) {

        if (position > 0) {
            _defaultChoose = true;
        }
        if (editText instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
            if (adapter instanceof ListAdapter && adapter instanceof Filterable) {
                if (!isInEditMode()) {
                    MySpinnerAdapter mySpinnerAdapter = (MySpinnerAdapter) adapter;

                    autoCompleteTextView.setAdapter(new MySpinnerAdapter<>().setData(mySpinnerAdapter.getData()));  // 设置适配器

                }

            }

        }
        int count = adapter == null ? 0 : adapter.getCount();
        appCoSpinner.setAdapter(adapter);
        /** 设置adapter之后只要item>)永远选择第0 个
         *   int position = mItemCount > 0 ? 0 : INVALID_POSITION;
         *
         *             setSelectedPositionInt(position);
         *             setNextSelectedPositionInt(position);
         */

        if (position >= 0 && adapter != null && position < adapter.getCount()) {

            if (count > 0) {
                Object item = adapter.getItem(position);
                doDefaultChoose(position, count, item + "");
            } else {
                doDefaultChoose(count, "");
            }
        } else {
            if (!TextUtils.isEmpty(getText())) {
                setTextDisableNotify("");
            }
        }
    }

    private void doDefaultChoose(int count, Object s) {
        doDefaultChoose(0, count, s);
    }

    private void doDefaultChoose(int pos, int count, Object s) {

        if (_defaultChoose) {
            setTextDisableNotify(s == null ? "" : s.toString());
        } else {
            setTextDisableNotify("");

        }
        if (_defaultChoose && count > 0) {
            if (appCoSpinner.getSelectedItemPosition() != pos) {
                appCoSpinner.setSuperSectionAndDisableNotify(pos);
            }
            appCoSpinner.setMyselection(pos);
        } else {
//            appCoSpinner.setSuperSection(choosePos);
            if (appCoSpinner.getSelectedItemPosition() != INVALID_POSITION) {
                appCoSpinner.setSuperSectionAndDisableNotify(-2);//设置是无效的，默认还是会调用0
                //https://blog.csdn.net/iteye_2125/article/details/82212857
            }
            appCoSpinner.setMyselection(INVALID_POSITION);

        }

    }

    private static class ZTextWatcher implements TextWatcher {
        public boolean isAllowNotify() {
            return allowNotify;
        }

        public void setAllowNotify(boolean allowNotify) {
            this.allowNotify = allowNotify;
        }

        boolean allowNotify;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void setTextDisableNotify(String s) {
        if (s == null) {
            s = "";
        }
//        editText.removeTextChangedListener(watcher);
        if (watcher != null) {
            watcher.setAllowNotify(false);//直接移除会导致系统内部空指针。 原来原因就是watcher本身还没初始化
        }
        setSpinnerText(s);
        if (watcher != null) {
            watcher.setAllowNotify(true);
        }
//        editText.addTextChangedListener(watcher);
    }

    public TextView getEditText() {
        return editText;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled == isEnabled()) return;
        if (editText != null && imageView != null) {
            editText.setEnabled(enabled);
            imageView.setEnabled(enabled);
            if (textInputLayout != null) {
                textInputLayout.setEnabled(enabled);
            } else if (_titleWrap != null) {
                _titleWrap.setEnabled(enabled);
            }

        }
//        setFlags(enabled ? ENABLED : DISABLED, ENABLED_MASK);
        super.setEnabled(enabled);
    }
}