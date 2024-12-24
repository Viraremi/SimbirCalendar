package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.Timestamp
import java.util.Date

class EventsSharedPref (
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {
    private var eventsList = mutableListOf<Event>()
    fun getEvents(): MutableList<Event> {
        refresh()
        return eventsList
    }

    companion object{
        private const val EVENTS_KEY = "events_list"
    }

    init {
        refresh()
    }

    fun save(){
        val json = gson.toJson(eventsList)
        sharedPref.edit()
            .putString(EVENTS_KEY, json)
            .apply()
    }

    fun add(event: Event){
        eventsList.add(0, event)
        save()
    }

    fun clear(){
        eventsList.clear()
        save()
    }

    fun refresh(){
        val json = sharedPref.getString(EVENTS_KEY, null)
        eventsList = gson.fromJson(json, object : TypeToken<MutableList<Event>>() {}.type) ?: mutableListOf<Event>()
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromStamp(stamp: Timestamp): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dataTime = Date(stamp.time)
        return sdf.format(dataTime)
    }

    @SuppressLint("SimpleDateFormat")
    fun getHoursFromStamp(stamp: Timestamp): String {
        val sdf = SimpleDateFormat("HH")
        val dataTime = Date(stamp.time)
        return sdf.format(dataTime)
    }
}