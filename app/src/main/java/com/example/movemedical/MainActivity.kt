package com.example.movemedical

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movemedical.adapter.AppointmentAdapter
import com.example.movemedical.model.Appointment
import com.example.movemedical.viewmodel.AppointmentViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), AppointmentAdapter.EditAndDeleteInterface {

    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var appointmentObserver: Observer<List<Appointment>>
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.floatingActionButton)
        recyclerView = findViewById(R.id.appointmentListRV)


        appointmentViewModel = ViewModelProvider(this)[AppointmentViewModel::class.java]
        appointmentObserver = Observer { appointments -> displayAppointmentList(appointments) }
        appointmentObserver.let {
            appointmentViewModel.getAppointmentList().observe(this, it)
        }

        floatingActionButton.setOnClickListener{
            openToAppointment()
        }
    }

    private fun displayAppointmentList(appointmentList: List<Appointment>){
        for (index in appointmentList.indices){
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = AppointmentAdapter(appointmentList, this)
        }
    }

    private fun openToAppointment(){
        val intent = Intent(this, AppointmentActivity::class.java)
        startActivity(intent)
    }

    override fun editAppointment(appointment: Appointment) {
        val id = appointment.id
        val date = appointment.date
        val description = appointment.description
        val location = appointment.location
        val startTime = appointment.startTime
        val endTime = appointment.endTime

        intent = Intent(this, AppointmentActivity::class.java)
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("date", date)
        bundle.putString("description", description)
        bundle.putString("location", location)
        bundle.putString("startTime", startTime)
        bundle.putString("endTime", endTime)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun deleteAppointment(appointment: Appointment) {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setTitle("Cancel This Appointment")
        alertDialog.setMessage("Are You Sure You Want To Cancel This Appointment")
        alertDialog.setPositiveButton("Accept"){
                _, _ ->
            appointment.id?.let { appointmentViewModel.deleteAppoint(it) }
        }
        alertDialog.setNegativeButton("Cancel"){
                _, _ ->  }
        alertDialog.show()
    }

}