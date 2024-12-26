package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
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
        //функция создающая список пустых дел
        private var NO_EVENT_LIST = mutableListOf<Event>()
        @SuppressLint("SimpleDateFormat")
        private fun noEventListCreate(){
            val today = TimestampConvert.getDate(Timestamp(System.currentTimeMillis()))
            for (i in 0..23){
                val longI = i.toLong()
                NO_EVENT_LIST.add(NO_EVENT_LIST.size, Event(i,
                    Timestamp(SimpleDateFormat("yyyy-MM-dd").parse(today).time + longI*3600000),
                    Timestamp(SimpleDateFormat("yyyy-MM-dd").parse(today).time + (longI+1)*3600000),
                    "",
                    ""
                ))
            }
        }
    }

    var selectedDay = ""
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

        //View переменные
        val clndr = findViewById<CalendarView>(R.id.calendar)
        val eventRecycler = findViewById<RecyclerView>(R.id.event_recycler)

        //Создаём список пустых дел для отображения ячеек списка дел
        noEventListCreate()

        //Задаём SharedPreferences
        EventsSharedPref.setSharedPref(getSharedPreferences(EVENTS, MODE_PRIVATE))

        //Устанавливаем adapter и layoutManager для RecyclerView
        eventRecycler.adapter = eventAdapter
        eventRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Обработка выбора даты на календаре
        clndr.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val events = EventsSharedPref.getEvents()
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

    //Обновляем данные и список дел на экране после добавления нового дела
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val index = data!!.getIntExtra("pos", 0)
            eventAdapterList[index] = EventsSharedPref.getEvents()[0]
            eventAdapter.notifyItemChanged(index)
            Toast.makeText(this@MainActivity, "Дело создано", Toast.LENGTH_SHORT).show()
        }
    }
}