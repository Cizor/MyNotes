package com.example.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.db.NoteDatabase
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.recyclerNoteList?.setHasFixedSize(true)
        binding?.recyclerNoteList?.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                binding?.recyclerNoteList?.adapter = NotesAdapter(notes)
            }

        }
        binding?.buttonAdd?.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}