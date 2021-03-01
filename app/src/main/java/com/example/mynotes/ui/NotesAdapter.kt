package com.example.mynotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.NoteLayoutBinding
import com.example.mynotes.db.Note


class NotesAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(binding)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder)
        {
            with(notes[position]){
                binding.textViewTitle.text = title
                binding.textViewNote.text = note
            }
        }
        holder.binding.root.setOnClickListener{
            val action = HomeFragmentDirections.actionAddNote(notes[position])
            Navigation.findNavController(it).navigate(action)

        }

    }

    inner class NoteViewHolder(val binding: NoteLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)
}