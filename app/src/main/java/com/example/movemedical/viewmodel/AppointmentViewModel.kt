package com.example.movemedical.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movemedical.model.Appointment
import com.google.firebase.database.*

class AppointmentViewModel : ViewModel() {

    private var firebaseReference: DatabaseReference = FirebaseDatabase
        .getInstance()
        .reference
        .child("Appointments")

    private var appointmentListMutableLiveData = MutableLiveData<List<Appointment>>()

    var databaseKey = firebaseReference.push().key


    // POST
    fun saveAppointment(id: String, appointment: Appointment){
        firebaseReference.child(id).setValue(appointment)
    }

    // GET
    fun getAppointmentList(): MutableLiveData<List<Appointment>>{
        firebaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val appointmentList = ArrayList<Appointment>()
                for (ds in snapshot.children){
                    val currentAppointment: Appointment? = ds.getValue(Appointment::class.java)
                    if (currentAppointment != null) {
                        appointmentList.add(currentAppointment)
                    }
                    appointmentListMutableLiveData.value = appointmentList
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return appointmentListMutableLiveData
    }

    // UPDATE
    fun updateAppointment(id: String, appointment: Appointment){
        val databaseRef = firebaseReference.child(id)
        databaseRef.setValue(appointment)
    }

    fun deleteAppoint(id: String){
        val databaseRef = firebaseReference.child(id)
        databaseRef.removeValue()
    }

}