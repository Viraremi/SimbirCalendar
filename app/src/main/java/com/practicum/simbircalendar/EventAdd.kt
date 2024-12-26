package com.practicum.simbircalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EventAdd : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    lateinit var newEvent: Event
    var eventPos: Int = 0
    var allGood = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            //Отступы при открытой клавиатуре
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

        //Данные из intent
        eventPos = intent.getIntExtra("pos", 0)
        val intentEvent = intent.getSerializableExtra("event") as Event

        //View переменные
        val viewTimeStart = findViewById<TextView>(R.id.add_title_time_start)
        val viewTimeEnd = findViewById<TextView>(R.id.add_title_time_end)
        val editName = findViewById<EditText>(R.id.add_edit_name)
        val editDescription = findViewById<EditText>(R.id.add_edit_description)
        val editDate = findViewById<TextView>(R.id.add_edit_date)
        val btnDone = findViewById<Button>(R.id.add_btn_done)
        val btnCancel = findViewById<Button>(R.id.add_btn_cancel)

        //Выводим полученные данные на экран
        viewTimeStart.text = TimestampConvert.getTime(intentEvent.data_start)
        viewTimeEnd.text = TimestampConvert.getTime(intentEvent.data_end)
        editName.setText(intentEvent.name)
        editDescription.setText(intentEvent.description)
        editDate.text = TimestampConvert.getDate(intentEvent.data_start)

        //Кнопка Готово
        btnDone.setOnClickListener{
            newEvent = Event(
                EventsSharedPref.getCurrentID(),
                intentEvent.data_start,
                intentEvent.data_end,
                editName.text.toString(),
                editDescription.text.toString()
            )
            EventsSharedPref.add(newEvent)
            allGood = true
            finish()
        }

        //Кнопка Отмена
        btnCancel.setOnClickListener {
            finish()
        }
    }

    //Возвращаем ответ в MainActivity
    override fun finish() {
        val intent = Intent()
        intent.putExtra("pos", eventPos)
        val requestCode = if (allGood) RESULT_OK else RESULT_CANCELED
        setResult(requestCode, intent)
        super.finish()
    }
}