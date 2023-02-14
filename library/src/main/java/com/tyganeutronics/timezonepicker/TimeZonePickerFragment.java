package com.tyganeutronics.timezonepicker;

import android.os.Bundle;

public class TimeZonePickerFragment extends TimeZonePickerBaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setShowsDialog(false);
    }

    @Override
    public void dismiss() {
        // Do nothing
    }

    @Override
    public void dismissAllowingStateLoss() {
        // Do nothing
    }
}