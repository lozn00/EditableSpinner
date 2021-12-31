package lozn.spinner;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/11/30
 * 12:00
 */
@Keep
public class EditableEdittext extends TextInputEditText {
    public EditableEdittext(@NonNull Context context) {
        super(context);
    }


    public EditableEdittext(@NonNull Context context, boolean editable) {
        super(context);

    }

    public EditableEdittext(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditableEdittext(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isInEditMode() {
        return editable;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text,editable? BufferType.EDITABLE:BufferType.NORMAL);
    }

    @Nullable
    @Override
    public Editable getText() {


        Editable text = super.getText();
        /*f (!editable) {
            super.setText(text, BufferType.NORMAL);
        }*/

        return text;
    }
    /*
        @Override
        public boolean isInEditMode() {
            return false;
        }*/
//    @Override
/*
    protected boolean supportsAutoSizeText() {
        return editable;
    }
*/

    @Override
    public boolean getFreezesText() {
        return true;
    }

    /**
     * 由于是之前就有了，很难控制。
     * @return
     */
    @Override
    protected boolean getDefaultEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if(!this.editable){
        setInputType(InputType.TYPE_NULL);//来禁止手机软键盘

        }
//        editText.setInputType(InputType.TYPE_CLASS_TEXT);//来开启软键盘
    }

    boolean editable = true;
}
