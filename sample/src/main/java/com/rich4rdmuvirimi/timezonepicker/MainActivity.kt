package com.rich4rdmuvirimi.timezonepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.android.timezonepicker.TimeZoneInfo
import com.android.timezonepicker.TimeZonePickerDialog
import java.time.ZonedDateTime

class MainActivity : AppCompatActivity(), View.OnClickListener, TimeZonePickerDialog.OnTimeZoneSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btn_timezone).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_timezone -> {
                    val args = Bundle()
                    args.putLong(
                        TimeZonePickerDialog.BUNDLE_START_TIME_MILLIS,
                        ZonedDateTime.now().toEpochSecond() * 1000
                    )
                    args.putString(
                        TimeZonePickerDialog.BUNDLE_TIME_ZONE,
                        ZonedDateTime.now().zone.id
                    )

                    val timeZonePickerDialog = TimeZonePickerDialog()
                    timeZonePickerDialog.arguments = args
                    timeZonePickerDialog.setOnTimeZoneSetListener(this)
                    timeZonePickerDialog.show(fragmentManager, TimeZonePickerDialog.TAG)
                }
            }
        }
    }

    override fun onTimeZoneSet(tzi: TimeZoneInfo?) {
        if (tzi != null) {
            findViewById<AppCompatTextView>(R.id.txt_timezone).text = tzi.mDisplayName
        }
    }
}