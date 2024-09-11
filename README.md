# Android Timezone Picker

![Release](https://jitpack.io/v/richard-muvirimi/android-timezone-picker.svg)

An android timezone picker implementation that is based
on [Google's](https://android.googlesource.com/platform/frameworks/opt/timezonepicker)
implementation where you can select timezones based on country.

Instead of using the on device timezone information which would usually be outdated depending on
when the device last received an update, this library ships with it's own timezone database. It also
takes into account daylight savings.

### Screenshots

|  Country                                                   |  Country Timezones                                         |  After Selection                                           |
|------------------------------------------------------------|------------------------------------------------------------|------------------------------------------------------------|
| <img src=".github/assets/screenshot-2.jpg" width="280px" > | <img src=".github/assets/screenshot-1.jpg" width="280px" > | <img src=".github/assets/screenshot-3.jpg" width="280px" > |

### Demo Application

[<img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/>](https://play.google.com/store/apps/details?id=com.tyganeutronics.timezonepicker.sample&utm_source=github&utm_campaign=github&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1)

### Building Overview

This library relies on files from different sources i.e

1. [Google timezone picker](https://android.googlesource.com/platform/frameworks/opt/timezonepicker)
   which just provides the picker interface
    - Added as a submodule to this repository for easier tracking
    - Needs to use appcompat views to support dark and light mode
    - Needs [zone.tab](https://github.com/eggert/tz/blob/main/zone.tab)
      and [backward](https://github.com/eggert/tz/blob/main/backward) for the timezone data.
2. [Time zone database](https://github.com/eggert/tz) for up-to date timezone data
   
These additional dependencies are managed and updated by gradle on building the project

### Installation

###### Step 1
Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```
###### Step 2
Add both dependencies
```groovy
dependencies {
    implementation 'com.github.richard-muvirimi:timezonepicker:Tag'
    implementation 'com.github.richard-muvirimi:com.android.timezonepicker:Tag'
}
```
You can get detailed setup instructions on [Jitpack.io](https://jitpack.io/#richard-muvirimi/android-timezone-picker)

### Usage

###### Step 1
Before creating a timezone picker fragment, you need to pass it arguments as defined below

```kotlin
val args = Bundle()
args.putLong(
    TimeZonePickerBaseFragment.BUNDLE_START_TIME_MILLIS,
    ZonedDateTime.now().toEpochSecond() * 1000
)
args.putString(
    TimeZonePickerBaseFragment.BUNDLE_TIME_ZONE,
    ZonedDateTime.now().zone.id
)
```

###### Step 2
Then create one of three timezone picker instances:

1. Normal Dialog Fragment

    ```kotlin
    val timeZonePickerDialog = TimeZonePickerAppCompatFragment()
    timeZonePickerDialog.arguments = args
    timeZonePickerDialog.setOnTimeZoneSetListener(this)
    timeZonePickerDialog.show(fragmentManager, TimeZonePickerAppCompatFragment.TAG)
    ```

2. Bottom Sheet Dialog Fragment

    ```kotlin
    val timeZonePickerDialog = TimeZonePickerBottomSheetFragment()
    timeZonePickerDialog.arguments = args
    timeZonePickerDialog.setOnTimeZoneSetListener(this)
    timeZonePickerDialog.show(fragmentManager, TimeZonePickerBottomSheetFragment.TAG)
    ```

3. Normal Fragment

    ```kotlin
    val timeZonePickerDialog = TimeZonePickerFragment()
    timeZonePickerDialog.arguments = args
    timeZonePickerDialog.setOnTimeZoneSetListener(this)
    
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container, fragment)
    transaction.commit()
    ```

###### Step 3
and then listening for the result

```kotlin
override fun onTimeZoneSet(tzi: TimeZoneInfo?) {
    if (tzi != null) {
        findViewById<AppCompatTextView>(R.id.txt_timezone).text = tzi.mDisplayName
    }
}
```

Make sure you import your classes from `import com.tyganeutronics.timezonepicker.*`

```kotlin
import com.tyganeutronics.timezonepicker.TimeZoneInfo
import com.tyganeutronics.timezonepicker.TimeZonePickerAppCompatFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerBaseFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerBottomSheetFragment
import com.tyganeutronics.timezonepicker.TimeZonePickerFragment
```

### Contribution

Fork and send a pull request 😉, or an issue...

### License

```license
Copyright 2022 Richard Muvirimi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
