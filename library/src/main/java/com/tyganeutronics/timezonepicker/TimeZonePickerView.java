package com.tyganeutronics.timezonepicker;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class TimeZonePickerView extends com.android.timezonepicker.TimeZonePickerView implements TextWatcher {

    @SuppressWarnings("FieldCanBeLocal")
    private AutoCompleteTextView mAutoCompleteTextView;

    private ImageButton mClearButton;

    public TimeZonePickerView(Context context, AttributeSet attrs){
        super(context, attrs, "GMT", System.currentTimeMillis(), null, false);
    }

    public TimeZonePickerView(Context context, AttributeSet attrs,
                              String timeZone, long timeMillis, OnTimeZoneSetListener l,
                              boolean hideFilterSearch) {
        super(context, attrs, timeZone, timeMillis, l, hideFilterSearch);

        mAutoCompleteTextView = findViewById(com.android.timezonepicker.R.id.searchBox);
        mAutoCompleteTextView.setHint(null);

        mAutoCompleteTextView.addTextChangedListener(this);

        mClearButton = findViewById(com.android.timezonepicker.R.id.clear_search);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mClearButton.getVisibility() == VISIBLE) {
            mClearButton.setVisibility(GONE);
        }
    }
}
