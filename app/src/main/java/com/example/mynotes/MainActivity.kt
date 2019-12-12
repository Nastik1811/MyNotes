package com.example.mynotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MainActivity : AppCompatActivity() {

    private lateinit var chipsGroup: ChipGroup
    private lateinit var notesViewModel: NotesViewModel

    private fun onNoteClicked(noteId: Long){
        editSelectedNote(noteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recyclerView = binding.notesList
        val adapter = NotesAdapter(this) { noteId: Long -> onNoteClicked(noteId)}
        recyclerView.adapter = adapter

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else
            recyclerView.layoutManager = GridLayoutManager(this, 2)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        val observer = Observer<List<NoteWithTags>>{ notes -> adapter.setNotes(notes) }
        notesViewModel.notesWithTags.observe(this, Observer { notes -> notes?.let { adapter.setNotes(it) } })

        binding.notesViewModel = notesViewModel
        binding.lifecycleOwner = this

        binding.addNote.setOnClickListener{

            addNewNote()
        }

        chipsGroup = binding.filterChips
        chipsGroup.isSingleSelection = true
        chipsGroup.setOnCheckedChangeListener{ chipGroup, id ->
            val chip = chipGroup.findViewById<Chip>(id)
            if (chip != null) {
                val tagName = chipGroup.findViewById<Chip>(id).text.toString()
                notesViewModel.getNotesWithSelectedTag(tagName)
                        .observe(this, Observer { notes -> notes?.let { adapter.setNotes(it) } })
                }
            else notesViewModel.notesWithTags.observe(
                    this,
                    Observer { notes -> notes?.let { adapter.setNotes(it) } })
        }
        setTags()
    }

    private fun editSelectedNote(id: Long){
        val intent  =  Intent(this, NoteDetailActivity::class.java)
        intent.putExtra("noteId", id)
        intent.action = Intent.ACTION_EDIT
        startActivity(intent)
        }

    private fun addNewNote()
    {
        val intent  =  Intent(this, NoteDetailActivity::class.java)
        intent.action = Intent.ACTION_INSERT
        startActivity(intent)
    }

    private fun setTags() {
        for (tag in notesViewModel.tags) {
            putChip(tag.name)
        }
    }

    private fun putChip(name: String){
        val chip = Chip(this, null, R.attr.CustomChipChoiceStyle)
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 10f,
            resources.displayMetrics
        ).toInt()
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
        chip.text = name
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if(chip.isChecked) chip.setChipBackgroundColorResource(R.color.colorPrimary)
        else chip.setChipBackgroundColorResource(R.color.colorChip)}
        chipsGroup.addView(chip as View)
    }

}


