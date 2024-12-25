package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.sql.Timestamp

class EventAdd : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    lateinit var newEvent: Event
    var eventPos: Int = 0
    var selectedDay: String? = ""
    var allGood = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets((WindowInsetsCompat.Type.ime()))
            val paddingBottom = if (ime.bottom > 0) {
                ime.bottom
            }
            else{
                systemBars.bottom
            }
            v.setPadding(
                systemBars.left, systemBars.top, systemBars.right, paddingBottom
            )
            insets
        }
        eventPos = intent.getIntExtra("pos", 0)
        selectedDay = intent.getStringExtra("selectedDay")
        val intentEvent = intent.getSerializableExtra("event") as Event

        val viewTimeStart = findViewById<TextView>(R.id.add_title_time_start)
        val viewTimeEnd = findViewById<TextView>(R.id.add_title_time_end)
        val editName = findViewById<EditText>(R.id.add_edit_name)
        val editDescription = findViewById<EditText>(R.id.add_edit_description)
        val editDate = findViewById<TextView>(R.id.add_edit_date)
        val btnDone = findViewById<Button>(R.id.add_btn_done)
        val btnCancel = findViewById<Button>(R.id.add_btn_cancel)

        val eventsStorage = EventsSharedPref(getSharedPreferences(MainActivity.EVENTS, MODE_PRIVATE), Gson())
        val date = SimpleDateFormat("yyyy-MM-dd").parse(selectedDay)
        editDate.text = selectedDay

        viewTimeStart.text = TimestampConvert.getTime(intentEvent.data_start)
        viewTimeEnd.text = TimestampConvert.getTime(intentEvent.data_end)
        editName.setText(intentEvent.name)
        editDescription.setText(intentEvent.description)

        btnDone.setOnClickListener{
            newEvent = Event(
                eventsStorage.getCurrentID(),
                Timestamp(date.time + eventPos * 3600000),
                Timestamp(date.time + (eventPos+1) * 3600000),
                editName.text.toString(),
                editDescription.text.toString()
            )
            eventsStorage.add(newEvent)
            allGood = true
            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra("pos", eventPos)
        val requestCode = if (allGood) RESULT_OK else RESULT_CANCELED
        setResult(requestCode, intent)
        super.finish()
    }
}