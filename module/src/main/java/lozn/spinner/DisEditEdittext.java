package lozn.spinner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author:Lozn
 * Email:qssq521@gmail.com
 * 2022/1/20
 * 9:46
 */
public class DisEditEdittext  extends EditableEdittext{
    public DisEditEdittext(@NonNull Context context) {
        super(context);
    }

    public DisEditEdittext(@NonNull Context context, boolean editable) {
        super(context, editable);
    }

    public DisEditEdittext(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DisEditEdittext(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean getDefaultEditableValue() {
        return false;
    }
}
