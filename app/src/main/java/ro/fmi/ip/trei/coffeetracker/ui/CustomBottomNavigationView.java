package ro.fmi.ip.trei.coffeetracker.ui;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

public class CustomBottomNavigationView extends BottomNavigationView {
    public CustomBottomNavigationView(Context context) {
        super(context);
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * <p>
     * Same as {@link BottomNavigationView#setSelectedItemId(int)} with the addition of a boolean
     * parameter that lets you control wether the {@link android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener}
     * is triggered.
     * </p>
     * @param itemId item resource id
     * @param triggerListener wether to trigger the listener or not
     */
    public void setSelectedItemId(int itemId, boolean triggerListener) {
        if (triggerListener) {
            super.setSelectedItemId(itemId);
        } else {
            MenuItem item = getMenu().findItem(itemId);
            if (item != null) {
                item.setChecked(true);
            }
        }
    }
}
