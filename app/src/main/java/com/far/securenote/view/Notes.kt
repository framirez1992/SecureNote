package com.far.securenote.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.far.securenote.R
import com.far.securenote.common.ScreenNavigator
import com.far.securenote.contants.Colors
import com.far.securenote.databinding.FragmentNotesBinding
import com.far.securenote.model.Note
import com.far.securenote.model.services.NoteService
import com.far.securenote.model.OperationResult
import com.far.securenote.utils.ColorUtils
import com.far.securenote.view.common.BaseFragment
import com.far.securenote.viewmodel.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class Notes : BaseFragment() {

    private var currentNote: Note?=null

    private lateinit var _binding: FragmentNotesBinding
    private lateinit var viewModel:NotesViewModel

    @Inject lateinit var noteService: NoteService
    @Inject lateinit var screenNavigator:ScreenNavigator


    companion object {
        @JvmStatic
        fun newInstance(note:Note?) =
            Notes().apply {
                arguments = Bundle().apply {
                    currentNote = note
                }

            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        presentationComponent.inject(this)
        viewModel =ViewModelProvider(this, NotesViewModel.NoteAddViewModelFactory(noteService,currentNote))[NotesViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentNotesBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    override fun onPause() {
        super.onPause()
        //text persistence
        updateNoteText()
    }


    private fun initViews(){
        val onColorSelected: (View) -> Unit = {
           var color:Colors= when(it.id){
                _binding.colorPicker.btnAmber.id->  Colors.AMBER
                _binding.colorPicker.btnPurple.id-> Colors.PURPLE
                _binding.colorPicker.btnBlue.id-> Colors.BLUE
                _binding.colorPicker.btnGray.id-> Colors.GRAY
                _binding.colorPicker.btnGreen.id-> Colors.GREEN
                _binding.colorPicker.btnPink.id-> Colors.PINK
                else -> Colors.BLACK
            }

            updateNoteText()
            viewModel.changeColor(color)
        }

        _binding.colorPicker.btnAmber.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnBlack.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnBlue.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnGray.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnGreen.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnPink.setOnClickListener(onColorSelected)
        _binding.colorPicker.btnPurple.setOnClickListener(onColorSelected)

        _binding.btnAddNote.setOnClickListener {
            if (!validateNote())
                return@setOnClickListener
            updateNoteText()
            viewModel.saveNote()
        }

        _binding.btnBulkInsertNotes.setOnClickListener{
            startActivity(Intent(context,BulkInsertNote::class.java))
        }
        _binding.btnEditNote.setOnClickListener {
            if (!validateNote())
                return@setOnClickListener
            updateNoteText()
            viewModel.updateNote()
        }
        _binding.btnDeleteNote.setOnClickListener {
            viewModel.deleteNote()
        }

    }
    private fun initObservers(){
        viewModel.state.observe(viewLifecycleOwner){
            processOperationResul(it.operationResult)
            _binding.btnAddNote.setLoading(it.loading)
            _binding.btnBulkInsertNotes.setLoading(it.loading)
            _binding.btnEditNote.setLoading(it.loading)
            _binding.btnDeleteNote.setLoading(it.loading)
            enableViews(!it.loading)
        }

        viewModel.currentNote.observe(viewLifecycleOwner){
                changeNoteColor(ColorUtils.colorByName(it!!.color))
                _binding.etTitle.setText(it!!.title)
                _binding.etBody.setText(it!!.body)
                showButtons(it.docRef == null)
        }
    }

    private fun validateNote(): Boolean {
        if(_binding.etTitle.text.isNullOrBlank()){
            Snackbar.make(_binding.root,getString(R.string.title_cannot_be_empty),Snackbar.LENGTH_LONG).show()
            _binding.etTitle.requestFocus()
            return false
        }
        if(_binding.etBody.text.isNullOrBlank()){
            Snackbar.make(_binding.root,getString(R.string.please_insert_content),Snackbar.LENGTH_LONG).show()
            _binding.etBody.requestFocus()
            return false
        }


        return true
    }

    private fun enableViews(enable:Boolean){
        _binding.etTitle.isEnabled = enable
        _binding.etBody.isEnabled = enable
        _binding.btnAddNote.isEnabled = enable
        _binding.btnBulkInsertNotes.isEnabled = enable
        _binding.colorPicker.btnAmber.isEnabled = enable
        _binding.colorPicker.btnBlack.isEnabled = enable
        _binding.colorPicker.btnBlue.isEnabled = enable
        _binding.colorPicker.btnGray.isEnabled = enable
        _binding.colorPicker.btnGreen.isEnabled = enable
        _binding.colorPicker.btnPink.isEnabled = enable
        _binding.colorPicker.btnPurple.isEnabled = enable

    }


    private fun changeNoteColor(color:Colors) {
        var combination = ColorUtils.getColors(color)
        _binding.etTitle.setTextColor(resources.getColor(combination.color3))
        _binding.etTitle.setBackgroundColor(resources.getColor(combination.color1))

        _binding.etBody.setTextColor(resources.getColor(combination.color3))
        _binding.cardBody.setCardBackgroundColor(resources.getColor(combination.color2))
    }

    private fun updateNoteText(){
        viewModel.updateNoteText(_binding.etTitle.text.toString(),_binding.etBody.text.toString())
    }

    private fun processOperationResul(operation:OperationResult?){
        if(operation != null){
            if (operation.code == "00" || operation.code == "01") {
                screenNavigator.MainScren(R.id.frame)
                Toast.makeText(context,operation.message,Toast.LENGTH_LONG).show()
            }else {
                Snackbar.make(_binding.root, operation.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showButtons(newNote:Boolean){
        _binding.btnAddNote.visibility = if(newNote)View.VISIBLE else View.GONE
        _binding.btnBulkInsertNotes.visibility = if(newNote)View.VISIBLE else View.GONE
        _binding.btnEditNote.visibility = if(newNote)View.GONE else View.VISIBLE
        _binding.btnDeleteNote.visibility = if(newNote)View.GONE else View.VISIBLE
    }



}