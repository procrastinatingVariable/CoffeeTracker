package ro.fmi.ip.trei.coffeetracker.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class UntouchableEditText extends android.support.v7.widget.AppCompatEditText {


    public UntouchableEditText(Context context) {
        super(context);
    }

    public UntouchableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UntouchableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return false;
    }
}
