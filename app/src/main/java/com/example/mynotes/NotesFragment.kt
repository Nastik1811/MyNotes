package com.example.mynotes

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.db.AppDb


class NotesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notes, container, false)

        val application = requireNotNull(this.activity).application

        val database = AppDb.getInstance(application)

        val viewModelFactory = NotesViewModelFactory(database, application)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(NotesViewModel::class.java)


        binding.notesViewModel = viewModel
        binding.setLifecycleOwner(this)

        val newNoteButton = binding.addNote
        newNoteButton.setOnClickListener{ Log.i("NotesFragment","note added")}

        val adapter = NotesAdapter()
        binding.notesList.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner, Observer { adapter.notes = it })

        return binding.root

    }


}
