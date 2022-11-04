package com.example.movemedical.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movemedical.R
import com.example.movemedical.model.Appointment

class AppointmentAdapter(private val appointmentList: List<Appointment>,
                         private val editAndDeleteInterface: EditAndDeleteInterface):
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    interface EditAndDeleteInterface{
        fun editAppointment(appointment: Appointment)
        fun deleteAppointment(appointment: Appointment)
    }

    class AppointmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date_label)
        var location: TextView = itemView.findViewById(R.id.location_label)
        var startTime: TextView = itemView.findViewById(R.id.start_time_label)
        var endTime: TextView = itemView.findViewById(R.id.end_time_label)
        var description: TextView = itemView.findViewById(R.id.description_label)
        var editAppointment: Button = itemView.findViewById(R.id.edit_card_button)
        var deleteAppointment: Button = itemView.findViewById(R.id.delete_card_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_card, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.date.text = "Date: " + appointmentList[position].date
        holder.location.text = "Location: " + appointmentList[position].location
        holder.startTime.text = "Start Time: " + appointmentList[position].startTime
        holder.endTime.text = "End Time: " + appointmentList[position].endTime
        holder.description.text = "Description: " + appointmentList[position].description

        val id = appointmentList[position].id
        val date = holder.date.text.toString()
        val location = holder.location.text.toString()
        val startTime = holder.startTime.text.toString()
        val endTime = holder.endTime.text.toString()
        val description = holder.description.text.toString()
        val currentAppointment = Appointment(id,date, location, startTime, endTime, description)

        holder.editAppointment.setOnClickListener {
            editAndDeleteInterface.editAppointment(currentAppointment)
        }
        holder.deleteAppointment.setOnClickListener {
            editAndDeleteInterface.deleteAppointment(currentAppointment)
        }
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }
}