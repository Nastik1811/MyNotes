package com.example.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import kotlinx.android.synthetic.main.note_item_view.view.*

class NotesAdapter constructor(context: Context,  val clickListener: (Long) -> Unit): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = listOf<NoteWithTags>()

    internal fun setNotes(notes: List<NoteWithTags>){
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = inflater.inflate(R.layout.note_item_view, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = notes[position]
        holder.bind(item, clickListener )
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(noteWithTags: NoteWithTags, clickListener: (noteId: Long) -> Unit){
            itemView.title.text = noteWithTags.note.title
            itemView.content.text = noteWithTags.note.content
            itemView.tags_line.text = noteWithTags.tags.joinToString (separator = " ")
            itemView.setOnClickListener { clickListener(noteWithTags.note.noteId) }
        }
    }
}
