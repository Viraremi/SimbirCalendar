package com.practicum.simbircalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private val events: List<Event>,
    private val click: (Event, Int) -> Unit
) : RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
        holder.itemView.setOnClickListener{
            click(events[position], position)
        }
    }
}