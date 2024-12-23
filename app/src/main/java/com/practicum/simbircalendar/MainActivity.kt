package com.practicum.simbircalendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clndr = findViewById<CalendarView>(R.id.calendar)
        val eventRecycler = findViewById<RecyclerView>(R.id.event_recycler)
        val noEventsMsg = findViewById<TextView>(R.id.events_is_empty)

        val eventAdapterList : ArrayList<Event> = ArrayList(arrayListOf(
            Event(
                1,
                Timestamp(System.currentTimeMillis() + 0*3600000),
                Timestamp(System.currentTimeMillis() + 1*3600000),
                "Покушать",
                "Надо обязательно покушать перед поездкой"),
            Event(
                1,
                Timestamp(System.currentTimeMillis() + 1*3600000),
                Timestamp(System.currentTimeMillis() + 2*3600000),
                "Поработать",
                "Надо обязательно поработать чтобы потом покушать"),
            Event(
                1,
                Timestamp(System.currentTimeMillis() + 2*3600000),
                Timestamp(System.currentTimeMillis() + 3*3600000),
                "Встреча с заказчиком",
                "Описание встречи"),
            Event(
                1,
                Timestamp(System.currentTimeMillis() + 3*3600000),
                Timestamp(System.currentTimeMillis() + 4*3600000),
                "Собеседование",
                "Описание собеседования"),
            Event(
                1,
                Timestamp(System.currentTimeMillis() + 4*3600000),
                Timestamp(System.currentTimeMillis() + 5*3600000),
                "Отдых",
                "Описание отдыха")
        ))
        eventRecycler.adapter = EventAdapter(eventAdapterList)
        eventRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        noEventsMsg.visibility = View.GONE
        eventRecycler.visibility = View.VISIBLE

        clndr.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //textView.text = "$dayOfMonth.${month + 1}.$year"
        }
    }
}