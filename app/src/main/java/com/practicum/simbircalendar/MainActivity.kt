package com.practicum.simbircalendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {

    companion object{
        private const val EVENTS = "events"
    }
    private val gson = Gson()
    private var eventAdapterList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val clndr = findViewById<CalendarView>(R.id.calendar)
        val eventRecycler = findViewById<RecyclerView>(R.id.event_recycler)
        val noEventsMsg = findViewById<TextView>(R.id.events_is_empty)
        val eventAdapter = EventAdapter(eventAdapterList)
        val eventsStorage = EventsSharedPref(getSharedPreferences(EVENTS, MODE_PRIVATE), gson)

        clndr.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = (dayOfMonth.toString() + (month + 1).toString() + year.toString()).toInt()
            eventAdapterList.clear()
            for (item in eventsStorage.getEvents()){
                if (item.id == selectedDay){
                    eventAdapterList.add(item)
                }
            }
            eventAdapter.notifyDataSetChanged()
            noEventsMsg.visibility = View.GONE
            eventRecycler.visibility = View.VISIBLE
            Toast.makeText(this@MainActivity, selectedDay.toString(), Toast.LENGTH_SHORT).show()
        }


        eventRecycler.adapter = eventAdapter
        eventRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}