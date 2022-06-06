package lozn.spinner;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 修复为空点击了后导致卡死问题 andorid10可复现
 * 2021/11/8
 * 15:34
 */
public class FixBugSpinner extends AppCompatSpinner {
    public FixBugSpinner(@NonNull Context context) {
        super(context);
    }

    public FixBugSpinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public FixBugSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {
        if (emptyBreak(null)) {
            return true;
        }
        return super.performClick();

    }

    public FixBugSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FixBugSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public FixBugSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (emptyBreak(event)) {
            if (event != null && event.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            }
            return false;
        }
        return super.onTouchEvent(event);
    }

    protected boolean emptyBreak(MotionEvent event) {
        if (getAdapter() == null) {
            if (event!=null&&event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(getContext(), "当前下拉列表无内容!", Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        if (event!=null&&getAdapter().getCount() == 0) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(getContext(), "当前下拉列表无内容", Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
      /*  if (emptyBreak()) {
            if(ev.getAction()==MotionEvent.ACTION_DOWN){
                return true;
            }
            return true;
        }*/
        return super.dispatchTouchEvent(ev);
    }
}
