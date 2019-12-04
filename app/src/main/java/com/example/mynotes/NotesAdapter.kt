package com.example.mynotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.fragment_notes_item.view.*

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    var notes = listOf<Note>()

    set(value){
        field=value
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notes[position]
        holder.titleView.text = item.title
        holder.contentView.text = item.content
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
        val contentView: TextView = view.content
    }
}