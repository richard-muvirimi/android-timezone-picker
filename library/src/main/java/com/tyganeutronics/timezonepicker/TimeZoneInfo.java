package com.tyganeutronics.timezonepicker;

import java.util.TimeZone;

public class TimeZoneInfo extends com.android.timezonepicker.TimeZoneInfo {

    public String getTzId() {
        return this.mTzId;
    }

    public String getCountry() {
        return this.mCountry;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public TimeZoneInfo(TimeZone tz, String country) {
        super(tz, country);
    }
    
}
