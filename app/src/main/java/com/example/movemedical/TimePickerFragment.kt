package com.example.movemedical

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        return TimePickerDialog(
            activity,
            activity as TimePickerDialog.OnTimeSetListener?,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}