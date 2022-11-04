package com.example.movemedical

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.movemedical.model.Appointment
import com.example.movemedical.viewmodel.AppointmentViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat

class AppointmentActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var selectedLocation: AutoCompleteTextView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var selectedDate: TextView
    private lateinit var selectedDescription: TextInputEditText
    private lateinit var selectedStartTime: TextView
    private lateinit var selectedEndTime: TextView
    private lateinit var timePickerFragment: TimePickerFragment

    var id = ""
    var date = ""
    var location = ""
    var startTime = ""
    var endTime = ""
    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_appointment)

        appointmentViewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]

        val bundle = intent.extras

        floatingActionButton = findViewById(R.id.back_FAB)

        selectedDate = findViewById(R.id.selected_date)
        selectedDate.text = bundle?.getString("date")

        selectedDescription = findViewById(R.id.description)


        selectedLocation = findViewById(R.id.autoCompleteTextView)

        selectedStartTime = findViewById(R.id.selected_start_time)
        selectedStartTime.text = bundle?.getString("startTime")

        selectedEndTime = findViewById(R.id.selected_end_time)
        selectedEndTime.text = bundle?.getString("endTime")

        timePickerFragment = TimePickerFragment()

        if(bundle?.getString("description") != null  && bundle.getString("location") != null){
            val descriptionEditable = SpannableStringBuilder(bundle.getString("description"))
            selectedDescription.text = descriptionEditable
            val locationEditable = SpannableStringBuilder(bundle.getString("location"))
            selectedLocation.text = locationEditable
        }

        // Floating Action Button
        floatingActionButton.setOnClickListener {
            finish()
        }

        // Select Date
        val dateButton = findViewById<Button>(R.id.select_date_button)
        dateButton.setOnClickListener {
            openDatePicker()
        }

        // Location Adapter
        val locations = resources.getStringArray(R.array.locations)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, locations)
        selectedLocation.setAdapter(arrayAdapter)

        // Select Stat Time
        val startTimeButton = findViewById<Button>(R.id.start_time_button)
        startTimeButton.setOnClickListener {
            openStartTimePicker()
        }

        // Select End Time
        val endTimeButton = findViewById<Button>(R.id.end_time_button)
        endTimeButton.setOnClickListener {
            openEndTimePicker()
        }

        // Save Appointment
        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            date = selectedDate.text.toString()
            location = selectedLocation.text.toString()
            startTime = selectedStartTime.text .toString()
            endTime = selectedEndTime.text.toString()
            description= selectedDescription.text.toString()

            if (bundle?.getString("id") == null){
                id = appointmentViewModel.databaseKey.toString()
                val appointment = Appointment(
                    id,
                    date,
                    location,
                    startTime,
                    endTime,
                    description)
                appointmentViewModel.saveAppointment(id, appointment)
            } else {
                id = bundle.getString("id")!!
                val appointment = Appointment(
                    id,
                    date,
                    location,
                    startTime,
                    endTime,
                    description)
                Log.d("TAG_Z", "HELLO")
                appointmentViewModel.updateAppointment(id, appointment)
            }
            finish()
        }

    }

    // Open Date Picker
    private fun openDatePicker(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Appointment Date")
            .build()

        datePicker.show(supportFragmentManager, "date picker")

        datePicker.addOnPositiveButtonClickListener {
            selectedDate.text = datePicker.headerText
        }
    }

    // Open Time Picker for Start Time
    private fun openStartTimePicker(){
        timePickerFragment.show(supportFragmentManager, "start time picker")
    }

    // Open Time Picker for End Time
    private fun openEndTimePicker(){
        timePickerFragment.show(supportFragmentManager, "end time picker")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minutes: Int) {
        var AM_PM = ""
        var hour = 0
        if(hourOfDay < 12){
            hour = hourOfDay
            AM_PM = "AM"
        } else  {
            hour = hourOfDay - 12
            AM_PM = "PM"
        }
        val decimalFormat = DecimalFormat("00")
        val min = decimalFormat.format(minutes)

        if(timePickerFragment.tag == "start time picker"){
            selectedStartTime.text = "Hour: " + hour + " Minutes: " + min + " " + AM_PM
        } else {
            selectedEndTime.text = "Hour: " + hour + " Minutes: " + min + " " + AM_PM
        }

    }
}