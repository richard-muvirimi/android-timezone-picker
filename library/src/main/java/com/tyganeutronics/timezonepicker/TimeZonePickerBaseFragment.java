package com.tyganeutronics.timezonepicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.timezonepicker.TimeZonePickerDialog;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZonePickerBaseFragment extends TimeZonePickerDialog implements TimeZonePickerDialog.OnTimeZoneSetListener {

    private static final String KEY_HAS_RESULTS = "has_results";
    private static final String KEY_LAST_FILTER_STRING = "last_filter_string";
    private static final String KEY_LAST_FILTER_TYPE = "last_filter_type";
    private static final String KEY_LAST_FILTER_TIME = "last_filter_time";
    private static final String KEY_HIDE_FILTER_SEARCH = "hide_filter_search";

    private TimeZonePickerBaseFragment.OnTimeZoneSetListener mTimeZoneSetListener;

    @SuppressWarnings("FieldCanBeLocal")
    private TimeZonePickerView mView;

    public interface OnTimeZoneSetListener extends TimeZonePickerDialog.OnTimeZoneSetListener {
        @Override
        @Deprecated
        default void onTimeZoneSet(com.android.timezonepicker.TimeZoneInfo tzi){
            onTimeZoneSet( (TimeZoneInfo) tzi);
        }

       void onTimeZoneSet(TimeZoneInfo tzi);
    }

    public void setOnTimeZoneSetListener(TimeZonePickerBaseFragment.OnTimeZoneSetListener l) {
        mTimeZoneSetListener = l;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long timeMillis = 0;
        String timeZone = null;
        Bundle b = getArguments();
        if (b != null) {
            timeMillis = b.getLong(BUNDLE_START_TIME_MILLIS);
            timeZone = b.getString(BUNDLE_TIME_ZONE);
        }
        boolean hideFilterSearch = false;

        if (savedInstanceState != null) {
            hideFilterSearch = savedInstanceState.getBoolean(KEY_HIDE_FILTER_SEARCH);
        }
        mView = new TimeZonePickerView(getActivity(), null, timeZone, timeMillis, this,
                hideFilterSearch);
        if (savedInstanceState != null && savedInstanceState.getBoolean(KEY_HAS_RESULTS, false)) {
            mView.showFilterResults(savedInstanceState.getInt(KEY_LAST_FILTER_TYPE),
                    savedInstanceState.getString(KEY_LAST_FILTER_STRING),
                    savedInstanceState.getInt(KEY_LAST_FILTER_TIME));
        }
        return mView;
    }

    @Override
    public void onTimeZoneSet(com.android.timezonepicker.TimeZoneInfo tzi) {
        if (mTimeZoneSetListener != null) {
            TimeZone tz = new SimpleTimeZone(tzi.getNowOffsetMillis(), tzi.mTzId);

            TimeZoneInfo timeZoneInfo = new TimeZoneInfo(tz, tzi.mCountry);
            timeZoneInfo.mDisplayName = tzi.mDisplayName;
            timeZoneInfo.groupId = tzi.groupId;

            mTimeZoneSetListener.onTimeZoneSet(timeZoneInfo);
        }
        dismiss();
    }

}
