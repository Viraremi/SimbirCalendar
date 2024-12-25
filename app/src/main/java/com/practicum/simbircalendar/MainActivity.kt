package com.practicum.simbircalendar

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.Serializable
import java.sql.Timestamp
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    companion object{
        const val EVENTS = "events"
        const val REQUES_CODE = 1
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
    var selectedDay = ""
    private val gson = Gson()
    lateinit var eventsStorage: EventsSharedPref
    private var eventAdapterList = mutableListOf<Event>()
    val eventAdapter = EventAdapter(eventAdapterList) {event: Event, position: Int ->
        val eventAddIntent = Intent(this, EventAdd::class.java)
        eventAddIntent.putExtra("event", event as Serializable)
        eventAddIntent.putExtra("pos", position)
        eventAddIntent.putExtra("selectedDay", selectedDay)
        startActivityForResult(eventAddIntent, REQUES_CODE)
    }

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
        eventsStorage = EventsSharedPref(getSharedPreferences(EVENTS, MODE_PRIVATE), gson)

        eventRecycler.adapter = eventAdapter
        eventRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        clndr.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val events = eventsStorage.getEvents()
            eventAdapterList.clear()
            eventAdapterList.addAll(NO_EVENT_LIST)
            selectedDay = LocalDate.of(year, month+1, dayOfMonth).toString()
            for (item in events){
                val day = TimestampConvert.getDate(item.data_start)
                if (day == selectedDay){
                    val index = TimestampConvert.getHours(item.data_start).toInt()
                    eventAdapterList[index] = item
                }
            }
            eventAdapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val index = data!!.getIntExtra("pos", 0)
            eventAdapterList[index] = eventsStorage.getEvents()[0]
            eventAdapter.notifyItemChanged(index)
            Toast.makeText(this@MainActivity, "Дело создано", Toast.LENGTH_SHORT).show()
        }
    }
}