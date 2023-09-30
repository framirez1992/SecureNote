package com.far.securenote.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.far.securenote.R
import com.far.securenote.common.FileManager
import com.far.securenote.databinding.ActivityBulkInsertNoteBinding
import com.far.securenote.model.viewStates.BulkInsertState
import com.far.securenote.model.services.NoteService
import com.far.securenote.view.common.BaseActivity
import com.far.securenote.viewmodel.BulkInsertViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class BulkInsertNote : BaseActivity() {

    private lateinit var _binding:ActivityBulkInsertNoteBinding


    @Inject lateinit var noteService: NoteService
    @Inject lateinit var fileManager: FileManager

    private lateinit var viewModel:BulkInsertViewModel
    private lateinit var activityResultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBulkInsertNoteBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        presentationComponent.inject(this)
        viewModel =ViewModelProvider(this, BulkInsertViewModel.BulkInsertViewModelFactory(noteService))[BulkInsertViewModel::class.java]


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            // Handle the Activity result.
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = result?.data?.data
                    lifecycleScope.launch {
                        try {
                            var fileContent=  fileManager.readFileContent(fileUri!!)
                            viewModel.massiveNotesUploadFromString(fileContent)

                        }catch (ex:Exception){
                            Toast.makeText(applicationContext,ex.message!!, Toast.LENGTH_LONG).show()
                        }
                    }

                }
                Activity.RESULT_CANCELED -> {
                    // The Activity was canceled.
                    Toast.makeText(applicationContext,"cancelled", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // The Activity failed.
                    Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                }
            }

        }

        initViews()
        initObservers()
    }

    private fun initViews(){
        _binding.btnSearchFile.setOnClickListener {
            searchFile()
        }
    }
    private fun initObservers(){
        viewModel.state.observe(this){
            _binding.btnSearchFile.setLoading(it.loading)
            setStatus(it)
        }
    }
    private fun searchFile(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/json"

        // Start the Activity that you want to receive the result from.
        activityResultLauncher.launch(intent)
    }


    private fun setStatus (status: BulkInsertState){
        val message:String
        val color:Int
        if(status.loading){
            message = getString(R.string.loading)
            color = getColor(R.color.black)
        }else if(status.operationResult != null){
            if(status.operationResult.code == "00"){
                message = getString(R.string.file_loaded_successfully)
                color = getColor(R.color.green_500)
            }else {
                message = getString(R.string.file_load_error)
                color = getColor(R.color.red_500)
            }
        }else{
            message = getString(R.string.search_note_file)
            color = getColor(R.color.black)
        }
        _binding.tvMessage.text = message
        _binding.tvMessage.setTextColor(color)
    }
}