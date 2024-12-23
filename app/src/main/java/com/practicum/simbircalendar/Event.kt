package com.practicum.simbircalendar

import android.content.ClipDescription
import java.sql.Timestamp

data class Event (
    val id: Int,
    val data_start: Timestamp,
    val data_end: Timestamp,
    val name: String,
    val description: String,
){
}