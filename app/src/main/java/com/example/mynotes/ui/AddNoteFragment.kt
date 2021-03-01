package com.example.mynotes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentAddNoteBinding
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.utils.toast
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

    private var _binding : FragmentAddNoteBinding? = null
    private val binding get() = _binding

    private var mNote : Note? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            mNote = AddNoteFragmentArgs.fromBundle(it).note
            binding?.editTextTitle?.setText(mNote?.title)
            binding?.editTextNote?.setText(mNote?.note)
        }
        binding?.buttonSave?.setOnClickListener{ view ->
            val titleText = binding!!.editTextTitle.text.toString().trim()
            val titleNote = binding!!.editTextNote.text.toString().trim()

            if (titleText.isEmpty()){
                binding?.editTextTitle?.error  = "Title required"
                binding?.editTextTitle?.requestFocus()
                return@setOnClickListener
            }

            if (titleNote.isEmpty()){
                binding?.editTextNote?.error = "Title required"
                binding?.editTextNote?.requestFocus()
                return@setOnClickListener
            }

            launch {

                context?.let {
                    val note = Note(titleText, titleNote)

                    if (mNote == null)
                    {
                        NoteDatabase(it).getNoteDao().addNote(note)
                        it.toast("Note saved")
                    }
                    else
                    {
                        note.id = mNote!!.id
                        NoteDatabase(it).getNoteDao().updateNote(note)
                        it.toast("Note Updated")
                    }
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }

            }

        }
    }

     private fun deleteNote()
     {
         AlertDialog.Builder(context).apply {
             setTitle("Are you sure?")
             setMessage("You cannot undo this operation")
             setPositiveButton("Yes"){_,_ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(mNote!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(requireView()).navigate(action)
                }
             }
             setNegativeButton("No"){_,_ ->

             }
         }.create().show()
     }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete -> if(mNote != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

}