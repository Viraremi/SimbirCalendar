package com.practicum.simbircalendar

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//Singleton класс отвечающий за работу с SharedPreferences в котором у нас хранятся дела
object EventsSharedPref {
    private lateinit var sharedPref: SharedPreferences
    var sharedPrefIsNull = true
    private val errMsg = "SharedPreferences is null. Use setSharedPref()"
    val gson = Gson()
    private const val EVENTS_KEY = "events_list"
    private var currenSize = 0
    private var eventsList = mutableListOf<Event>()

    //Метод который задаёт SharedPreferences. Если этот метол ни разу не вызвать, остальные методы работать не будут
    fun setSharedPref(sp: SharedPreferences){
        sharedPref = sp
        sharedPrefIsNull = false
        refresh()
    }

    fun getEvents(): MutableList<Event> {
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        refresh()
        return eventsList
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

    //В проекте не используется. Нужен для отладки
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
    //возвращает корректный id для новых дел в зависимости от количества дел в хранилище
    fun getCurrentID(): Int{
        if (sharedPrefIsNull) throw NullPointerException(errMsg)
        refresh()
        return currenSize
    }
}