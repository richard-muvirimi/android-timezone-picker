package com.tyganeutronics.timezonepicker.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
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
        findViewById<AppCompatButton>(R.id.btn_privacy_policy).setOnClickListener(this)

        findViewById<FrameLayout>(R.id.fragment_container).post {
            val fragment = TimeZonePickerFragment()
            fragment.arguments = this.fragmentArguments()
            fragment.setOnTimeZoneSetListener(this)

            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }

        applyWindowInsets()

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

                R.id.btn_privacy_policy -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = getString(R.string.privacy_policy_url).toUri()
                    startActivity(intent)
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

    private fun applyWindowInsets() {

        // https://developer.android.com/develop/ui/views/layout/edge-to-edge#kotlin
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<ScrollView>(R.id.layout_container)) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }
    }
}