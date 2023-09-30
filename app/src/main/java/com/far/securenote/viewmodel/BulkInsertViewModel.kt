package com.far.securenote.viewmodel

import androidx.lifecycle.*
import com.far.securenote.contants.Colors
import com.far.securenote.model.*
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class BulkInsertViewModel(var noteService: NoteService): ViewModel() {

    private val _state = MutableLiveData<BulkInsertState>()
    val state: LiveData<BulkInsertState> = _state

    init {
        _state.value = BulkInsertState(false,null)
    }


    fun massiveNotesUploadFromString(json:String){
        _state.value = _state.value?.copy(loading = true, operationResult = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gson = Gson()
                val notes: List<MassiveLoadingNote> = gson.fromJson(json, object : TypeToken<List<MassiveLoadingNote>>() {}.type)
                val n = notes.map { Note(id= UUID.randomUUID().toString(), title = it.title, body = it.description, color = Colors.values()[(0 until Colors.values().size).random()].toString()) }
                noteService.batchInsertNote(n)

                operationResult("00")

            }catch (ex:Exception){
                operationResult("99",ex.message!!)
            }

        }
    }

    private fun operationResult(code:String){
        operationResult(code,"")
    }
    private fun operationResult(code:String,message:String){
        val operationResult= when(code){
            "00" -> OperationResult("00","success")
            "01" -> OperationResult("01","success (LOCAL)")
            "90" -> OperationResult("90","cancelled")
            else -> OperationResult("99",message)
        }
        val newState: BulkInsertState = _state.value!!.copy(loading = false,operationResult = operationResult)
        _state.postValue(newState)
    }


    class BulkInsertViewModelFactory(ns: NoteService) : ViewModelProvider.Factory {
        private val noteService: NoteService

        init {
            noteService = ns
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BulkInsertViewModel(noteService) as T
        }
    }
}