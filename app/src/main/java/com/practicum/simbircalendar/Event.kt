package com.practicum.simbircalendar

import java.io.Serializable

data class Event ( // Класс дела
    val id: Int,
    val data_start: Long,
    val data_end: Long,
    val name: String,
    val description: String,
) : Serializable{
}