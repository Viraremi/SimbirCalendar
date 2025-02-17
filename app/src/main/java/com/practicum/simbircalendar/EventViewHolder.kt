package com.practicum.simbircalendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val eventName: TextView
    val eventDescription: TextView
    val eventStart: TextView
    val eventEnd: TextView
    init {
        eventName = itemView.findViewById(R.id.event_name)
        eventDescription = itemView.findViewById(R.id.event_description)
        eventStart = itemView.findViewById(R.id.event_datestart)
        eventEnd = itemView.findViewById(R.id.event_dateend)
    }

    fun bind(model: Event){
        eventName.text = model.name
        eventDescription.text = model.description
        eventStart.text = TimestampConvert.getTime(model.data_start)
        eventEnd.text = TimestampConvert.getTime(model.data_end)
    }
}