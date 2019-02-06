package ro.fmi.ip.trei.coffeetracker.ui.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.fmi.ip.trei.coffeetracker.R;
import ro.fmi.ip.trei.coffeetracker.util.SoftKeyboardUtils;

public class SizeSelector extends LinearLayout {

    private static final String TAG_SMALL = "small";
    private static final String TAG_MEDIUM = "medium";
    private static final String TAG_LARGE = "large";
    private static final String TAG_CUSTOM = "custom";
    private static final String TAG_AMOUNT = "amount";

    private enum ButtonType {
        EDGE_LEFT,
        EDGE_RIGHT,
        MIDDLE
    }

    private enum State {
        SELECTED,
        UNSELECTED
    }


    private View buttonSizeSmall;
    private View buttonSizeMedium;
    private View buttonSizeLarge;
    private View buttonSizeCustom;

    private EditText customSizeEditText;

    private View lastSelectedButton = null;

    private OnClickListener selectOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View button) {
            if (button == lastSelectedButton) {
                return;
            }

            if (lastSelectedButton != null) {
                deselectButton(lastSelectedButton);
            }
            lastSelectedButton = button;
            selectButton(lastSelectedButton);

            if (button != buttonSizeCustom) {
                customSizeEditText.clearFocus();
                SoftKeyboardUtils.hideSoftInput(customSizeEditText);
            } else {
                customSizeEditText.requestFocus();
                SoftKeyboardUtils.showSoftInput(customSizeEditText);
            }
        }
    };

    public SizeSelector(Context context) {
        super(context);
        initView(context);
    }

    public SizeSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SizeSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SizeSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.size_selector, this);

        buttonSizeSmall = findViewById(R.id.button_size_small);
        buttonSizeSmall.setOnClickListener(selectOnClickListener);

        buttonSizeMedium = findViewById(R.id.button_size_medium);
        buttonSizeMedium.setOnClickListener(selectOnClickListener);

        buttonSizeLarge = findViewById(R.id.button_size_large);
        buttonSizeLarge.setOnClickListener(selectOnClickListener);

        buttonSizeCustom = findViewById(R.id.button_size_custom);
        buttonSizeCustom.setOnClickListener(selectOnClickListener);
        customSizeEditText = buttonSizeCustom.findViewById(R.id.button_size_custom_edit);
    }

    private void selectButton(@NonNull View button) {
        if (button == null) {
            return;
        }
        
        String tag = (String)button.getTag();
        if (tag.equals(TAG_SMALL)) {
            styleButtonAsSelectedByType(button, ButtonType.EDGE_LEFT);
        } else if (tag.equals(TAG_MEDIUM)) {
            styleButtonAsSelectedByType(button, ButtonType.MIDDLE);
        } else if (tag.equals(TAG_LARGE)) {
            styleButtonAsSelectedByType(button, ButtonType.MIDDLE);
        } else if (tag.equals(TAG_CUSTOM)) {
            styleButtonAsSelectedByType(button, ButtonType.EDGE_RIGHT);
        }
    }


    private void deselectButton(@NonNull View button) {
        if (button == null) {
            return;
        }

        String tag = (String)button.getTag();
        if (tag.equals(TAG_SMALL)) {
            styleButtonAsUnselectedByType(button, ButtonType.EDGE_LEFT);
        } else if (tag.equals(TAG_MEDIUM)) {
            styleButtonAsUnselectedByType(button, ButtonType.MIDDLE);
        } else if (tag.equals(TAG_LARGE)) {
            styleButtonAsUnselectedByType(button, ButtonType.MIDDLE);
        } else if (tag.equals(TAG_CUSTOM)) {
            styleButtonAsUnselectedByType(button, ButtonType.EDGE_RIGHT);
        }
    }

    private void styleButtonAsSelectedByType(View button, ButtonType buttonType) {
        Context context = getContext();
        Drawable backgroundSelected;
        switch (buttonType) {
            case EDGE_LEFT:
                backgroundSelected = context.getDrawable(R.drawable.size_selector_left_selected);
                break;

            case MIDDLE:
                backgroundSelected = context.getDrawable(R.drawable.size_selector_middle_selected);
                break;

            case EDGE_RIGHT:
                backgroundSelected = context.getDrawable(R.drawable.size_selector_right_selected);
                break;

            default:
                backgroundSelected = new ColorDrawable(Color.TRANSPARENT);
        }
        button.setBackground(backgroundSelected);
        styleLabelsForSelected(button);
    }

    private void styleButtonAsUnselectedByType(View button, ButtonType buttonType) {
        Context context = getContext();
        Drawable backgroundUnselected;
        switch (buttonType) {
            case EDGE_LEFT:
                backgroundUnselected = context.getDrawable(R.drawable.size_selector_left_unselected);
                break;

            case MIDDLE:
                backgroundUnselected = context.getDrawable(R.drawable.size_selector_middle_unselected);
                break;

            case EDGE_RIGHT:
                backgroundUnselected = context.getDrawable(R.drawable.size_selector_right_unselected);
                break;

            default:
                backgroundUnselected = new ColorDrawable(Color.TRANSPARENT);
        }
        button.setBackground(backgroundUnselected);
        styleLabelsForUnselected(button);
    }

    private void styleLabelsForSelected(View button) {
        if (button instanceof ViewGroup) {
            ViewGroup buttonViewGroup = (ViewGroup) button;
            int childCount = buttonViewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View label = buttonViewGroup.getChildAt(i);
                if (label instanceof TextView) {
                    styleTextViewAsSelected((TextView)label);
                } else if (label instanceof ViewGroup) {
                    styleLabelsForSelected(label);
                }
            }
        }
    }

    private void styleLabelsForUnselected(View button) {
        if (button instanceof ViewGroup) {
            ViewGroup buttonViewGroup = (ViewGroup) button;
            int childCount = buttonViewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View label = buttonViewGroup.getChildAt(i);
                if (label instanceof TextView) {
                    styleTextViewAsUnselected((TextView)label);
                } else if (label instanceof ViewGroup) {
                    styleLabelsForUnselected(label);
                }
            }
        }
    }

    private void styleTextViewAsSelected(TextView textView) {
        int selectedColor = getContext().getColor(android.R.color.white);
        textView.setTextColor(selectedColor);
        textView.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(android.R.color.white)));
    }

    private void styleTextViewAsUnselected(TextView textView) {
        int unselectedColor = getContext().getColor(android.R.color.black);
        textView.setTextColor(unselectedColor);
        textView.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(android.R.color.black)));
    }

    public int getSelectedAmount() {
        View button = lastSelectedButton;
        String tag = (String)lastSelectedButton.getTag();
        int selectedAmount = 0;
        if (tag.equals(TAG_SMALL) || tag.equals(TAG_MEDIUM) || tag.equals(TAG_LARGE)) {
            TextView amount = button.findViewWithTag(TAG_AMOUNT);
            String amountString = amount.getText().toString();
            selectedAmount = Integer.parseInt(amountString.substring(0, 3));
        } else if (tag.equals(TAG_CUSTOM)) {
            EditText amount = button.findViewWithTag(TAG_AMOUNT);
            String amountString = amount.getText().toString();
            selectedAmount = Integer.parseInt(amountString);
        }
        return selectedAmount;
    }


}
