package com.tyganeutronics.timezonepicker.sample

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.tyganeutronics.timezonepicker.TimeZoneInfo
import com.tyganeutronics.timezonepicker.TimeZonePickerAppCompatFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerBaseFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerBottomSheetFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerFragment
import java.time.ZonedDateTime

class MainActivity : AppCompatActivity(), View.OnClickListener,
    TimeZonePickerBaseFragment.OnTimeZoneSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btn_bottomsheet_picker).setOnClickListener(this)
        findViewById<AppCompatButton>(R.id.btn_dialog_picker).setOnClickListener(this)

        findViewById<FrameLayout>(R.id.fragment_container).post {
            val fragment = TimeZonePickerFragment()
            fragment.arguments = this.fragmentArguments()
            fragment.setOnTimeZoneSetListener(this)

            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }

    }

    override fun onClick(view: View?) {
        if (view != null) {

            when (view.id) {
                R.id.btn_bottomsheet_picker -> {

                    val timeZonePickerDialog = TimeZonePickerBottomSheetFragment()
                    timeZonePickerDialog.arguments = this.fragmentArguments()
                    timeZonePickerDialog.setOnTimeZoneSetListener(this)
                    timeZonePickerDialog.show(
                        fragmentManager,
                        TimeZonePickerBottomSheetFragment.TAG
                    )
                }
                R.id.btn_dialog_picker -> {

                    val timeZonePickerDialog = TimeZonePickerAppCompatFragment()
                    timeZonePickerDialog.arguments = this.fragmentArguments()
                    timeZonePickerDialog.setOnTimeZoneSetListener(this)
                    timeZonePickerDialog.show(fragmentManager, TimeZonePickerAppCompatFragment.TAG)
                }
            }
        }
    }

    private fun fragmentArguments(): Bundle {
        val args = Bundle()
        args.putLong(
            TimeZonePickerBaseFragment.BUNDLE_START_TIME_MILLIS,
            ZonedDateTime.now().toEpochSecond() * 1000
        )
        args.putString(
            TimeZonePickerBaseFragment.BUNDLE_TIME_ZONE,
            ZonedDateTime.now().zone.id
        )

        return args
    }

    override fun onTimeZoneSet(tzi: TimeZoneInfo?) {
        if (tzi != null) {

            findViewById<View>(R.id.results_container).run {
                findViewById<AppCompatTextView>(com.tyganeutronics.timezonepicker.R.id.time_zone).text =
                    tzi.displayName

                findViewById<AppCompatTextView>(com.tyganeutronics.timezonepicker.R.id.time_offset).text =
                    tzi.getGmtDisplayName(this@MainActivity)

                findViewById<AppCompatTextView>(com.tyganeutronics.timezonepicker.R.id.location).apply {
                    val location = tzi.country
                    if (location == null) {
                        visibility = View.INVISIBLE
                    } else {
                        text = location
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}