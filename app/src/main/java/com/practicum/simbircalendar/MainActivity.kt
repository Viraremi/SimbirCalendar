package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.sql.Timestamp
import java.time.LocalDate
import java.util.Date

class MainActivity : AppCompatActivity() {

    companion object{
        private const val EVENTS = "events"
        private val NO_EVENT_LIST = listOf<Event>(
            Event(0,
                Timestamp(0*3600000),
                Timestamp(1*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(1*3600000),
                Timestamp(2*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(2*3600000),
                Timestamp(3*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(3*3600000),
                Timestamp(4*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(4*3600000),
                Timestamp(5*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(5*3600000),
                Timestamp(6*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(6*3600000),
                Timestamp(7*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(7*3600000),
                Timestamp(8*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(8*3600000),
                Timestamp(9*3600000),
                "",
                ""
            ),
            Event(0,
                Timestamp(9*3600000),
                Timestamp(10*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(10*3600000),
                Timestamp(11*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(11*3600000),
                Timestamp(12*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(12*3600000),
                Timestamp(13*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(13*3600000),
                Timestamp(14*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(14*3600000),
                Timestamp(15*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(15*3600000),
                Timestamp(16*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(16*3600000),
                Timestamp(17*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(17*3600000),
                Timestamp(18*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(18*3600000),
                Timestamp(19*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(19*3600000),
                Timestamp(20*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(20*3600000),
                Timestamp(21*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(21*3600000),
                Timestamp(22*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(22*3600000),
                Timestamp(23*3600000),
                "",
                ""
            ),Event(0,
                Timestamp(23*3600000),
                Timestamp(-1 + 24*3600000),
                "",
                ""
            )
        )
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
        val eventAdapter = EventAdapter(eventAdapterList)
        val eventsStorage = EventsSharedPref(getSharedPreferences(EVENTS, MODE_PRIVATE), gson)
        val events = eventsStorage.getEvents()

        eventRecycler.adapter = eventAdapter
        eventRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        clndr.setOnDateChangeListener { _, year, month, dayOfMonth ->
            eventAdapterList.clear()
            eventAdapterList.addAll(NO_EVENT_LIST)
            val selectedDay = LocalDate.of(year, month+1, dayOfMonth).toString()
            for (item in events){
                val day = getDateFromStamp(item.data_start)
                if (day == selectedDay){
                    val index = getHoursFromStamp(item.data_start).toInt() - 1
                    eventAdapterList[index] = item
                }
            }
            eventAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateFromStamp(stamp: Timestamp): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dataTime = Date(stamp.time)
        return sdf.format(dataTime)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getHoursFromStamp(stamp: Timestamp): String {
        val sdf = SimpleDateFormat("HH")
        val dataTime = Date(stamp.time)
        return sdf.format(dataTime)
    }
}