package lozn.spinner;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2021/11/12
 * 13:57
 */
public class EditInnerSpinner extends FixBugSpinner {

    public int getMyselection() {
        return myselection;
    }

    public void setMyselection(int myselection) {
        this.myselection = myselection;
    }

    @Override
    public Object getSelectedItem() {
        if(myselection==-1){
            return null;
        }
        return super.getSelectedItem();
    }

    /**
     * 自己维护一个位置方便setSection(-1);
     */
    int myselection=-1;

    public EditInnerSpinner(@NonNull Context context) {
        super(context);
    }

    public EditInnerSpinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public EditInnerSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditInnerSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditInnerSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public EditInnerSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }
    /**
     * 并不能真正解决问题，因为内部的计算是根据spinner的宽度来控制item的宽度的。
     */
   /* @Override
    protected boolean addViewInLayout(View child, int index, LayoutParams params) {
//            return super.addViewInLayout(child, index, params);
        return false;
    }*/
    /*  @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
//        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }


    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
//        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }*/

  /*  @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0);
    }*/
}
