package com.practicum.simbircalendar

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EventsSharedPref (
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {
    private var eventsList = mutableListOf<Event>()
    fun getEvents(): MutableList<Event> {return eventsList}

    companion object{
        private const val EVENTS_KEY = "events_list"
    }

    init {
        val json = sharedPref.getString(EVENTS_KEY, null)
        eventsList = gson.fromJson(json, object : TypeToken<MutableList<Event>>() {}.type) ?: mutableListOf<Event>()
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
}