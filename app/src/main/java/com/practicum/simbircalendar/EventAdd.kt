package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.ConstrainedFieldPosition
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.textclassifier.ConversationActions.Request
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        val editName = findViewById<EditText>(R.id.add_edit_name)
        val editDescription = findViewById<EditText>(R.id.add_edit_description)
        val btnDone = findViewById<Button>(R.id.add_btn_done)
        val btnCancel = findViewById<Button>(R.id.add_btn_cancel)
        val eventsStorage = EventsSharedPref(getSharedPreferences(MainActivity.EVENTS, MODE_PRIVATE), Gson())
        val date = SimpleDateFormat("yyyy-MM-dd").parse(selectedDay)

        btnDone.setOnClickListener{
            newEvent = Event(
                0,
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
        intent.putExtra("event_id", newEvent.id)
        val requestCode = if (allGood) RESULT_OK else RESULT_CANCELED
        setResult(requestCode, intent)
        super.finish()
    }
}