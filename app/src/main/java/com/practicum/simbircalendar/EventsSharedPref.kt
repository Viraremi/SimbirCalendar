package com.practicum.simbircalendar

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EventsSharedPref {
    private lateinit var sharedPref: SharedPreferences
    var sharedPrefIsNull = true
    private val errMsg = "SharedPreferences is null. Use setSharedPref()"
    val gson = Gson()
    private const val EVENTS_KEY = "events_list"
    private var currenSize = 0
    private var eventsList = mutableListOf<Event>()
    fun getEvents(): MutableList<Event> {
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        refresh()
        return eventsList
    }

    fun setSharedPref(sp: SharedPreferences){
        sharedPref = sp
        sharedPrefIsNull = false
        refresh()
    }

    fun save(){
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        val json = gson.toJson(eventsList)
        sharedPref.edit()
            .putString(EVENTS_KEY, json)
            .apply()
    }

    fun add(event: Event){
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        eventsList.add(0, event)
        save()
    }

    fun clear(){
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        eventsList.clear()
        save()
    }

    fun refresh(){
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        val json = sharedPref.getString(EVENTS_KEY, null)
        eventsList = gson.fromJson(json, object : TypeToken<MutableList<Event>>() {}.type) ?: mutableListOf<Event>()
        currenSize = eventsList.size
    }

    fun getCurrentID(): Int{
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        refresh()
        return currenSize
    }
}