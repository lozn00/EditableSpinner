package lozn.spinner;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.AdapterView;

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
      /*  if (myselection == -1) {
            return null;
        }*/
        return super.getSelectedItem();
    }


    @Override
    public void setSelection(int position) {
        if(notifiySameSelectNotify(position)){
            return;
        }
    /*    if(myselection!=position){//这里主要用来允许为空的时候使用的，允许为空的时候在adapter item里面 myselction始终无法触发
            myselection=position;
        }*/
        super.setSelection(position);

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void setSelection(int position, boolean animate) {
        if(notifiySameSelectNotify(position)){
           return;
        }
        super.setSelection(position, animate);
    }
    public void setSuperSection(int position){
        super.setSelection(position);
    }

    /**
     * 跳过选中 setNextSelectedPositionInt
     * @param position
     * @param animate
     */
    public void setSuperSection(int position,boolean animate){
        super.setSelection(position,animate);
    }
//return true避免重复
    public boolean notifiySameSelectNotify(int position) {
        boolean sameSelected = position ==getSelectedItemPosition();// getMyselection();
        if (sameSelectedNotifiy) {

//            boolean sameSelected = position == getSelectedItemPosition();
            if (sameSelected||(getMyselection()<0&&position>=0)) {//这种情况也应该通知，但是自带的是不会通知的。
                AdapterView.OnItemSelectedListener onItemSelectedListener = getOnItemSelectedListener();
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
                }
                return true;
            }
        }
        return false;
    }

    public boolean isSameSelectedNotifiy() {
        return sameSelectedNotifiy;
    }

    public void setSameSelectedNotifiy(boolean sameSelectedNotifiy) {
        this.sameSelectedNotifiy = sameSelectedNotifiy;
    }

    boolean sameSelectedNotifiy = true;

    /**
     * 自己维护一个位置方便setSection(-1);
     */
    int myselection = -1;

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

    public void setSuperSectionAndDisableNotify(int position) {
        super.setSelection(position,true);
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
