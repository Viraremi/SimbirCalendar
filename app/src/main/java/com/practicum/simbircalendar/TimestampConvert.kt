package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.sql.Timestamp
import java.util.Date

object TimestampConvert { //Singleton класс отвечающий за получения строк с датой из Timestamp
    @SuppressLint("SimpleDateFormat")
    fun getDate(stamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dataTime = Date(stamp)
        return sdf.format(dataTime)
    }

    @SuppressLint("SimpleDateFormat")
    fun getHours(stamp: Long): String {
        val sdf = SimpleDateFormat("HH")
        val dataTime = Date(stamp)
        return sdf.format(dataTime)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime(stamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm")
        val dataTime = Date(stamp)
        return sdf.format(dataTime)
    }
}