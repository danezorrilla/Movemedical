package com.example.movemedical.model

data class Appointment(
    var id: String? = null,
    var date: String? = null,
    var location: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var description: String? = null
)
